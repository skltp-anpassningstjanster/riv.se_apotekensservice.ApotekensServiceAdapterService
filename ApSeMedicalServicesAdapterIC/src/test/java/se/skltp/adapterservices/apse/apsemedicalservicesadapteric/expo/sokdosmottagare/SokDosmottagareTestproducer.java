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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.expo.sokdosmottagare;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import org.w3c.addressing.v1.AttributedURIType;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.expo.sokdosmottagare.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.expo.sokdosmottagare.v1.rivtabp21.SokDosmottagareResponderInterface;
import se.riv.inera.se.apotekensservice.expo.sokdosmottagare.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.expo.sokdosmottagareresponder.v1.SokDosmottagareRequestType;
import se.riv.se.apotekensservice.expo.sokdosmottagareresponder.v1.SokDosmottagareResponseType;

public class SokDosmottagareTestproducer implements SokDosmottagareResponderInterface {


	@Override
	@WebResult(name = "SokDosmottagareResponse", targetNamespace = "urn:riv:se.apotekensservice:expo:SokDosmottagareResponder:1", partName = "parameters")
	@WebMethod(operationName = "SokDosmottagare", action = "urn:riv:se.apotekensservice:expo:SokDosmottagareResponder:1:SokDosmottagare")
	public SokDosmottagareResponseType sokDosmottagare(SokDosmottagareRequestType parameters, String logicalAddress,
			ArgosHeaderType argosHeader) throws ApplicationException, SystemException {

		SokDosmottagareResponseType response = new SokDosmottagareResponseType();
		return response;
	}

}
