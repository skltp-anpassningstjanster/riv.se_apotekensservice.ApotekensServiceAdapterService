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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.or.forlangordinationvard.v5;

import static se.skltp.adapterservices.apse.apsemedicalservicesadapteric.ArgosHeaderTestUtil.createOrganizationArgosHeader;

import java.net.URL;

import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.se.apotekensservice.or.forlangordinationvard.v5.rivtabp21.ApplicationException;
import se.riv.se.apotekensservice.or.forlangordinationvard.v5.rivtabp21.SystemException;
import se.riv.se.apotekensservice.or.forlangordinationvard.v5.rivtabp21.ForlangOrdinationVardResponderInterface;
import se.riv.se.apotekensservice.or.forlangordinationvardresponder.v5.ForlangOrdinationVardRequestType;
import se.riv.se.apotekensservice.or.forlangordinationvardresponder.v5.ForlangOrdinationVardResponseType;

public class ForlangOrdinationVardTestConsumer {

	private static final Logger log = LoggerFactory.getLogger(ForlangOrdinationVardTestConsumer.class);

	private ForlangOrdinationVardResponderInterface _service = null;

	public ForlangOrdinationVardTestConsumer(String serviceAddress) {
		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setServiceClass(ForlangOrdinationVardResponderInterface.class);
		proxyFactory.setAddress(serviceAddress);

		// Used for HTTPS
		SpringBusFactory bf = new SpringBusFactory();
		URL cxfConfig = ForlangOrdinationVardTestConsumer.class.getClassLoader().getResource("cxf-test-consumer-config.xml");
		if (cxfConfig != null) {
			proxyFactory.setBus(bf.createBus(cxfConfig));
		}

		_service = (ForlangOrdinationVardResponderInterface) proxyFactory.create();
	}

	public ForlangOrdinationVardResponseType requestIncludingCompleteArgosInformation(String to) 
			throws SystemException, ApplicationException
	{
		ArgosHeaderType argosHeader = createOrganizationArgosHeader();
		String logicalAddress = to;
		ForlangOrdinationVardRequestType request = createLasLFVardsystemRequest();
		return _service.forlangOrdinationVard(request, logicalAddress, argosHeader);
	}

	private ForlangOrdinationVardRequestType createLasLFVardsystemRequest() {
		ForlangOrdinationVardRequestType request = new ForlangOrdinationVardRequestType();
		request.setOrdinationsId(123);
		return request;
	}

}
