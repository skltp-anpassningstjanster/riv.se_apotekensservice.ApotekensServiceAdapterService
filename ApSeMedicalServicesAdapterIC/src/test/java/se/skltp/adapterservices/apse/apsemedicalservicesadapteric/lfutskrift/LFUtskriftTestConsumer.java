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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lfutskrift;

import java.net.URL;

import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.riv.inera.se.apotekensservice.lf.lfutskrift.v1.rivtabp21.LFUtskriftResponderInterface;
import se.riv.se.apotekensservice.lf.lfutskriftresponder.v1.LFUtskriftRequestType;
import se.riv.se.apotekensservice.lf.lfutskriftresponder.v1.LFUtskriftResponseType;
import se.riv.inera.se.apotekensservice.lf.lfutskrift.v1.rivtabp21.SystemException;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.lf.lfutskrift.v1.rivtabp21.ApplicationException;

import static se.skltp.adapterservices.apse.apsemedicalservicesadapteric.ApSeMedicalServicesAdapterICMuleServer.getAddress;

public class LFUtskriftTestConsumer {

	private static final Logger log = LoggerFactory.getLogger(LFUtskriftTestConsumer.class);

	private LFUtskriftResponderInterface _service = null;

	public LFUtskriftTestConsumer(String serviceAddress) {
		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setServiceClass(LFUtskriftResponderInterface.class);
		proxyFactory.setAddress(serviceAddress);

		// Used for HTTPS
		SpringBusFactory bf = new SpringBusFactory();
		URL cxfConfig = LFUtskriftTestConsumer.class.getClassLoader().getResource("cxf-test-consumer-config.xml");
		if (cxfConfig != null) {
			proxyFactory.setBus(bf.createBus(cxfConfig));
		}

		_service = (LFUtskriftResponderInterface) proxyFactory.create();
	}

	

	public LFUtskriftResponseType citicenRequest(LFUtskriftRequestType request, String logicalAddress, ArgosHeaderType argosHeader)
			throws SystemException, ApplicationException {

		return _service.lfUtskrift(request, logicalAddress, argosHeader);
	}

	public LFUtskriftResponseType organizationRequest(LFUtskriftRequestType request, String logicalAddress,
			ArgosHeaderType argosHeader) throws SystemException, ApplicationException {

		return _service.lfUtskrift(request, logicalAddress, argosHeader);
	}
	
	public static void main(String[] args) throws Exception {
		String serviceAddress = getAddress("inbound.endpoint.apotekensservice.lf.LFUtskrift");
		String glnkod = "1234567890";

		LFUtskriftTestConsumer consumer = new LFUtskriftTestConsumer(serviceAddress);
		LFUtskriftResponseType response = consumer.callService(glnkod);
		log.info("Returned value = " + response.getPatient().getPersonnummer());
	}

	public LFUtskriftResponseType callService(String id) throws Exception {
		log.debug("Calling sample-soap-service with id = {}", id);
		LFUtskriftRequestType request = new LFUtskriftRequestType();
		request.setForskrivarkod(id);
		return _service.lfUtskrift(request, "TEST", null);
	}

}
