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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lf.registrerasamtyckevardsystem;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.lf.registrerasamtyckevardsystem.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.lf.registrerasamtyckevardsystem.v1.rivtabp21.RegistreraSamtyckeVardsystemResponderInterface;
import se.riv.inera.se.apotekensservice.lf.registrerasamtyckevardsystem.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.lf.registrerasamtyckevardsystemresponder.v1.RegistreraSamtyckeVardsystemRequestType;
import se.riv.se.apotekensservice.lf.registrerasamtyckevardsystemresponder.v1.RegistreraSamtyckeVardsystemResponseType;


public class RegistreraSamtyckeVardsystemTestProducer implements RegistreraSamtyckeVardsystemResponderInterface {

	@Override
	@WebResult(name = "RegistreraSamtyckeVardsystemResponse", targetNamespace = "urn:riv:se.apotekensservice:lf:RegistreraSamtyckeVardsystemResponder:1", partName = "parameters")
	@WebMethod(operationName = "RegistreraSamtyckeVardsystem", action = "urn:riv:se.apotekensservice:lf:RegistreraSamtyckeVardsystemResponder:1:RegistreraSamtyckeVardsystem")
	public RegistreraSamtyckeVardsystemResponseType registreraSamtyckeVardsystem(
			RegistreraSamtyckeVardsystemRequestType parameters, String logicalAddress, ArgosHeaderType argosHeader)
					throws SystemException, ApplicationException {
		RegistreraSamtyckeVardsystemResponseType response = new RegistreraSamtyckeVardsystemResponseType();
		return response;
	}



}
