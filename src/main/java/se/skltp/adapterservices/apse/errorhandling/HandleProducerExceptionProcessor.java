package se.skltp.adapterservices.apse.errorhandling;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.skltp.adapterservices.apse.config.ExceptionHandling;
import java.util.Collections;


@Service
@Log4j2
public class HandleProducerExceptionProcessor implements Processor {

    private static final String SOAP_XMLNS = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final Integer HTTP_STATUS_500 = 500;

    @Autowired
    ExceptionHandling exceptionHandling;

    private static  final String soapFault = "<?xml version = '1.0' encoding = 'UTF-8'?>\n" +
            "<SOAP-ENV:Envelope\n" +
            "   xmlns:SOAP-ENV = \"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
            "   xmlns:xsi = \"http://www.w3.org/1999/XMLSchema-instance\"\n" +
            "   xmlns:xsd = \"http://www.w3.org/1999/XMLSchema\">\n" +
            "\n" +
            "   <SOAP-ENV:Body>\n" +
            "      <SOAP-ENV:Fault>\n" +
            "         <faultcode xsi:type = \"xsd:string\">SOAP-ENV:%s</faultcode>\n" +
            "         <faultstring xsi:type = \"xsd:string\">\n" +
            "            %s" +
            "         </faultstring>\n" +
            "      </SOAP-ENV:Fault>\n" +
            "   </SOAP-ENV:Body>\n" +
            "</SOAP-ENV:Envelope>";

    private String createServerSoapFault(String faultString) {
        return String.format(soapFault, "Server", faultString);
    }
    private String createClientSoapFault(String faultString) {
        return String.format(soapFault, "Client", faultString);
    }


    private static final String vp009msg = "VP009 Error connecting to service producer at address %s. %s";
    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        Throwable cause =  exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        String faultString = String.format(vp009msg, exchange.getProperty("outbound_url", String.class), cause);
        if (cause instanceof HttpOperationFailedException) {
            HttpOperationFailedException causeOperationFailed = (HttpOperationFailedException) cause;
            int statusCode = causeOperationFailed.getStatusCode();
            if (statusCode >= 500 || (statusCode >= 200 && statusCode <= 220)) {
                in.removeHeaders(".*");
                in.setHeaders(Collections.unmodifiableMap(causeOperationFailed.getResponseHeaders()));
                in.setHeader(Exchange.HTTP_RESPONSE_CODE, exceptionHandling.getHttpStatus("passthrough", statusCode));
                in.setBody(causeOperationFailed.getResponseBody());
            } else {
                in.setBody(createServerSoapFault(faultString));
                in.setHeader(Exchange.HTTP_RESPONSE_CODE, exceptionHandling.getHttpStatus("serverFault"));
            }
        } else {
            in.setBody(createServerSoapFault(faultString));
            in.setHeader(Exchange.HTTP_RESPONSE_CODE, exceptionHandling.getHttpStatus("clientFault"));
        }
    }
}