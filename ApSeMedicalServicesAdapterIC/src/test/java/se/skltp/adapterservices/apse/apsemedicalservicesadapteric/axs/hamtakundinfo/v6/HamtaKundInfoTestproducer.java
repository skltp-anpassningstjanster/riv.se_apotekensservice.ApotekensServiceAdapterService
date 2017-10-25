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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.axs.hamtakundinfo.v6;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import riv.se_apotekensservice.axs._4.ApoteksinformationResponse;
import riv.se_apotekensservice.axs._5.KundinformationResponse;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.axs.hamtakundinfo.v6.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.axs.hamtakundinfo.v6.rivtabp21.HamtaKundInfoResponderInterface;
import se.riv.se.apotekensservice.axs.hamtakundinfo.v6.rivtabp21.SystemException;
import se.riv.se.apotekensservice.axs.hamtakundinforesponder.v6.HamtaKundInfoRequestType;
import se.riv.se.apotekensservice.axs.hamtakundinforesponder.v6.HamtaKundInfoResponseType;


public class HamtaKundInfoTestproducer implements HamtaKundInfoResponderInterface {
	
	public static final String SSN_OK = "121212121212";

	@Override
	@WebResult(name = "HamtaKundInfoResponse", targetNamespace = "urn:riv:se.apotekensservice:axs:HamtaKundInfoResponder:5", partName = "parameters")
	@WebMethod(operationName = "HamtaKundInfo", action = "urn:riv:se.apotekensservice:axs:HamtaKundInfoResponder:5:HamtaKundInfo")
	public HamtaKundInfoResponseType hamtaKundInfo(HamtaKundInfoRequestType parameters, String logicalAddress,
			ArgosHeaderType argosHeader) throws SystemException, ApplicationException {

		HamtaKundInfoResponseType respone = new HamtaKundInfoResponseType();
		respone.setKundinformation(createKundInformation(parameters));
		respone.setApotek(createApoteksInformation(parameters));
		return respone;
	}
	private ApoteksinformationResponse createApoteksInformation(HamtaKundInfoRequestType parameters) {
		ApoteksinformationResponse response = new ApoteksinformationResponse();
		return response;
	}

	private KundinformationResponse createKundInformation(HamtaKundInfoRequestType parameters) {
		KundinformationResponse response = new KundinformationResponse();
		response.setStatusKod("OK");
		return response;
	}

}
