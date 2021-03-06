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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lf.laslfprivatperson.v4;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.lf.laslfprivatperson.v4.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.lf.laslfprivatperson.v4.rivtabp21.LasLFPrivatpersonResponderInterface;
import se.riv.se.apotekensservice.lf.laslfprivatperson.v4.rivtabp21.SystemException;
import se.riv.se.apotekensservice.lf.laslfprivatpersonresponder.v4.LasLFPrivatpersonRequestType;
import se.riv.se.apotekensservice.lf.laslfprivatpersonresponder.v4.LasLFPrivatpersonResponseType;


public class LasLFPrivatpersonTestProducer implements LasLFPrivatpersonResponderInterface {

	@Override
	@WebResult(name = "LasLFPrivatpersonResponse", targetNamespace = "urn:riv:se.apotekensservice:lf:LasLFPrivatpersonResponder:4", partName = "parameters")
	@WebMethod(operationName = "LasLFPrivatperson", action = "urn:riv:se.apotekensservice:lf:LasLFPrivatpersonResponder:4:LasLFPrivatperson")
	public LasLFPrivatpersonResponseType lasLFPrivatperson(
			@WebParam(partName = "parameters", name = "LasLFPrivatperson", targetNamespace = "urn:riv:se.apotekensservice:lf:LasLFPrivatpersonResponder:4") LasLFPrivatpersonRequestType parameters,
			@WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "http://www.w3.org/2005/08/addressing", header = true) String logicalAddress,
			@WebParam(partName = "ArgosHeader", name = "ArgosHeader", targetNamespace = "urn:riv:inera.se.apotekensservice:argos:1", header = true) ArgosHeaderType argosHeader)
			throws SystemException,	ApplicationException {
		// TODO Auto-generated method stub

		LasLFPrivatpersonResponseType response = new LasLFPrivatpersonResponseType();
		return response;
	}


	
}
