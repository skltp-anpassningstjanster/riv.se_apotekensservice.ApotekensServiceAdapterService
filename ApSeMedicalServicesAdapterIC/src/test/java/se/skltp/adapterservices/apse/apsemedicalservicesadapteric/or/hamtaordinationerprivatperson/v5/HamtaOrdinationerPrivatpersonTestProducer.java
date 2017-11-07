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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.or.hamtaordinationerprivatperson.v5;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.or.hamtaordinationerprivatperson.v5.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.or.hamtaordinationerprivatperson.v5.rivtabp21.HamtaOrdinationerPrivatpersonResponderInterface;
import se.riv.se.apotekensservice.or.hamtaordinationerprivatperson.v5.rivtabp21.SystemException;
import se.riv.se.apotekensservice.or.hamtaordinationerprivatpersonresponder.v5.HamtaOrdinationerPrivatpersonRequestType;
import se.riv.se.apotekensservice.or.hamtaordinationerprivatpersonresponder.v5.HamtaOrdinationerPrivatpersonResponseType;


public class HamtaOrdinationerPrivatpersonTestProducer implements HamtaOrdinationerPrivatpersonResponderInterface {

	@Override
	@WebResult(name = "HamtaOrdinationerPrivatpersonResponse", targetNamespace = "urn:riv:se.apotekensservice:or:HamtaOrdinationerPrivatpersonResponder:5", partName = "parameters")
    @WebMethod(operationName = "HamtaOrdinationerPrivatperson", action = "urn:riv:se.apotekensservice:or:HamtaOrdinationerPrivatpersonResponder:5:HamtaOrdinationerPrivatperson")
    public HamtaOrdinationerPrivatpersonResponseType hamtaOrdinationerPrivatperson(
        @WebParam(partName = "parameters", name = "HamtaOrdinationerPrivatperson", targetNamespace = "urn:riv:se.apotekensservice:or:HamtaOrdinationerPrivatpersonResponder:5")
        HamtaOrdinationerPrivatpersonRequestType parameters,
        @WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true)
        String logicalAddress,
        @WebParam(partName = "ArgosHeader", name = "ArgosHeader", targetNamespace = "urn:riv:inera.se.apotekensservice:argos:1", header = true)
        ArgosHeaderType argosHeader
    ) throws SystemException, ApplicationException {
		HamtaOrdinationerPrivatpersonResponseType response = new HamtaOrdinationerPrivatpersonResponseType();
//		response.setOrdinationsId(parameters.getOrdinationsId());
//		response.setUnderlagsversion(parameters.getUnderlagsversion());
		return response;
	}
}
