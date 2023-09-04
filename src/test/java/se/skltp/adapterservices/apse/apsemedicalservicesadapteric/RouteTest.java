package se.skltp.adapterservices.apse.apsemedicalservicesadapteric;

import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import se.skltp.adapterservices.apse.config.SecurityProperties;
import org.springframework.boot.test.context.SpringBootTest;
import se.skltp.adapterservices.apse.EndpointResolverProcessor;
import se.skltp.adapterservices.apse.utils.SamlHeaderFromArgosProcessor;
import se.skltp.adapterservices.apse.config.SSLContextParametersConfig;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class RouteTest extends CamelTestSupport {

    SamlHeaderFromArgosProcessor samlHeaderFromArgosProcessor;
    EndpointResolverProcessor resolveEndpoint;

    private static String hamtaPatientInfoRequestIn =
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:riv:inera.se.apotekensservice:argos:1\" xmlns:urn1=\"urn:riv:itintegration:registry:1\" xmlns:urn2=\"urn:riv:se.apotekensservice:axs:HamtaPatientInfoResponder:6\">\n" +
            "   <soapenv:Header>\n" +
            "      <urn:ArgosHeader>\n" +
            "         <!--Optional:-->\n" +
            "         <urn:forskrivarkod>Förskrivare</urn:forskrivarkod>\n" +
            "      </urn:ArgosHeader>\n" +
            "      <urn1:LogicalAddress>LOGICALADDR</urn1:LogicalAddress>\n" +
            "   </soapenv:Header>\n" +
            "   <soapenv:Body>\n" +
            "      <urn2:HamtaPatientInfo>\n" +
            "         <urn2:personnummer>191212121212</urn2:personnummer>\n" +
            "         <!--Optional:-->\n" +
            "         <urn2:extension>\n" +
            "            <!--You may enter ANY elements at this point-->\n" +
            "         </urn2:extension>\n" +
            "      </urn2:HamtaPatientInfo>\n" +
            "   </soapenv:Body>\n" +
            "</soapenv:Envelope>";

    @Test
    public void testArgos2SamlProcessor() throws Exception {

        getMockEndpoint("mock:result").expectedMinimumMessageCount(1);

        template.sendBody("direct:argos2saml", hamtaPatientInfoRequestIn);
        assertMockEndpointsSatisfied();

        List<Exchange> exchanges = getMockEndpoint("mock:result").getExchanges();
        Exchange e = exchanges.get(0);
        String body = e.getIn().getBody(String.class);

        assertThat(body).contains(new String[]{
                "83xHIE0CAwEAAaNZMFcwVQYDVR0BBE4wTIAQg6JKNhP",
                "<ds:KeyName>Pascal Key</ds:KeyName>",
                "<saml2:AttributeValue xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">Förskrivare</saml2:AttributeValue>"
        });

    }

    @Test
    public void testSslToHttpBin() throws Exception {
        SecurityProperties secprop = new SecurityProperties();
        secprop.setStore(new SecurityProperties.Store());
        secprop.getStore().setConsumer(new SecurityProperties.Store.Consumer());
        secprop.getStore().setTruststore(new SecurityProperties.Store.Truststore());

        SSLContextParametersConfig sslbeans = new SSLContextParametersConfig(secprop);
        context.getRegistry().bind("outgoingSSLContextParameters", sslbeans.outgoingSSLContextParameters());
        template.sendBody("direct:test_ssl", "Test!");
        assertMockEndpointsSatisfied();

        List<Exchange> exchanges = getMockEndpoint("mock:result").getExchanges();
        Exchange e = exchanges.get(0);
        String body = e.getIn().getBody(String.class);
        assertThat(body).contains("Apache-HttpClient");
    }

    @Override
    protected RoutesBuilder createRouteBuilder() {
        return new RouteBuilder() {
            @Override
            public void configure() {
                samlHeaderFromArgosProcessor = new SamlHeaderFromArgosProcessor();
                resolveEndpoint = new EndpointResolverProcessor();

                from("direct:argos2saml")
                        .process(samlHeaderFromArgosProcessor)
                        .to("mock:result");

                from("direct:test_ssl")
                        .toD("https://httpbin.org/post?sslContextParameters=#outgoingSSLContextParameters")
                        .to("mock:result");

            }
        };
    }

}