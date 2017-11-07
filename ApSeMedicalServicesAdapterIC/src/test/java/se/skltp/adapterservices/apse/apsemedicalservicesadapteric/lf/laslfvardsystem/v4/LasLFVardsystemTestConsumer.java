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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lf.laslfvardsystem.v4;

import static se.skltp.adapterservices.apse.apsemedicalservicesadapteric.ArgosHeaderTestUtil.createOrganizationArgosHeader;

import java.net.URL;

import org.apache.cxf.bus.spring.SpringBusFactory;
import org.apache.cxf.jaxws.JaxWsProxyFactoryBean;
import se.riv.inera.se.apotekensservice.argos.v1.ArgosHeaderType;
import se.riv.inera.se.apotekensservice.lf.laslfvardsystem.v4.rivtabp21.ApplicationException;
import se.riv.inera.se.apotekensservice.lf.laslfvardsystem.v4.rivtabp21.LasLFVardsystemResponderInterface;
import se.riv.inera.se.apotekensservice.lf.laslfvardsystem.v4.rivtabp21.SystemException;
import se.riv.se.apotekensservice.lf.laslfvardsystemresponder.v4.LasLFVardsystemRequestType;
import se.riv.se.apotekensservice.lf.laslfvardsystemresponder.v4.LasLFVardsystemResponseType;

public class LasLFVardsystemTestConsumer {

	private LasLFVardsystemResponderInterface _service = null;

	public LasLFVardsystemTestConsumer(String serviceAddress) {
		JaxWsProxyFactoryBean proxyFactory = new JaxWsProxyFactoryBean();
		proxyFactory.setServiceClass(LasLFVardsystemResponderInterface.class);
		proxyFactory.setAddress(serviceAddress);

		// Used for HTTPS
		SpringBusFactory bf = new SpringBusFactory();
		URL cxfConfig = LasLFVardsystemTestConsumer.class.getClassLoader().getResource("cxf-test-consumer-config.xml");
		if (cxfConfig != null) {
			proxyFactory.setBus(bf.createBus(cxfConfig));
		}

		_service = (LasLFVardsystemResponderInterface) proxyFactory.create();
	}

	public LasLFVardsystemResponseType requestIncludingCompleteArgosInformation(String socialSecurityNumber, String to) 
			throws SystemException, ApplicationException
	{
		ArgosHeaderType argosHeader = createOrganizationArgosHeader();
		String logicalAddress = to;
		LasLFVardsystemRequestType request = createLasLFVardsystemRequest(socialSecurityNumber);
		return _service.lasLFVardsystem(request, logicalAddress, argosHeader);
	}

	private LasLFVardsystemRequestType createLasLFVardsystemRequest(String socialSecurityNumber) {
		LasLFVardsystemRequestType request = new LasLFVardsystemRequestType();
		request.setPersonnummer(socialSecurityNumber);
		return request;
	}

}
