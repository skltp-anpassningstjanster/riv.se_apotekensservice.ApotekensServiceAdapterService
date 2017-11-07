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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.or.hamtaordinationerprivatperson.v6;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import riv.se_apotekensservice.or.hamtaordinationerprivatpersonresponder._6.HamtaOrdinationerPrivatpersonRequestType;
import riv.se_apotekensservice.or.hamtaordinationerprivatpersonresponder._6.HamtaOrdinationerPrivatpersonResponseType;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.or.hamtaordinationerprivatperson.v6.rivtabp21.HamtaOrdinationerPrivatpersonResponderInterface;


public class HamtaOrdinationerPrivatpersonTestProducer implements HamtaOrdinationerPrivatpersonResponderInterface {

	@Override
	@WebResult(name = "HamtaOrdinationerPrivatpersonResponse", targetNamespace = "urn:riv:se.apotekensservice:or:HamtaOrdinationerPrivatpersonResponder:6", partName = "parameters")
    @WebMethod(operationName = "HamtaOrdinationerPrivatperson", action = "urn:riv:se.apotekensservice:or:HamtaOrdinationerPrivatpersonResponder:6:HamtaOrdinationerPrivatperson")

	public HamtaOrdinationerPrivatpersonResponseType hamtaOrdinationerPrivatperson(
			HamtaOrdinationerPrivatpersonRequestType parameters, String logicalAddress, ArgosHeaderType argosHeader) {
	HamtaOrdinationerPrivatpersonResponseType response = new HamtaOrdinationerPrivatpersonResponseType();
	return response;
	}
}
