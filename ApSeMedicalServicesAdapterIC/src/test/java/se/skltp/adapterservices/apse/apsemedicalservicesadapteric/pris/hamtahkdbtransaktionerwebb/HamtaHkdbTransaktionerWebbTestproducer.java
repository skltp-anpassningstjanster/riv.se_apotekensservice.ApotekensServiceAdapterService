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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.pris.hamtahkdbtransaktionerwebb;

import javax.jws.WebMethod;
import javax.jws.WebResult;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbtransaktionerwebb.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbtransaktionerwebb.v1.rivtabp21.HamtaHkdbTransaktionerWebbResponderInterface;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbtransaktionerwebb.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.pris.hamtahkdbtransaktionerwebbresponder.v1.HamtaHkdbTransaktionerWebbRequestType;
import se.riv.se.apotekensservice.pris.hamtahkdbtransaktionerwebbresponder.v1.HamtaHkdbTransaktionerWebbResponseType;



public class HamtaHkdbTransaktionerWebbTestproducer implements HamtaHkdbTransaktionerWebbResponderInterface {

	@Override
	@WebResult(name = "HamtaHkdbTransaktionerWebbResponse", targetNamespace = "urn:riv:se.apotekensservice:pris:HamtaHkdbTransaktionerWebbResponder:1", partName = "parameters")
	@WebMethod(operationName = "HamtaHkdbTransaktionerWebb", action = "urn:riv:se.apotekensservice:pris:HamtaHkdbTransaktionerWebbResponder:1:HamtaHkdbTransaktionerWebb")
	public HamtaHkdbTransaktionerWebbResponseType hamtaHkdbTransaktionerWebb(HamtaHkdbTransaktionerWebbRequestType arg0,
			String arg1, ArgosHeaderType arg2) throws SystemException, ApplicationException {
		// TODO Auto-generated method stub
		HamtaHkdbTransaktionerWebbResponseType response = new HamtaHkdbTransaktionerWebbResponseType();
		return response;
	}


}
