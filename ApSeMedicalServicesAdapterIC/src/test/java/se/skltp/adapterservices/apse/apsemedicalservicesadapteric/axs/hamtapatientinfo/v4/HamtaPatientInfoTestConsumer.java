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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.axs.hamtapatientinfo.v4;

import static se.skltp.adapterservices.apse.apsemedicalservicesadapteric.ArgosHeaderTestUtil.createOrganizationArgosHeader;

import java.net.URL;

import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.axs.hamtapatientinfo.v4.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.axs.hamtapatientinfo.v4.rivtabp21.HamtaPatientInfoResponderInterface;
import se.riv.se.apotekensservice.axs.hamtapatientinfo.v4.rivtabp21.SystemException;
import se.riv.se.apotekensservice.axs.hamtapatientinforesponder.v4.HamtaPatientInfoRequestType;
import se.riv.se.apotekensservice.axs.hamtapatientinforesponder.v4.HamtaPatientInfoResponseType;

public class HamtaPatientInfoTestConsumer {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(HamtaPatientInfoTestConsumer.class);

	private HamtaPatientInfoResponderInterface _service = null;

	public HamtaPatientInfoTestConsumer(String serviceAddress) {
		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setServiceClass(HamtaPatientInfoResponderInterface.class);
		proxyFactory.setAddress(serviceAddress);

		// Used for HTTPS
		SpringBusFactory bf = new SpringBusFactory();
		URL cxfConfig = HamtaPatientInfoTestConsumer.class.getClassLoader().getResource("cxf-test-consumer-config.xml");
		if (cxfConfig != null) {
			proxyFactory.setBus(bf.createBus(cxfConfig));
		}

		_service = (HamtaPatientInfoResponderInterface) proxyFactory.create();
	}

	public HamtaPatientInfoResponseType requestIncludingCompleteArgosInformation(String socialSecurityNumber, String logicalAddress)
			throws SystemException, ApplicationException {
		ArgosHeaderType argosHeader = createOrganizationArgosHeader();
		HamtaPatientInfoRequestType requestSocialSecurityNumber = createSocialSecurityNumberRequest(socialSecurityNumber);
		return _service.hamtaPatientInfo(requestSocialSecurityNumber, logicalAddress, argosHeader);
	}

	private HamtaPatientInfoRequestType createSocialSecurityNumberRequest(String socialSecurityNumber) {
		HamtaPatientInfoRequestType aktuellaOrdinationerRequest = new HamtaPatientInfoRequestType();
		aktuellaOrdinationerRequest.setPersonnummer(socialSecurityNumber);
		return aktuellaOrdinationerRequest;
	}

}
