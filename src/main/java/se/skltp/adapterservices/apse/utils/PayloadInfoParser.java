/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.skltp.adapterservices.apse.utils;


import lombok.Data;
import lombok.extern.log4j.Log4j2;

import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;

@Log4j2
public class PayloadInfoParser {

  public static final String RIVTABP_21 = "rivtabp21";
  public static final String RIVTABP_20 = "rivtabp20";

  // Static utility
  private PayloadInfoParser() {
  }

  public static PayloadInfo extractInfoFromPayload(final XMLStreamReader reader) throws XMLStreamException {
    PayloadInfo payloadInfo = new PayloadInfo();
    payloadInfo.setEncoding(reader.getEncoding());

    boolean headerFound = false;
    boolean bodyFound = false;

    while (reader.hasNext()) {
      if(reader.isStartElement()){
        String local = reader.getLocalName();
        log.debug(local);

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
      if (reader.hasNext()) {
          reader.next();
      }
    }

    return payloadInfo;
  }

  private static void readHeader(XMLStreamReader reader, PayloadInfo payloadInfo, String local) throws XMLStreamException {
    if (local.equals("To")) {
      payloadInfo.setRivVersion(RIVTABP_20);
      payloadInfo.setReceiverId(getReceiver(reader));
    } else if (local.equals("LogicalAddress")) {
      payloadInfo.setRivVersion(RIVTABP_21);
      payloadInfo.setReceiverId(getReceiver(reader));
    }
  }

  private static String getReceiver(XMLStreamReader reader) throws XMLStreamException {
    reader.next();
    if (!reader.isEndElement() && !reader.isWhiteSpace()) {
      return reader.getText();
    }
    return null;
  }

  @Data
  public static class PayloadInfo {
    String encoding;
    String receiverId;
    String rivVersion;
    String serviceContractNamespace;
  }

}
