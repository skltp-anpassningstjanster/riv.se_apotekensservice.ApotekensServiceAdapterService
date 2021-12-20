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
import se.skltp.adapterservices.apse.constants.HttpHeaders;
import se.skltp.camel.utils.constants.TjpExchangeProperties;

import javax.xml.stream.XMLInputFactory;
import java.io.ByteArrayInputStream;
import java.util.Map;
import java.text.SimpleDateFormat;

import static se.skltp.camel.utils.constants.TjpExchangeProperties.*;

/**
 * @author jonmat
 */
@Service
@Log4j2
public class ExtractPropertiesFromMessageProcessor implements Processor {
    private final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    @Override
    public void process(Exchange exchange) throws Exception {
        String in = exchange.getIn().getBody(String.class);
        if (in.length() < 1) {
            throw new PayloadInfoParser.PayloadExcepption("The message payload was empty");
        }
        byte[] bodyBytes = in.getBytes("UTF-8");

        PayloadInfoParser.PayloadInfo payloadInfo = PayloadInfoParser.extractInfoFromPayload(xmlInputFactory.createXMLStreamReader(new ByteArrayInputStream(bodyBytes)));


        exchange.setProperty(SERVICECONTRACT_NAMESPACE, payloadInfo.getServiceContractNamespace().replaceFirst(".*apotekensservice", "apotekensservice").replace(':', '.'));
        exchange.setProperty(RECEIVER_ID, payloadInfo.getReceiverId());
        exchange.setProperty(RIV_VERSION, payloadInfo.getRivVersion());
        exchange.setProperty(XML_REQUEST_ENCODING, payloadInfo.getEncoding());
        Map<String, Object> headers = exchange.getIn().getHeaders();
        exchange.setProperty(TjpExchangeProperties.HTTP_URL_IN,  headers.get(Exchange.HTTP_URL));
        exchange.setProperty(TjpExchangeProperties.TjP_X_FORWARDED_HOST,  headers.get(HttpHeaders.X_FORWARDED_HOST));
        exchange.setProperty(TjpExchangeProperties.TjP_X_FORWARDED_PORT,  headers.get(HttpHeaders.X_FORWARDED_PORT));
        exchange.setProperty(TjpExchangeProperties.TjP_X_FORWARDED_PROTO,  headers.get(HttpHeaders.X_FORWARDED_PROTO));
        exchange.setProperty(TjpExchangeProperties.SENDER_IP_ADRESS,  headers.get(HttpHeaders.X_FORWARDED_HOST));
        exchange.setProperty(TjpExchangeProperties.SENDER_ID, headers.get(HttpHeaders.X_VP_SENDER_ID));
        exchange.setProperty(TjpExchangeProperties.IN_ORIGINAL_SERVICE_CONSUMER_HSA_ID, headers.get(HttpHeaders.X_RIVTA_ORIGINAL_SERVICE_CONSUMER_HSA_ID));
        exchange.setProperty(TjpExchangeProperties.SKLTP_CORRELATION_ID, headers.get(HttpHeaders.X_SKLTP_CORRELATION_ID));

    }
}

