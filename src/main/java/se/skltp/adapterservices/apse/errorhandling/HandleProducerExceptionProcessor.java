package se.skltp.adapterservices.apse.errorhandling;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.apache.camel.http.base.HttpOperationFailedException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.skltp.adapterservices.apse.config.ExceptionHandling;
import se.skltp.adapterservices.apse.utils.PayloadInfoParser;

import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
@Log4j2
public class HandleProducerExceptionProcessor implements Processor {

    private static final String SOAP_XMLNS = "http://schemas.xmlsoap.org/soap/envelope/";
    private static final Integer HTTP_STATUS_500 = 500;

    @Autowired
    ExceptionHandling exceptionHandling;

    private static final String soapFault = "<?xml version = '1.0' encoding = 'UTF-8'?>\n" +
            "<SOAP-ENV:Envelope\n" +
            "   xmlns:SOAP-ENV = \"http://schemas.xmlsoap.org/soap/envelope/\"\n" +
            "   xmlns:xsi = \"http://www.w3.org/1999/XMLSchema-instance\"\n" +
            "   xmlns:xsd = \"http://www.w3.org/1999/XMLSchema\">\n" +
            "   <SOAP-ENV:Body>\n" +
            "      <SOAP-ENV:Fault>\n" +
            "         <faultcode xsi:type = \"xsd:string\">SOAP-ENV:%s</faultcode>\n" +
            "         <faultstring xsi:type = \"xsd:string\">\n" +
            "            %s" +
            "         </faultstring>\n" +
            "      </SOAP-ENV:Fault>\n" +
            "   </SOAP-ENV:Body>\n" +
            "</SOAP-ENV:Envelope>";

    private enum faultType {
        SERVER("Server"),
        CLIENT("Client");
        String type;
        @Override
        public String toString() { return type; }
        faultType(String type){ this.type=type; }
    }

    private String getFaultstring(String responseBody) {

        Matcher m = faultstringRePattern.matcher(responseBody);
        if (m.find()) {
            return m.group(1);
        }
        return null;
    }

    private static void setFaultBody(Exchange exchange, faultType type, String faultString, String body) {
        if (body == null) {
            body = String.format(soapFault, type, faultString);
        }
        exchange.getIn().setBody(body);
        exchange.setProperty("soapFault", faultString);
    }

    private static final String vp009msg = "VP009 Error connecting to service producer at address %s. %s";

    private static final Pattern faultstringRePattern = Pattern.compile("<[^:^ ]*:?faultstring[^>]*>([^<]*)</");

    @Override
    public void process(Exchange exchange) throws Exception {
        Message in = exchange.getIn();
        Throwable cause = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);

        String faultString = String.format(vp009msg, exchange.getProperty("outbound_url", String.class), cause);
        if (cause instanceof HttpOperationFailedException) {
            HttpOperationFailedException causeOperationFailed = (HttpOperationFailedException) cause;
            int statusCode = causeOperationFailed.getStatusCode();
            if (statusCode >= 500 || (statusCode >= 200 && statusCode <= 220)) {
                in.removeHeaders(".*");
                in.setHeaders(Collections.unmodifiableMap(causeOperationFailed.getResponseHeaders()));
                String responseBody = causeOperationFailed.getResponseBody();
                setFaultBody(exchange, faultType.SERVER, getFaultstring(responseBody), responseBody);
                in.setHeader(Exchange.HTTP_RESPONSE_CODE, exceptionHandling.getHttpStatus("passthrough", statusCode));
            } else {
                setFaultBody(exchange, faultType.SERVER, faultString, null);
                in.setHeader(Exchange.HTTP_RESPONSE_CODE, exceptionHandling.getHttpStatus("serverFault"));
            }
        } else if (cause instanceof PayloadInfoParser.PayloadExcepption) {
            setFaultBody(exchange, faultType.CLIENT, faultString, null);
            in.setHeader(Exchange.HTTP_RESPONSE_CODE, exceptionHandling.getHttpStatus("clientFault"));
        } else {
            setFaultBody(exchange, faultType.CLIENT, faultString, null);
            in.setHeader(Exchange.HTTP_RESPONSE_CODE, exceptionHandling.getHttpStatus("IOFault"));
        }
    }
}
