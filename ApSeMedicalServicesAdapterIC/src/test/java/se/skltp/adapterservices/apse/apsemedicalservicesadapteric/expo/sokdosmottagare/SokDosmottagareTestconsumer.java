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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.expo.sokdosmottagare;

import java.net.URL;

import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.expo.sokdosmottagare.v1.rivtabp21.SokDosmottagareResponderInterface;
import se.riv.se.apotekensservice.expo.sokdosmottagareresponder.v1.SokDosmottagareRequestType;
import se.riv.se.apotekensservice.expo.sokdosmottagareresponder.v1.SokDosmottagareResponseType;

public class SokDosmottagareTestconsumer {

	private static final Logger log = LoggerFactory.getLogger(SokDosmottagareTestconsumer.class);

	private SokDosmottagareResponderInterface _service = null;

	public SokDosmottagareTestconsumer(String serviceAddress) {
		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setServiceClass(SokDosmottagareResponderInterface.class);
		proxyFactory.setAddress(serviceAddress);

		// Used for HTTPS
		SpringBusFactory bf = new SpringBusFactory();
		URL cxfConfig = SokDosmottagareTestconsumer.class.getClassLoader().getResource("cxf-test-consumer-config.xml");
		if (cxfConfig != null) {
			proxyFactory.setBus(bf.createBus(cxfConfig));
		}

		_service = (SokDosmottagareResponderInterface) proxyFactory.create();
	}

	public SokDosmottagareResponseType callService(String id, ArgosHeaderType argosHeader) throws Exception {
		log.debug("Calling sample-soap-service with id = {}", id);
		SokDosmottagareRequestType request = new SokDosmottagareRequestType();
		return _service.sokDosmottagare(request, "TEST", argosHeader);
	}
}
