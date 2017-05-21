/**
 * Copyright (c) 2013 Center for eHalsa i samverkan (CeHis).
 * 							<http://cehis.se/>
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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.hamtapatientinfo;

import javax.jws.WebService;

import org.w3c.addressing.v1.AttributedURIType;

import riv.se_apotekensservice.axs._4.ApoteksinformationResponse;
import riv.se_apotekensservice.axs._4.PatientInformationResponse;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.axs.hamtapatientinfo.v4.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.axs.hamtapatientinfo.v4.rivtabp21.HamtaPatientInfoResponderInterface;
import se.riv.inera.se.apotekensservice.axs.hamtapatientinfo.v4.rivtabp21.SystemException;
import se.riv.se.apotekensservice.axs.hamtapatientinforesponder.v4.HamtaPatientInfoRequestType;
import se.riv.se.apotekensservice.axs.hamtapatientinforesponder.v4.HamtaPatientInfoResponseType;

@WebService(serviceName = "HamtaPatientInfoResponderService", 
			endpointInterface = "se.riv.inera.se.apotekensservice.axs.hamtapatientinfo.v1.rivtabp20.HamtaPatientInfoResponderInterface", 
			portName = "HamtaPatientInfoResponderPort", targetNamespace = "urn:riv:inera:se.apotekensservice:axs:HamtaPatientInfo:1:rivtabp20", 
			wsdlLocation = "TD_APSE_AXS_4_0_RC1/interactions/HamtaPatientInfoInteraction/HamtaPatientInfoInteraction_4.0_rivtabp21.wsdl")
public class HamtaPatientInfoTestProducer implements HamtaPatientInfoResponderInterface {

	@Override
	public HamtaPatientInfoResponseType hamtaPatientInfo(HamtaPatientInfoRequestType arg0, String arg1,
			ArgosHeaderType arg2) throws SystemException, ApplicationException {
		// TODO Auto-generated method stub
		
		HamtaPatientInfoResponseType response = new HamtaPatientInfoResponseType();
		String ssn = arg0.getPersonnummer();
		
		response.setApotek(createApoteksInformationResponse(ssn));
		response.setPatientInformation(createPatientInformationResponse());
		response.setFinnsOrdination(true);
		response.setDosproducent(ssn);
		return response;
	}
	
    private ApoteksinformationResponse createApoteksInformationResponse(String ssn) {
	ApoteksinformationResponse apoteksinformationResponse = new ApoteksinformationResponse();
	apoteksinformationResponse.setAktorsnamn("Aktörsnamn");
	return null;
    }

    private PatientInformationResponse createPatientInformationResponse() {
	PatientInformationResponse informationResponse = new PatientInformationResponse();
	informationResponse.setLkfKod("1234");
	return null;
    }

}
