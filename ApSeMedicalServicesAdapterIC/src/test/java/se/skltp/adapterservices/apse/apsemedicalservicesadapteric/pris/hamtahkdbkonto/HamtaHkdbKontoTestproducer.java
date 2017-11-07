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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.pris.hamtahkdbkonto;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbkonto.v1.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbkonto.v1.rivtabp21.HamtaHkdbKontoResponderInterface;
import se.riv.inera.se.apotekensservice.pris.hamtahkdbkonto.v1.rivtabp21.SystemException;
import se.riv.se.apotekensservice.pris.hamtahkdbkontoresponder.v1.HamtaHkdbKontoRequestType;
import se.riv.se.apotekensservice.pris.hamtahkdbkontoresponder.v1.HamtaHkdbKontoResponseType;


public class HamtaHkdbKontoTestproducer implements HamtaHkdbKontoResponderInterface {

	@Override
	public HamtaHkdbKontoResponseType hamtaHkdbKonto(HamtaHkdbKontoRequestType arg0, String arg1, ArgosHeaderType arg2)
			throws ApplicationException, SystemException {
		// TODO Auto-generated method stub
		HamtaHkdbKontoResponseType response = new HamtaHkdbKontoResponseType();
		return response;
	}

}
