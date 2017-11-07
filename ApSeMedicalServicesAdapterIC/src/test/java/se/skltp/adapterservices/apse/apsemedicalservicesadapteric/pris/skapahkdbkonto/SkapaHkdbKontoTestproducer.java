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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.pris.skapahkdbkonto;

import se.riv.inera.se.apotekensservice.pris.skapahkdbkonto.v4.rivtabp21.SkapaHkdbKontoResponderInterface;
import se.riv.se.apotekensservice.pris.skapahkdbkontoresponder.v4.SkapaHkdbKontoRequestType;
import se.riv.se.apotekensservice.pris.skapahkdbkontoresponder.v4.SkapaHkdbKontoResponseType;

public class SkapaHkdbKontoTestproducer implements SkapaHkdbKontoResponderInterface {

	@Override
	public SkapaHkdbKontoResponseType skapaHkdbKonto(SkapaHkdbKontoRequestType arg0, String arg1,
			riv.inera_se_apotekensservice.argos._1.ArgosHeaderType arg2)
					throws se.riv.inera.se.apotekensservice.pris.skapahkdbkonto.v4.rivtabp21.SystemException,
					se.riv.inera.se.apotekensservice.pris.skapahkdbkonto.v4.rivtabp21.ApplicationException {
		// TODO Auto-generated method stub
		SkapaHkdbKontoResponseType response = new SkapaHkdbKontoResponseType();
		return response;
	}

}
