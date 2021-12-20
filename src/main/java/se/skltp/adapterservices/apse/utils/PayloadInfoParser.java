/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.skltp.adapterservices.apse.utils;


import lombok.Data;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;


public class PayloadInfoParser {

    public static final String RIVTABP_21 = "rivtabp21";
    public static final String RIVTABP_20 = "rivtabp20";

    public static class PayloadExcepption extends Exception{
        public PayloadExcepption(String message) {
            super(message);
        }
    };

    // Static utility
    private PayloadInfoParser() {
    }

    public static PayloadInfo extractInfoFromPayload(final XMLStreamReader reader) throws XMLStreamException {
        PayloadInfo payloadInfo = new PayloadInfo();
        payloadInfo.setEncoding(reader.getEncoding());

        boolean headerFound = false;
        boolean bodyFound = false;

        while (reader.hasNext()) {
            if (reader.isStartElement()) {
                String local = reader.getLocalName();

                if (bodyFound) {
                    // We have found the element we need in the Header and Body, i.e. we
                    // are done. Let's bail out!
                    payloadInfo.setServiceContractNamespace(reader.getNamespaceURI());
                    return payloadInfo;
                }

                //Body found, next element is the service interaction e.g GetSubjectOfCareSchedule
                if (local.equals("Body")) {
                    bodyFound = true;
                }

                if (local.equals("Header")) {
                    headerFound = true;
                }

                if (headerFound) {
                    readHeader(reader, payloadInfo, local);
                }

            }
            reader.next();
        }

        return payloadInfo;
    }

    private static void readHeader(XMLStreamReader reader, PayloadInfo payloadInfo, String local) throws XMLStreamException {
        switch (local) {
            case "To" :
                payloadInfo.setRivVersion(RIVTABP_20);
                break;
            case "LogicalAdress":
                payloadInfo.setRivVersion(RIVTABP_21);
                break;
        }
        reader.next();

        if (!reader.isEndElement() && !reader.isWhiteSpace()) {
            payloadInfo.setReceiverId(reader.getText());
        }
    }

    @Data
    public static class PayloadInfo {
        String encoding;
        String receiverId;
        String rivVersion;
        String serviceContractNamespace;
    }

}
