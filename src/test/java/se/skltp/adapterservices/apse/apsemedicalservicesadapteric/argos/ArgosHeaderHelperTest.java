/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 * <p>
 * This file is part of SKLTP.
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.argos;


import org.junit.jupiter.api.Test;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamReader;
import java.io.StringReader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArgosHeaderHelperTest {

    static final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();

    @Test
    public void testExtractArgosHeaderWithAllInformationFromXMLEventReader() throws Exception {
        ArgosHeader expectedArgosHeader = createExpectedArgosHeader();
        String personnummer = "196308212817";

        String xml = getXmlPayload(expectedArgosHeader, personnummer);
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xml));
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(xmlStreamReader);
        ArgosHeader actualArgosHeader = new ArgosHeaderHelper().extractArgosHeaderFromXMLEventReader(xmlEventReader);

        assertCompleteArgosHeader(expectedArgosHeader, actualArgosHeader);
    }

    @Test
    public void testEmptyArgosHeaderIsCreatedWhenNoInfoIsProvidedFromXMLEventReader() throws Exception {
        ArgosHeader expectedArgosHeader = null;
        String personnummer = "196308212817";

        String xml = getXmlPayload(expectedArgosHeader, personnummer);
        XMLStreamReader xmlStreamReader = xmlInputFactory.createXMLStreamReader(new StringReader(xml));
        XMLEventReader xmlEventReader = xmlInputFactory.createXMLEventReader(xmlStreamReader);
        ArgosHeader actualArgosHeader = new ArgosHeaderHelper().extractArgosHeaderFromXMLEventReader(xmlEventReader);

        assertArgosHeaderIsEmpty(actualArgosHeader);
    }

    @Test
    public void testEmptyArgosHeaderIsCreatedWhenNoInfoIsProvided() throws XMLStreamException {
        String personnummer = "196308212817";

        XMLEventReader muleMessageBasedOnArgosHeader = xmlInputFactory.createXMLEventReader(new StringReader(getXmlPayload(null, personnummer)));
        ArgosHeader actualArgosHeader = new ArgosHeaderHelper().extractArgosHeader(muleMessageBasedOnArgosHeader);

        assertArgosHeaderIsEmpty(actualArgosHeader);
    }

    @Test
    public void testExtractArgosHeaderWithAllInformation() throws XMLStreamException {
        ArgosHeader expectedArgosHeader = createExpectedArgosHeader();
        String personnummer = "196308212817";

        XMLEventReader muleMessageBasedOnArgosHeader = xmlInputFactory.createXMLEventReader(new StringReader(getXmlPayload(expectedArgosHeader, personnummer)));
        ArgosHeader actualArgosHeader = new ArgosHeaderHelper().extractArgosHeader(muleMessageBasedOnArgosHeader);

        assertCompleteArgosHeader(expectedArgosHeader, actualArgosHeader);
    }

    private void assertArgosHeaderIsEmpty(ArgosHeader argosHeader) {
        assertThat(argosHeader).isNotNull();


        assertThat(argosHeader.getArbetsplatskod()).isNull();
        assertThat(argosHeader.getArbetsplatsnamn()).isNull();
        assertThat(argosHeader.getBefattningskod()).isNull();
        assertThat(argosHeader.getDirectoryID()).isNull();
        assertThat(argosHeader.getEfternamn()).isNull();
        assertThat(argosHeader.getFornamn()).isNull();
        assertThat(argosHeader.getForskrivarkod()).isNull();
        assertThat(argosHeader.getHsaID()).isNull();
        assertThat(argosHeader.getKatalog()).isNull();
        assertThat(argosHeader.getLegitimationskod()).isNull();
        assertThat(argosHeader.getOrganisationsnummer()).isNull();
        assertThat(argosHeader.getPostadress()).isNull();
        assertThat(argosHeader.getPostnummer()).isNull();
        assertThat(argosHeader.getPostort()).isNull();
        assertThat(argosHeader.getRequestId()).isNull();
        assertThat(argosHeader.getRollnamn()).isNull();
        assertThat(argosHeader.getSystemIp()).isNull();
        assertThat(argosHeader.getSystemnamn()).isNull();
        assertThat(argosHeader.getSystemversion()).isNull();
        assertThat(argosHeader.getTelefonnummer()).isNull();
        assertThat(argosHeader.getYrkesgrupp()).isNull();
    }

    private void assertCompleteArgosHeader(ArgosHeader expectedArgosHeader, ArgosHeader actualArgosHeader) {
        assertThat(actualArgosHeader).isNotNull();
        assertThat(expectedArgosHeader).isNotNull();


        assertThat(actualArgosHeader.getArbetsplatskod()).isEqualTo(expectedArgosHeader.getArbetsplatskod());
        assertThat(actualArgosHeader.getArbetsplatsnamn()).isEqualTo(expectedArgosHeader.getArbetsplatsnamn());
        assertThat(actualArgosHeader.getBefattningskod()).isEqualTo(expectedArgosHeader.getBefattningskod());
        assertThat(actualArgosHeader.getDirectoryID()).isEqualTo(expectedArgosHeader.getDirectoryID());
        assertThat(actualArgosHeader.getEfternamn()).isEqualTo(expectedArgosHeader.getEfternamn());
        assertThat(actualArgosHeader.getFornamn()).isEqualTo(expectedArgosHeader.getFornamn());
        assertThat(actualArgosHeader.getForskrivarkod()).isEqualTo(expectedArgosHeader.getForskrivarkod());
        assertThat(actualArgosHeader.getHsaID()).isEqualTo(expectedArgosHeader.getHsaID());
        assertThat(actualArgosHeader.getKatalog()).isEqualTo(expectedArgosHeader.getKatalog());
        assertThat(actualArgosHeader.getLegitimationskod()).isEqualTo(expectedArgosHeader.getLegitimationskod());
        assertThat(actualArgosHeader.getOrganisationsnummer()).isEqualTo(expectedArgosHeader.getOrganisationsnummer());
        assertThat(actualArgosHeader.getPostadress()).isEqualTo(expectedArgosHeader.getPostadress());
        assertThat(actualArgosHeader.getPostnummer()).isEqualTo(expectedArgosHeader.getPostnummer());
        assertThat(actualArgosHeader.getPostort()).isEqualTo(expectedArgosHeader.getPostort());
        assertThat(actualArgosHeader.getRequestId()).isEqualTo(expectedArgosHeader.getRequestId());
        assertThat(actualArgosHeader.getRollnamn()).isEqualTo(expectedArgosHeader.getRollnamn());
        assertThat(actualArgosHeader.getSystemIp()).isEqualTo(expectedArgosHeader.getSystemIp());
        assertThat(actualArgosHeader.getSystemnamn()).isEqualTo(expectedArgosHeader.getSystemnamn());
        assertThat(actualArgosHeader.getSystemversion()).isEqualTo(expectedArgosHeader.getSystemversion());
        assertThat(actualArgosHeader.getTelefonnummer()).isEqualTo(expectedArgosHeader.getTelefonnummer());
        assertThat(actualArgosHeader.getYrkesgrupp()).isEqualTo(expectedArgosHeader.getYrkesgrupp());
    }

    private ArgosHeader createExpectedArgosHeader() {

        String forskrivarkod = "1111129";
        String legitimationskod = "123";
        String fornamn = "Lars";
        String efternamn = "Lakare";
        String yrkesgrupp = "Lakare";
        String befattningskod = "123456";
        String arbetsplatskod = "1234567890";
        String arbetsplatsnamn = "Sjukhuset";
        String postort = "Staden";
        String postadress = "Vagen 1";
        String postnummer = "11111";
        String telefonnummer = "08-1234567";
        String requestId = "123456";
        String rollnamn = "FORSKRIVARE";
        String directoryID = "TSE6565656565-1003";
        String hsaID = "TSE6565656565-1003";
        String katalog = "HSA";
        String organisationsnummer = "1234567890";
        String systemnamn = "Melior";
        String systemversion = "1.0";
        String systemIp = "192.0.0.1";

        return new ArgosHeader(
                forskrivarkod, legitimationskod, fornamn, efternamn,
                yrkesgrupp, befattningskod, arbetsplatskod, arbetsplatsnamn,
                postort, postadress, postnummer, telefonnummer, requestId,
                rollnamn, directoryID, hsaID, katalog, organisationsnummer,
                systemnamn, systemversion, systemIp
        );
    }

    private String getXmlPayload(ArgosHeader expectedArgosHeader, String personnummer) {
        if (expectedArgosHeader != null) {
            return "<?xml version='1.0' encoding='UTF-8'?>"
                    + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:riv:inera.se.apotekensservice:argos:1\" xmlns:add=\"http://www.w3.org/2005/08/addressing\" xmlns:urn1=\"urn:riv:se.apotekensservice:or:HamtaAktuellaOrdinationerResponder:1\">"
                    + "<soapenv:Header>" + "<urn:ArgosHeader>"
                    + "<urn:yrkesgrupp>" + expectedArgosHeader.getYrkesgrupp() + "</urn:yrkesgrupp>"
                    + "<urn:forskrivarkod>" + expectedArgosHeader.getForskrivarkod() + "</urn:forskrivarkod>"
                    + "<urn:legitimationskod>" + expectedArgosHeader.getLegitimationskod() + "</urn:legitimationskod>"
                    + "<urn:fornamn>" + expectedArgosHeader.getFornamn() + "</urn:fornamn>"
                    + "<urn:efternamn>" + expectedArgosHeader.getEfternamn() + "</urn:efternamn>"
                    + "<urn:befattningskod>" + expectedArgosHeader.getBefattningskod() + "</urn:befattningskod>"
                    + "<urn:arbetsplatskod>" + expectedArgosHeader.getArbetsplatskod() + "</urn:arbetsplatskod>"
                    + "<urn:arbetsplatsnamn>" + expectedArgosHeader.getArbetsplatsnamn() + "</urn:arbetsplatsnamn>"
                    + "<urn:postort>" + expectedArgosHeader.getPostort() + "</urn:postort>"
                    + "<urn:postadress>" + expectedArgosHeader.getPostadress() + "</urn:postadress>"
                    + "<urn:postnummer>" + expectedArgosHeader.getPostnummer() + "</urn:postnummer>"
                    + "<urn:telefonnummer>" + expectedArgosHeader.getTelefonnummer() + "</urn:telefonnummer>"
                    + "<urn:requestId>" + expectedArgosHeader.getRequestId() + "</urn:requestId>"
                    + "<urn:rollnamn>" + expectedArgosHeader.getRollnamn() + "</urn:rollnamn>"
                    + "<urn:directoryID>" + expectedArgosHeader.getDirectoryID() + "</urn:directoryID>"
                    + "<urn:hsaID>" + expectedArgosHeader.getHsaID() + "</urn:hsaID>"
                    + "<urn:katalog>" + expectedArgosHeader.getKatalog() + "</urn:katalog>"
                    + "<urn:organisationsnummer>" + expectedArgosHeader.getOrganisationsnummer() + "</urn:organisationsnummer>"
                    + "<urn:systemnamn>" + expectedArgosHeader.getSystemnamn() + "</urn:systemnamn>"
                    + "<urn:systemversion>" + expectedArgosHeader.getSystemversion() + "</urn:systemversion>"
                    + "<urn:systemIp>" + expectedArgosHeader.getSystemIp() + "</urn:systemIp>" + "</urn:ArgosHeader>"
                    + "<add:To>1234567</add:To>" + "</soapenv:Header>" + "<soapenv:Body>"
                    + "<urn1:HamtaAktuellaOrdinationer>" + "<urn1:personnummer>" + personnummer + "</urn1:personnummer>"
                    + "</urn1:HamtaAktuellaOrdinationer>" + "</soapenv:Body>" + "</soapenv:Envelope>";
        } else {
            return "<?xml version='1.0' encoding='UTF-8'?>"
                    + "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:riv:inera.se.apotekensservice:argos:1\" xmlns:add=\"http://www.w3.org/2005/08/addressing\" xmlns:urn1=\"urn:riv:se.apotekensservice:or:HamtaAktuellaOrdinationerResponder:1\">"
                    + "<soapenv:Header>" + "<add:To>1234567</add:To>" + "</soapenv:Header>" + "<soapenv:Body>"
                    + "<urn1:HamtaAktuellaOrdinationer>" + "<urn1:personnummer>" + personnummer + "</urn1:personnummer>"
                    + "</urn1:HamtaAktuellaOrdinationer>" + "</soapenv:Body>" + "</soapenv:Envelope>";
        }
    }
}
