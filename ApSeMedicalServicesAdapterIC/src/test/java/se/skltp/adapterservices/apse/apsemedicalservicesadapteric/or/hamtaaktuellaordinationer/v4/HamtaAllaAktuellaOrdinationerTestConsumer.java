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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.or.hamtaaktuellaordinationer.v4;

import java.net.URL;

import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.or.hamtaaktuellaordinationer.v4.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.or.hamtaaktuellaordinationer.v4.rivtabp21.HamtaAktuellaOrdinationerResponderInterface;
import se.riv.se.apotekensservice.or.hamtaaktuellaordinationer.v4.rivtabp21.SystemException;
import se.riv.se.apotekensservice.or.hamtaaktuellaordinationerresponder.v4.HamtaAktuellaOrdinationerRequestType;
import se.riv.se.apotekensservice.or.hamtaaktuellaordinationerresponder.v4.HamtaAktuellaOrdinationerResponseType;
import se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lfutskrift.LFUtskriftTestConsumer;

public class HamtaAllaAktuellaOrdinationerTestConsumer {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LFUtskriftTestConsumer.class);

	private HamtaAktuellaOrdinationerResponderInterface _service;
	
	public HamtaAllaAktuellaOrdinationerTestConsumer(String serviceAddress) {
		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setServiceClass(HamtaAktuellaOrdinationerResponderInterface.class);
		proxyFactory.setAddress(serviceAddress);

		// Used for HTTPS
		SpringBusFactory bf = new SpringBusFactory();
		URL cxfConfig = HamtaAllaAktuellaOrdinationerTestConsumer.class.getClassLoader().getResource("cxf-test-consumer-config.xml");
		if (cxfConfig != null) {
			proxyFactory.setBus(bf.createBus(cxfConfig));
		}

		_service = (HamtaAktuellaOrdinationerResponderInterface) proxyFactory.create();
	}

	public HamtaAktuellaOrdinationerResponseType requestIncludingCompleteArgosInformation(String socialSecurityNumber,
			String logicalAddress) throws ApplicationException, SystemException {
		ArgosHeaderType argosHeader = createCompleteArgosHeader();
		HamtaAktuellaOrdinationerRequestType requestSocialSecurityNumber = createSocialSecurityNumberRequest(socialSecurityNumber);
		return _service.hamtaAktuellaOrdinationer(requestSocialSecurityNumber, logicalAddress, argosHeader);
	}

	private HamtaAktuellaOrdinationerRequestType createSocialSecurityNumberRequest(String socialSecurityNumber) {
		HamtaAktuellaOrdinationerRequestType aktuellaOrdinationerRequest = new HamtaAktuellaOrdinationerRequestType();
		aktuellaOrdinationerRequest.setPersonnummer(socialSecurityNumber);
		return aktuellaOrdinationerRequest;
	}

	private ArgosHeaderType createCompleteArgosHeader() {
		ArgosHeaderType argosHeader = new ArgosHeaderType();
		argosHeader.setArbetsplatskod("1234567890");
		argosHeader.setArbetsplatsnamn("Sjukhuset");
		argosHeader.setBefattningskod("123456");
		argosHeader.setEfternamn("Läkare");
		argosHeader.setFornamn("Lars");
		argosHeader.setForskrivarkod("1111129");
		argosHeader.setHsaID("TSE6565656565-1003");
		argosHeader.setKatalog("HSA");
		argosHeader.setLegitimationskod("1");
		argosHeader.setOrganisationsnummer("1234567890");
		argosHeader.setPostadress("Gatan 1");
		argosHeader.setPostnummer("11111");
		argosHeader.setPostort("Staden");
		argosHeader.setRequestId("123456");
		argosHeader.setRollnamn("FORSKRIVARE");
		argosHeader.setSystemIp("192.0.0.1");
		argosHeader.setSystemnamn("Melior");
		argosHeader.setSystemversion("1.0");
		argosHeader.setTelefonnummer("08-1234567");
		argosHeader.setYrkesgrupp("LK");
		return argosHeader;
	}
}
