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
import se.skltp.adapterservices.apse.utils.PayloadInfoParser.PayloadInfo;

import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import java.io.ByteArrayInputStream;

/**
 *
 * @author jonmat
 */
@Service
@Log4j2
public class SamlHeaderFromArgosProcessor implements Processor {

    private final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    private final XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
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
        String in = exchange.getIn().getBody(String.class);
        byte[] bodyBytes = in.getBytes("UTF-8");

        extractPropertiesFromBody(bodyBytes, exchange);

        String transformed;
        transformed = (new SamlTicketTransformer()).transform(bodyBytes);
        exchange.getIn().setBody(transformed);
    }

    private void extractPropertiesFromBody(byte[] bodyBytes, Exchange exchange) throws XMLStreamException {
        PayloadInfo payloadInfo = PayloadInfoParser.extractInfoFromPayload(xmlInputFactory.createXMLStreamReader(new ByteArrayInputStream(bodyBytes)));
        exchange.setProperty(SERVICECONTRACT_NAMESPACE, payloadInfo.getServiceContractNamespace().replaceFirst(".*apotekensservice", "apotekensservice").replace(':', '.'));
        exchange.setProperty(RECEIVER_ID, payloadInfo.getReceiverId());
        exchange.setProperty(RIV_VERSION, payloadInfo.getRivVersion());
        exchange.setProperty(XML_REQUEST_ENCODING, payloadInfo.getEncoding());
    }
}
