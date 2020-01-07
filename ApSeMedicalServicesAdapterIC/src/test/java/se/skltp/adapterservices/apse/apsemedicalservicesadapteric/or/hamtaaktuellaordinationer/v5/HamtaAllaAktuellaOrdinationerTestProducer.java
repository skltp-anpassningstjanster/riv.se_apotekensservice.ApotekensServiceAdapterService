/**
 * Copyright (c) 2014 Inera AB, <http://inera.se/>
 *
 * This file is part of SKLTP.
 *
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 *
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.or.hamtaaktuellaordinationer.v5;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

import se.riv.se.apotekensservice.or.hamtaaktuellaordinationer.v5.ApplicationException;
import se.riv.se.apotekensservice.or.hamtaaktuellaordinationer.v5.HamtaAktuellaOrdinationerResponderInterface;
import se.riv.se.apotekensservice.or.hamtaaktuellaordinationer.v5.SystemException;
import se.riv.se.apotekensservice.or.hamtaaktuellaordinationerresponder.v5.HamtaAktuellaOrdinationerResponseType;
import se.riv.se.apotekensservice.or.v7.ApoteksinformationResponse;
import se.riv.se.apotekensservice.or.v7.ArbetsplatsInfoResponse;
import se.riv.se.apotekensservice.or.v7.ArtikelResponse;
import se.riv.se.apotekensservice.or.v7.DosunderlagResponse;
import se.riv.se.apotekensservice.or.v7.ForskrivarinfoResponse;
import se.riv.se.apotekensservice.or.v7.OrdinationslistaResponse;

@WebService(serviceName = "HamtaAktuellaOrdinationerResponderService",
		endpointInterface = "se.riv.se.apotekensservice.or.hamtaaktuellaordinationer.v5.HamtaAktuellaOrdinationerResponderInterface",
portName = "HamtaAktuellaOrdinationerResponderPort", targetNamespace = "urn:riv:se.apotekensservice:or:HamtaAktuellaOrdinationer:5",
wsdlLocation = "TD_APSE_OR_7_0/interactions/HamtaAktuellaOrdinationerInteraction/HamtaAktuellaOrdinationerInteraction_5.2_rivtabp21.wsdl")
public class HamtaAllaAktuellaOrdinationerTestProducer implements
		HamtaAktuellaOrdinationerResponderInterface {

	@Override
    @WebResult(name = "HamtaAktuellaOrdinationerResponse", targetNamespace = "urn:riv:se.apotekensservice:or:HamtaAktuellaOrdinationerResponder:5", partName = "parameters")
    @WebMethod(operationName = "HamtaAktuellaOrdinationer", action = "urn:riv:se.apotekensservice:or:HamtaAktuellaOrdinationerResponder:5:HamtaAktuellaOrdinationer")
    public HamtaAktuellaOrdinationerResponseType hamtaAktuellaOrdinationer(
        @WebParam(partName = "parameters", name = "HamtaAktuellaOrdinationer", targetNamespace = "urn:riv:se.apotekensservice:or:HamtaAktuellaOrdinationerResponder:5")
        se.riv.se.apotekensservice.or.hamtaaktuellaordinationerresponder.v5.HamtaAktuellaOrdinationerRequestType parameters,
        @WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true)
        String logicalAddress,
        @WebParam(partName = "ArgosHeader", name = "ArgosHeader", targetNamespace = "urn:riv:inera.se.apotekensservice:argos:1", header = true)
        se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType argosHeader
    ) throws ApplicationException, SystemException {
		String personnummer = parameters.getPersonnummer();

		if (personnummer == null || "".equals(personnummer)) {
		    throw new RuntimeException("Personnummer is mandatory!");
		}

		if ("APPLICATIONEXCEPTION".equals(personnummer)) {
		    throw new ApplicationException(
			    "APPLICATIONEXCEPTION");
		}

		if ("SYSTEMEXCEPTION".equals(personnummer)) {
		    throw new SystemException(
			    "SYSTEMEXCEPTION");
		}

		HamtaAktuellaOrdinationerResponseType response = new HamtaAktuellaOrdinationerResponseType();
		response.getApoteksInformationLista().add(createApoteksInformation());
		response.getArbetsplatsInfoLista().add(createArbetsplatsInfo());
		response.getArtikelLista().add(createArtikelResponse());
		response.getForskrivarInfoLista().add(createForskrivarinfo());
		response.setOrdinationslista(createOrdinationsLIsta(personnummer));
		return response;
	}

    private ApoteksinformationResponse createApoteksInformation() {
	ApoteksinformationResponse apoteksInformation = new ApoteksinformationResponse();
	apoteksInformation.setAktorsnamn("Aktornamn");
	apoteksInformation.setAktorsorgnr(1L);
	apoteksInformation.setAllmantelefon("Allmantelefon");
	apoteksInformation.setBesoksadress("Besoksadress");
	apoteksInformation.setEReceptDjur(true);
	apoteksInformation.setFax("Fax");
	apoteksInformation.setGlnKod("GlnKod");
	apoteksInformation.setHuvudtypkod("Huvudtypkod");
	apoteksInformation.setNamn("Namne");
	apoteksInformation.setOrt("Ort");
	apoteksInformation.setRegistrerad(true);
	// apoteksInformation.setSlutdatum();
	// apoteksInformation.setStartdatum(value);
	return apoteksInformation;
    }

    private ArbetsplatsInfoResponse createArbetsplatsInfo() {
	ArbetsplatsInfoResponse arbetsplatsInfo = new ArbetsplatsInfoResponse();
	arbetsplatsInfo.setArbetsplatskod("Arbetsplatskod");
	arbetsplatsInfo.setArbetsplatsNamn("Arbetsplatsnamn");
	arbetsplatsInfo.setArbetsplatsOrt("Arbetsplatsort");
	arbetsplatsInfo.setFaxnummer("Faxnummer");
	// arbetsplatsInfo.setGiltigSlut(value);
	// arbetsplatsInfo.setGiltigStart(value);
	arbetsplatsInfo.setPostadress("Potadress");
	arbetsplatsInfo.setPostnummer("Postnummer");
	arbetsplatsInfo.setRegistrerad(true);
	arbetsplatsInfo.setTelefonnummer1("Telefonnummer1");
	arbetsplatsInfo.setTelefonnummer2("Telefonnummer2");
	return arbetsplatsInfo;
    }

    private ArtikelResponse createArtikelResponse() {
	ArtikelResponse artikel = new ArtikelResponse();
	artikel.setAlternativtAntalKlartext("AlternativtAntalKlartext");
	artikel.setAntalKlartext("AntalKlartext");
	artikel.setArtikelbenamning("Artikelbenamning");
	artikel.setAtcKlartext("AtcKlartext");
	artikel.setAtcKod("AtcKod");
	// artikel.setAvregistreringsdatum(value);
	artikel.setBestallningsstoppad(true);
	artikel.setForegaendePrisperiodensvara(true);
	artikel.setFormansberattigad(true);
	artikel.setForpackningsenhet("Forpackningsenhet");
	artikel.setForpackningsinnehall("Forpackningsinnehall");
	artikel.setForpackningsmangd(10D);
	artikel.setForpackningstyp("Forpackningstyp");
	artikel.setForsaljningsstoppad(true);
	// TODO: Fler attribut
	return artikel;
    }

    private ForskrivarinfoResponse createForskrivarinfo() {
	ForskrivarinfoResponse forskrivarInfo = new ForskrivarinfoResponse();
	forskrivarInfo.setEfternamn("Efternamn");
	forskrivarInfo.setFornamn("Fornamn");
	forskrivarInfo.setForskrivarkod("Forskrivarkod");
	forskrivarInfo.setGiltig(true);
	forskrivarInfo.setGruppforskrivarkodKlartext("Gruppforskrivarkod");
	//forskrivarInfo.setInskrankt(true);
	return forskrivarInfo;
    }

    private OrdinationslistaResponse createOrdinationsLIsta(String personnummer) {
	DosunderlagResponse dosunderlag = new DosunderlagResponse();
	dosunderlag.setDosproducent("Dosproducent");
	dosunderlag.setDosunderlagsversion(1);

	OrdinationslistaResponse ordinationslista = new OrdinationslistaResponse();
	ordinationslista.setPersonnummer(personnummer);
	ordinationslista.setUnderlagsversion(1);
	ordinationslista.setDosunderlag(dosunderlag);
	return ordinationslista;
    }

}
