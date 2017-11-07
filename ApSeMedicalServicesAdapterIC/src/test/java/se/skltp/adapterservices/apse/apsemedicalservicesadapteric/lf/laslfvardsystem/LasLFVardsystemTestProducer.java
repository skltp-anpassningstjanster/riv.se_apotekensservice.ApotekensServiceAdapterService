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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lf.laslfvardsystem;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.lf.laslfvardsystem.v4.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.lf.laslfvardsystem.v4.rivtabp21.LasLFVardsystemResponderInterface;
import se.riv.inera.se.apotekensservice.lf.laslfvardsystem.v4.rivtabp21.SystemException;
import se.riv.se.apotekensservice.lf.laslfvardsystemresponder.v4.LasLFVardsystemRequestType;
import se.riv.se.apotekensservice.lf.laslfvardsystemresponder.v4.LasLFVardsystemResponseType;
import se.riv.se.apotekensservice.lf.v5.PatientResponse;


public class LasLFVardsystemTestProducer implements LasLFVardsystemResponderInterface {

	@Override
	@WebResult(name = "LasLFVardsystemResponse", targetNamespace = "urn:riv:se.apotekensservice:lf:LasLFVardsystemResponder:4", partName = "parameters")
	@WebMethod(operationName = "LasLFVardsystem", action = "urn:riv:se.apotekensservice:lf:LasLFVardsystemResponder:4:LasLFVardsystem")
	public LasLFVardsystemResponseType lasLFVardsystem(LasLFVardsystemRequestType parameters, String logicalAddress,
			ArgosHeaderType argosHeader) throws SystemException, ApplicationException {

		LasLFVardsystemResponseType response = new LasLFVardsystemResponseType();
		String ssn = parameters.getPersonnummer();
		PatientResponse p = new PatientResponse();
		p.setPersonnummer(ssn);
		p.setFornamn("Fornamn");
		p.setEfternamn("Efternamn");
		p.setAvliden(false);
		response.setPatient(p);
		response.setVarningsnivaUppnadd(false);
		return response;
	}


}
