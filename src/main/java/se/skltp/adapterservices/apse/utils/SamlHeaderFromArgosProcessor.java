/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.skltp.adapterservices.apse.utils;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;
import se.skltp.adapterservices.apse.apsemedicalservicesadapteric.saml.SamlTicketTransformer;

/**
 * @author jonmat
 */
@Service
@Log4j2
public class SamlHeaderFromArgosProcessor implements Processor {

    public static final String SENDER_ID = "senderid";
    public static final String SENDER_IP_ADRESS = "senderIpAdress";
    public static final String OUT_ORIGINAL_SERVICE_CONSUMER_HSA_ID = "originalServiceconsumerHsaidOut";
    public static final String IN_ORIGINAL_SERVICE_CONSUMER_HSA_ID = "originalServiceconsumerHsaidIn";
    public static final String SKLTP_CORRELATION_ID = "skltp_correlationId";
    public static final String RECEIVER_ID = "receiverid";
    public static final String RIV_VERSION = "rivversion";
    public static final String RIV_VERSION_OUT = "rivversion_out";
    public static final String SERVICECONTRACT_NAMESPACE = "servicecontract_namespace";
    public static final String XML_REQUEST_ENCODING = "XmlRequestEncoding";
    public static final String ORIGINAL_REQUEST_ENCODING = "OriginalRequestEncoding";
    public static final String HTTP_URL_IN = "HttpUrlIn";
    public static final String HTTP_URL_OUT = "HttpUrlOut";

    @Override
    public void process(Exchange exchange) throws Exception {
        byte[] bodyBytes = exchange.getIn().getBody(String.class).getBytes("UTF-8");

        String transformed;
        transformed = (new SamlTicketTransformer()).transform(bodyBytes);
        exchange.getIn().setBody(transformed);
    }
}
