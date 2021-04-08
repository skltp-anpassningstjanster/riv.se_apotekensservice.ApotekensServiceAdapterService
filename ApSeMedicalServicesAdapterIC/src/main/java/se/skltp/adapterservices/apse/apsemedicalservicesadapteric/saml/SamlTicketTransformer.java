/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 *
 * You should have received a copy of the GNU Lesser General Public License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.saml;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import javax.xml.stream.FactoryConfigurationError;
import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLEventWriter;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.EndElement;
import javax.xml.stream.events.StartElement;
import javax.xml.stream.events.XMLEvent;

import lombok.extern.log4j.Log4j2;

import se.skltp.adapterservices.apse.apsemedicalservicesadapteric.argos.ArgosHeader;
import se.skltp.adapterservices.apse.apsemedicalservicesadapteric.argos.ArgosHeaderHelper;
import se.skltp.adapterservices.apse.apsemedicalservicesadapteric.exception.TicketMachineException;
import se.skltp.adapterservices.apse.apsemedicalservicesadapteric.ticket.TicketMachine;

/**
 * Transformer is responsible to add a SAML ticket to the incoming request,
 * based on Argos header information.
 *
 */
@Log4j2
public class SamlTicketTransformer {

    private final XMLInputFactory xmlInputFactory;
    private final XMLOutputFactory xmlOutputFactory;
    private final byte[] inputBody;
    
    public SamlTicketTransformer(byte[] inputBodyParam)
    {
        xmlInputFactory = XMLInputFactory.newInstance();
        xmlOutputFactory = XMLOutputFactory.newInstance();
        inputBody = inputBodyParam;
    }

    private XMLEventReader getInputXmlEventReader() throws XMLStreamException {
        return xmlInputFactory.createXMLEventReader(new ByteArrayInputStream(inputBody));
    }
    
    private class OutputXmlWriterWrapper {
        ByteArrayOutputStream os;
        XMLEventWriter writer;
        public OutputXmlWriterWrapper() throws XMLStreamException {
            this.os = new ByteArrayOutputStream();
            this.writer = xmlOutputFactory.createXMLEventWriter(os);
        }
        
    }
    
    public String transform() throws Exception {
        log.info("Saml ticket transformer executing");
        
        ArgosHeaderHelper argosUtil = new ArgosHeaderHelper();

        ArgosHeader argosHeader = argosUtil.extractArgosHeader(getInputXmlEventReader());

        try {
            XMLEventReader samlTicketXER = createSamlTicketFromArgosHeader(argosHeader);
            
            XMLEventReader inputRequestXER = getInputXmlEventReader();
            OutputXmlWriterWrapper updatedRequestXER = new OutputXmlWriterWrapper();

            addSamlTicketToOriginalRequest(inputRequestXER, samlTicketXER, updatedRequestXER.writer);
            return updatedRequestXER.os.toString();

        } catch (IllegalStateException | XMLStreamException | TicketMachineException e) {
            log.error("Could not transform/apply saml ticket to message", e);
            throw new IllegalStateException("Could not transform/apply saml ticket to message");
        }
    }

    XMLEventReader createSamlTicketFromArgosHeader(ArgosHeader argosHeader) throws TicketMachineException, XMLStreamException, FactoryConfigurationError {
        String samlTicketStr = new TicketMachine().produceSamlTicket(argosHeader);
        log.debug("Created saml ticket: \n" + samlTicketStr);

        StringReader stringReader = new StringReader(samlTicketStr);
        XMLEventReader samlTicket = XMLInputFactory.newInstance().createXMLEventReader(stringReader);
        return samlTicket;
    }

    void addSamlTicketToOriginalRequest(final XMLEventReader originalRequestReader, XMLEventReader samlTicket, XMLEventWriter outgoingMessageWriter) throws XMLStreamException {

        boolean insideArgosHeader = false;

        while (originalRequestReader.hasNext()) {
            final XMLEvent event = originalRequestReader.nextEvent();

            if (isNextEventArgusStartHeader(event)) {
                addSamlTicketToHeader(outgoingMessageWriter, samlTicket);
                insideArgosHeader = true;
                log.debug("Inside argos header, replaced it with SAML ticket");
            }

            if (isNextEventArgusEndHeader(event)) {
                insideArgosHeader = false;
                log.debug("Exit argos header");
                continue;
            }

            if (!insideArgosHeader) {
                outgoingMessageWriter.add(event);
            }
        }

        outgoingMessageWriter.flush();

    }

    private void addSamlTicketToHeader(final XMLEventWriter header, final XMLEventReader samlTicket) throws XMLStreamException {

        while (samlTicket.hasNext()) {

            XMLEvent nextEvent = samlTicket.nextEvent();

            if (nextEvent.isStartElement()) {
                header.add(nextEvent.asStartElement());
            } else if (nextEvent.isEndElement()) {
                header.add(nextEvent.asEndElement());
            } else if (nextEvent.isCharacters()) {
                header.add(nextEvent.asCharacters());
            }
        }
    }

    public boolean isNextEventArgusStartHeader(final XMLEvent event) {
        if (event.isStartElement()) {
            return isArgosElement(event.asStartElement());
        }
        return false;
    }

    private boolean isArgosElement(final StartElement se) {
        return se.getName().getLocalPart().equals("ArgosHeader");
    }

    public boolean isNextEventArgusEndHeader(final XMLEvent event) {
        if (event.isEndElement()) {
            return isArgosElement(event.asEndElement());
        }
        return false;
    }

    private boolean isArgosElement(final EndElement se) {
        return se.getName().getLocalPart().equals("ArgosHeader");
    }
}
