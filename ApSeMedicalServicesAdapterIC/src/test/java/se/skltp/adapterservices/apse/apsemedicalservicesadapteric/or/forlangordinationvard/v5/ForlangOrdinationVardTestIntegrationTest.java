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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.or.forlangordinationvard.v5;

import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static se.skltp.adapterservices.apse.apsemedicalservicesadapteric.ApSeMedicalServicesAdapterICMuleServer.getAddress;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.test.AbstractJmsTestUtil;
import org.soitoolkit.commons.mule.test.ActiveMqJmsTestUtil;
import org.soitoolkit.commons.mule.test.junit4.AbstractTestCase;

import se.riv.se.apotekensservice.or.forlangordinationvardresponder.v5.ForlangOrdinationVardResponseType;

public class ForlangOrdinationVardTestIntegrationTest extends AbstractTestCase {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(ForlangOrdinationVardTestIntegrationTest.class);

	private static final String DEFAULT_SERVICE_ADDRESS = getAddress("inbound.endpoint.http.apotekensservice.or.ForlangOrdinationVard.v5");
	private static final String ERROR_LOG_QUEUE = "SOITOOLKIT.LOG.ERROR";
	private AbstractJmsTestUtil jmsUtil = null;

	public ForlangOrdinationVardTestIntegrationTest() {

		// Only start up Mule once toinbound.endpoint.http.apotekensservice.lf.LasLFVardsystem.v4 make the tests run faster...
		// Set to false if tests interfere with each other when Mule is started
		// only once.
		setDisposeContextPerClass(true);
	}

	@Override
	protected void doSetUp() throws Exception {
		super.doSetUp();

		doSetUpJms();
	}

	private void doSetUpJms() {
		// TODO: Fix lazy init of JMS connection et al so that we can create
		// jmsutil in the declaration
		// (The embedded ActiveMQ queue manager is not yet started by Mule when
		// jmsutil is delcared...)
		if (jmsUtil == null)
			jmsUtil = new ActiveMqJmsTestUtil();

		// Clear queues used for error handling
		jmsUtil.clearQueues(ERROR_LOG_QUEUE);
	}

	@Override
	protected String getConfigResources() {
		return "soitoolkit-mule-jms-connector-activemq-embedded.xml," + "ApSeMedicalServicesAdapterIC-common.xml,"
				+ "services/OR-ForlangOrdinationVard-v5-apse-service.xml,"
				+ "teststub-services/OR-ForlangOrdinationVard-v5-apse-teststub-service.xml";
	}

	@Test
	public void testRequestWithCompleteArgosHeader() throws Exception {
		String to = "1234567";

		ForlangOrdinationVardResponseType response = new ForlangOrdinationVardTestConsumer(DEFAULT_SERVICE_ADDRESS)
				.requestIncludingCompleteArgosInformation(to);

		assertNotNull(response);
		assertTrue(response.getOrdinationsId() == 123);
	}

	
}
