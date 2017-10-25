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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.axs.uppdaterasamtycke.v4;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v4.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.axs.uppdaterasamtycke.v4.rivtabp21.UppdateraSamtyckeResponderInterface;
import se.riv.se.apotekensservice.axs.uppdaterasamtyckeresponder.v4.UppdateraSamtyckeRequestType;
import se.riv.se.apotekensservice.axs.uppdaterasamtyckeresponder.v4.UppdateraSamtyckeResponseType;


public class UppdateraSamtyckeTestproducer implements UppdateraSamtyckeResponderInterface {

	@Override
	@WebResult(name = "UppdateraSamtyckeResponse", targetNamespace = "urn:riv:se.apotekensservice:axs:UppdateraSamtyckeResponder:4", partName = "parameters")
	@WebMethod(operationName = "UppdateraSamtycke", action = "urn:riv:se.apotekensservice:axs:UppdateraSamtyckeResponder:4:UppdateraSamtycke")
	public UppdateraSamtyckeResponseType uppdateraSamtycke(UppdateraSamtyckeRequestType parameters,
			String logicalAddress, ArgosHeaderType argosHeader)
					throws se.riv.inera.se.apotekensservice.axs.uppdaterasamtycke.v4.rivtabp21.SystemException,
					se.riv.inera.se.apotekensservice.axs.uppdaterasamtycke.v4.rivtabp21.ApplicationException {
		// TODO Auto-generated method stub
		UppdateraSamtyckeResponseType response = new UppdateraSamtyckeResponseType();
		return response;
	}



}
