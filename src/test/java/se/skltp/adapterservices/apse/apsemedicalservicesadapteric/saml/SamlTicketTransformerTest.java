/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 * <p>
 * This file is part of SKLTP.
 * <p>
 * This library is free software; you can redistribute it and/or modify it under
 * the terms of the GNU Lesser General Public License as published by the Free
 * Software Foundation; either version 2.1 of the License, or (at your option)
 * any later version.
 * <p>
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or FITNESS
 * FOR A PARTICULAR PURPOSE. See the GNU Lesser General Public License for more
 * details.
 * <p>
 * You should have received a copy of the GNU Lesser General Publi
 * c License
 * along with this library; if not, write to the Free Software Foundation, Inc.,
 * 51 Franklin Street, Fifth Floor, Boston, MA 02110-1301 USA
 */

package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.saml;

import lombok.extern.log4j.Log4j2;
import org.junit.jupiter.api.Test;
import se.skltp.adapterservices.apse.apsemedicalservicesadapteric.argos.ArgosHeader;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLOutputFactory;
import java.io.ByteArrayOutputStream;
import java.io.StringReader;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Log4j2
public class SamlTicketTransformerTest {

    public static final String EXPECTED_NAMESPACE = "<wsse:Security xmlns:wsse=\"http://docs.oasis-open.org/wss/2004/01/oasis-200401-wss-wssecurity-secext-1.0.xsd\">";
    static final XMLOutputFactory xmlOutputFactory = XMLOutputFactory.newInstance();
    static final XMLInputFactory xmlInputFactory = XMLInputFactory.newInstance();
    static final Holder mockPayloadHolder = new Holder();
    private static final String PAYLOAD = "<soapenv:Envelope xmlns:soapenv=\"http://schemas.xmlsoap.org/soap/envelope/\" xmlns:urn=\"urn:riv:inera.se.apotekensservice:argos:1\" xmlns:add=\"http://www.w3.org/2005/08/addressing\" xmlns:urn1=\"urn:riv:se.apotekensservice:or:HamtaAktuellaOrdinationerResponder:1\">"
            + "<soapenv:Header>"
            + "<urn:ArgosHeader>"
            + "<urn:forskrivarkod>1111129</urn:forskrivarkod>"
            + "<urn:legitimationskod>KOD</urn:legitimationskod>"
            + "<urn:fornamn>Lars</urn:fornamn>"
            + "<urn:efternamn>Lakare</urn:efternamn>"
            + "<urn:befattningskod>123456</urn:befattningskod>"
            + "<urn:arbetsplatskod>1234567890</urn:arbetsplatskod>"
            + "<urn:arbetsplatsnamn>Sjukhuset</urn:arbetsplatsnamn>"
            + "<urn:postort>Staden</urn:postort>"
            + "<urn:postadress>Vagen 1</urn:postadress>"
            + "<urn:postnummer>11111</urn:postnummer>"
            + "<urn:telefonnummer>08-1234567</urn:telefonnummer>"
            + "<urn:requestId>123456</urn:requestId>"
            + "<urn:rollnamn>FORSKRIVARE</urn:rollnamn>"
            + "<urn:directoryID>TSE6565656565-1003</urn:directoryID>"
            + "<urn:hsaID>TSE6565656565-1003</urn:hsaID>"
            + "<urn:katalog>HSA</urn:katalog>"
            + "<urn:organisationsnummer>1234567890</urn:organisationsnummer>"
            + "<urn:systemnamn>Melior</urn:systemnamn>"
            + "<urn:systemversion>1.0</urn:systemversion>"
            + "<urn:systemIp>192.0.0.1</urn:systemIp>"
            + "<urn:yrkesgrupp>Lakare</urn:yrkesgrupp>"
            + "</urn:ArgosHeader>"
            + "<add:To>1234567</add:To>"
            + "</soapenv:Header>"
            + "<soapenv:Body>"
            + "<urn1:HamtaAktuellaOrdinationer>"
            + "<urn1:personnummer>196308212817</urn1:personnummer>"
            + "</urn1:HamtaAktuellaOrdinationer>" + "</soapenv:Body>" + "</soapenv:Envelope>";
    private static final String UTF8 = "UTF-8";

    @Test
    public void testAddSamlTicketToOriginalRequest() throws Exception {
        SamlTicketTransformer samlTicketTransformer = new SamlTicketTransformer();

        XMLEventReader samlTicket = samlTicketTransformer.createSamlTicketFromArgosHeader(getArgosHeader());

        XMLEventReader payload = xmlInputFactory.createXMLEventReader(new StringReader(PAYLOAD));

        ByteArrayOutputStream result = new ByteArrayOutputStream();
        samlTicketTransformer.addSamlTicketToOriginalRequest(payload, samlTicket, xmlOutputFactory.createXMLEventWriter(result));
        String xml = result.toString(UTF8);
        assertThat(xml).contains(EXPECTED_NAMESPACE);
        assertThat(xml).doesNotContain("<urn:ArgosHeader");

    }

    @Test
    public void testSamlTicketReplacesArgosHeader() throws Exception {

        String msgIncludingSaml = new SamlTicketTransformer().transform(PAYLOAD.getBytes("UTF-8")).toString("UTF-8");

        assertThat(msgIncludingSaml).contains(EXPECTED_NAMESPACE);
        assertThat(msgIncludingSaml).doesNotContain("<urn:ArgosHeader");
    }

    private ArgosHeader getArgosHeader() {

        String forskrivarkod = "1111129";
        String legitimationskod = "KOD";
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

        return new ArgosHeader(forskrivarkod, legitimationskod, fornamn, efternamn, yrkesgrupp, befattningskod,
                arbetsplatskod, arbetsplatsnamn, postort, postadress, postnummer, telefonnummer, requestId, rollnamn,
                directoryID, hsaID, katalog, organisationsnummer, systemnamn, systemversion, systemIp);
    }

    static class Holder {

        private Object value = null;

        public Object getValue() {
            return value;
        }

        public void setValue(Object value) {
            this.value = value;
        }
    }
}
