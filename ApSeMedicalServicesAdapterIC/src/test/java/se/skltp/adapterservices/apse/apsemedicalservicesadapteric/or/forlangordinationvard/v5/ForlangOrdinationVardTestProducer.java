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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.or.forlangordinationvard.v5;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.or.forlangordinationvard.v5.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.or.forlangordinationvard.v5.rivtabp21.ForlangOrdinationVardResponderInterface;
import se.riv.se.apotekensservice.or.forlangordinationvard.v5.rivtabp21.SystemException;
import se.riv.se.apotekensservice.or.forlangordinationvardresponder.v5.ForlangOrdinationVardRequestType;
import se.riv.se.apotekensservice.or.forlangordinationvardresponder.v5.ForlangOrdinationVardResponseType;


public class ForlangOrdinationVardTestProducer implements ForlangOrdinationVardResponderInterface {

	@Override
	@WebResult(name = "ForlangOrdinationVardResponse", targetNamespace = "urn:riv:se.apotekensservice:or:ForlangOrdinationVardResponder:5", partName = "parameters")
	@WebMethod(operationName = "ForlangOrdinationVard", action = "urn:riv:se.apotekensservice:or:ForlangOrdinationVardResponder:5:ForlangOrdinationVard")
	public ForlangOrdinationVardResponseType forlangOrdinationVard(
			@WebParam(partName = "parameters", name = "ForlangOrdinationVard", targetNamespace = "urn:riv:se.apotekensservice:or:ForlangOrdinationVardResponder:5") ForlangOrdinationVardRequestType parameters,
			@WebParam(partName = "LogicalAddress", name = "LogicalAddress", targetNamespace = "urn:riv:itintegration:registry:1", header = true) String logicalAddress,
			@WebParam(partName = "ArgosHeader", name = "ArgosHeader", targetNamespace = "urn:riv:inera.se.apotekensservice:argos:1", header = true) ArgosHeaderType argosHeader)
			throws SystemException, ApplicationException {
		
		ForlangOrdinationVardResponseType response = new ForlangOrdinationVardResponseType();
		response.setOrdinationsId(parameters.getOrdinationsId());
		response.setUnderlagsversion(parameters.getUnderlagsversion());
		return response;
	}
}
