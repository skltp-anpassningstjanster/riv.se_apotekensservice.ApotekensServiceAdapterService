package se.skltp.adapterservices.apse.apsemedicalservicesadapteric;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.RoutesBuilder;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit5.CamelTestSupport;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import se.skltp.adapterservices.apse.EndpointResolverProcessor;
import se.skltp.adapterservices.apse.utils.SamlHeaderFromArgosProcessor;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Log4j2
public class RouteTest extends CamelTestSupport {

    SamlHeaderFromArgosProcessor samlHeaderFromArgosProcessor;
    EndpointResolverProcessor resolveEndpoint;

    private static String prescripber = "Förskrivare";
    private static String hamtaPatientInfoRequestIn =
            "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:riv:inera.se.apotekensservice:argos:1\" xmlns:urn1=\"urn:riv:itintegration:registry:1\" xmlns:urn2=\"urn:riv:se.apotekensservice:axs:HamtaPatientInfoResponder:6\">\n" +
            "   <soapenv:Header>\n" +
            "      <urn:ArgosHeader>\n" +
            "         <!--Optional:-->\n" +
            "         <urn:forskrivarkod>"+prescripber+"</urn:forskrivarkod>\n" +
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
    @DisplayName("Argos to Saml Processor")
    public void testArgos2SamlProcessor() throws Exception {

        getMockEndpoint("mock:result").expectedMinimumMessageCount(1);

        template.sendBody("direct:argos2saml", hamtaPatientInfoRequestIn);

        MockEndpoint.assertIsSatisfied(context);

        List<Exchange> exchanges = getMockEndpoint("mock:result").getExchanges();
        Exchange e = exchanges.get(0);
        String body = e.getIn().getBody(String.class);

        assertThat(body).contains(new String[]{
                "83xHIE0CAwEAAaNZMFcwVQYDVR0BBE4wTIAQg6JKNhP",
                "<ds:KeyName>Pascal Key</ds:KeyName>",
                "<saml2:AttributeValue xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xsi:type=\"xs:string\">"+prescripber+"</saml2:AttributeValue>"
        });

    }

    @Test
    @DisplayName("Test SSL to common https site (httpbin)")
    public void testSslToHttpBin() throws Exception {
        template.sendBody("direct:test_ssl", "Test!");
        MockEndpoint.assertIsSatisfied(context);

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
                        .toD("https://httpbin.org/post")
                        .to("mock:result");

            }
        };
    }

}