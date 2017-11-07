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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lfutskrift;

import javax.jws.WebService;

import riv.se_apotekensservice.lf._1.PatientResponse;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.lf.lfutskrift.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.lf.lfutskrift.v1.rivtabp21.LFUtskriftResponderInterface;
import se.riv.inera.se.apotekensservice.lf.lfutskrift.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.lf.lfutskriftresponder.v1.LFUtskriftRequestType;
import se.riv.se.apotekensservice.lf.lfutskriftresponder.v1.LFUtskriftResponseType;

@WebService(serviceName = "LFUtskriftResponderService", 
			endpointInterface = "se.riv.inera.se.apotekensservice.lf.lfutskrift.v1.rivtabp21.LFUtskriftResponderInterface", 
			portName = "LFUtskriftResponderPort", targetNamespace = "urn:riv:se.apotekensservice:lf:LFUtskrift:1:rivtabp21", 
			wsdlLocation = "TD_APSE_LF_5_0_RC1/interactions/LFUtskriftInteraction/LFUtskriftInteraction_1.0_rivtabp21.wsdl")
public class LFUtskriftTestProducer implements LFUtskriftResponderInterface {
	
	public static final String CITIZENREQUEST = "188803099368";
	public static final String ORGANIZATIONREQUEST = "191704109279";

	@Override
	public LFUtskriftResponseType lfUtskrift(LFUtskriftRequestType parameters, String logicalAddress,
			ArgosHeaderType argosHeader) throws ApplicationException, SystemException {
		/*
		 * ArgosHeader is removed by TicketMachine and can not be accessed by
		 * the producer. TODO: Is there any way we can verify that this is a
		 * citizen request, we need the saml ticket in the soap header?
		 */

		return createResponse(parameters.getPersonnummer());
	}

	private LFUtskriftResponseType createResponse(String ssn) {
		LFUtskriftResponseType response = new LFUtskriftResponseType();
		response.setPatient(createPatientResponse(ssn));
		return response;
	}

	private PatientResponse createPatientResponse(String ssn) {
		PatientResponse response = new PatientResponse();
		response.setAvliden(true);
		response.setEfternamn("Andersson");
		response.setFornamn("Agda");
		response.setPersonnummer(ssn);
		return response;
	}

}
