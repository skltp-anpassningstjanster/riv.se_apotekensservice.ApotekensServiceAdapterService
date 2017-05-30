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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lf.aterkallasamtyckewebb;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import org.w3c.addressing.v1.AttributedURIType;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.lf.aterkallasamtyckewebb.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.lf.aterkallasamtyckewebb.v1.rivtabp21.AterkallaSamtyckeWebbResponderInterface;
import se.riv.inera.se.apotekensservice.lf.aterkallasamtyckewebb.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.lf.aterkallasamtyckewebbresponder.v1.AterkallaSamtyckeWebbRequestType;
import se.riv.se.apotekensservice.lf.aterkallasamtyckewebbresponder.v1.AterkallaSamtyckeWebbResponseType;


public class AterkallaSamtyckeWebbTestProducer implements AterkallaSamtyckeWebbResponderInterface {

	@Override
	@WebResult(name = "AterkallaSamtyckeWebbResponse", targetNamespace = "urn:riv:se.apotekensservice:lf:AterkallaSamtyckeWebbResponder:1", partName = "parameters")
	@WebMethod(operationName = "AterkallaSamtyckeWebb", action = "urn:riv:se.apotekensservice:lf:AterkallaSamtyckeWebbResponder:1:AterkallaSamtyckeWebb")
	public AterkallaSamtyckeWebbResponseType aterkallaSamtyckeWebb(AterkallaSamtyckeWebbRequestType parameters,
			String logicalAddress, ArgosHeaderType argosHeader) throws SystemException, ApplicationException {
		// TODO Auto-generated method stub
		AterkallaSamtyckeWebbResponseType response = new AterkallaSamtyckeWebbResponseType();
		response.setSamtycke(1);
		return response;
		
	}

}
