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

    // Static utility
    private PayloadInfoParser() {
    }

    public static PayloadInfo extractInfoFromPayload(final XMLStreamReader reader) throws XMLStreamException {
        PayloadInfo payloadInfo = new PayloadInfo();
        payloadInfo.setEncoding(reader.getEncoding());

        while (reader.hasNext()) {
            if (reader.isStartElement()) {
                String local = reader.getLocalName();
                reader.next();
                switch (local) {
                    case "Header": {
                        String rivtabp = local.equals("LogicalAddress") ? RIVTABP_21 : (local.equals("To") ? RIVTABP_20 : null);
                        if (rivtabp != null) {
                            payloadInfo.setRivVersion(rivtabp);
                            reader.next();
                            if (!reader.isEndElement() && !reader.isWhiteSpace()) {
                                payloadInfo.setReceiverId(reader.getText());
                            }
                        }
                    }
                    break;
                    case "Body": {
                        // We have found the element we need in the Header and Body, i.e. we
                        // are done. Let's bail out!
                        payloadInfo.setServiceContractNamespace(reader.getNamespaceURI());
                        return payloadInfo;
                    }
                }
            }
        }
        return payloadInfo;
    }


    @Data
    public static class PayloadInfo {
        String encoding;
        String receiverId;
        String rivVersion;
        String serviceContractNamespace;
    }
}
