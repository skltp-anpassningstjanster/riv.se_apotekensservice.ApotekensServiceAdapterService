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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lf.laslfforskrivare.v4;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.lf.laslfforskrivare.v4.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.lf.laslfforskrivare.v4.rivtabp21.LasLFForskrivareResponderInterface;
import se.riv.se.apotekensservice.lf.laslfforskrivare.v4.rivtabp21.SystemException;
import se.riv.se.apotekensservice.lf.laslfforskrivareresponder.v4.LasLFForskrivareRequestType;
import se.riv.se.apotekensservice.lf.laslfforskrivareresponder.v4.LasLFForskrivareResponseType;


public class LasLFForskrivareTestProducer implements LasLFForskrivareResponderInterface {

	@Override
	@WebResult(name = "LasLFForskrivareResponse", targetNamespace = "urn:riv:se.apotekensservice:lf:LasLFForskrivareResponder:4", partName = "parameters")
	@WebMethod(operationName = "LasLFForskrivare", action = "urn:riv:se.apotekensservice:lf:LasLFForskrivareResponder:4:LasLFForskrivare")
	public LasLFForskrivareResponseType lasLFForskrivare(
			@WebParam(partName = "parameters", name = "LasLFForskrivare", targetNamespace = "urn:riv:se.apotekensservice:lf:LasLFForskrivareResponder:4") LasLFForskrivareRequestType arg0, 
			@WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "http://www.w3.org/2005/08/addressing", header = true) String arg1,
			@WebParam(partName = "ArgosHeader", name = "ArgosHeader", targetNamespace = "urn:riv:inera.se.apotekensservice:argos:1", header = true) ArgosHeaderType arg2) 
					throws ApplicationException, SystemException {
		// TODO Auto-generated method stub

		LasLFForskrivareResponseType response = new LasLFForskrivareResponseType();
		return response;
	}


}
