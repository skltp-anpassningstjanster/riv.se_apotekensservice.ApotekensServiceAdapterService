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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lf.kontrollerasamtycke;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.lf.kontrollerasamtycke.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.lf.kontrollerasamtycke.v1.rivtabp21.KontrolleraSamtyckeResponderInterface;
import se.riv.inera.se.apotekensservice.lf.kontrollerasamtycke.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.lf.kontrollerasamtyckeresponder.v1.KontrolleraSamtyckeRequestType;
import se.riv.se.apotekensservice.lf.kontrollerasamtyckeresponder.v1.KontrolleraSamtyckeResponseType;


public class KontrolleraSamtyckeTestProducer implements KontrolleraSamtyckeResponderInterface {

	@Override
	@WebResult(name = "KontrolleraSamtyckeResponse", targetNamespace = "urn:riv:se.apotekensservice:lf:KontrolleraSamtyckeResponder:1", partName = "parameters")
	@WebMethod(operationName = "KontrolleraSamtycke", action = "urn:riv:se.apotekensservice:lf:KontrolleraSamtyckeResponder:1:KontrolleraSamtycke")
	public KontrolleraSamtyckeResponseType kontrolleraSamtycke(KontrolleraSamtyckeRequestType parameters,
			String logicalAddress, ArgosHeaderType argosHeader) throws ApplicationException, SystemException {
		KontrolleraSamtyckeResponseType response = new KontrolleraSamtyckeResponseType();
		return response;
	}

	

}
