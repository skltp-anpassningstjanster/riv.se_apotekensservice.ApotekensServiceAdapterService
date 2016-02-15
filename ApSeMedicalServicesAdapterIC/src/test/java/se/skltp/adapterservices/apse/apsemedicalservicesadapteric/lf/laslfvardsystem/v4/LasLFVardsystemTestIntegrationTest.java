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
package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.lf.laslfvardsystem.v4;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static se.skltp.adapterservices.apse.apsemedicalservicesadapteric.ApSeMedicalServicesAdapterICMuleServer.getAddress;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.soitoolkit.commons.mule.test.AbstractJmsTestUtil;
import org.soitoolkit.commons.mule.test.ActiveMqJmsTestUtil;
import org.soitoolkit.commons.mule.test.junit4.AbstractTestCase;

import se.riv.se.apotekensservice.lf.laslfvardsystemresponder.v4.LasLFVardsystemResponseType;

public class LasLFVardsystemTestIntegrationTest extends AbstractTestCase {

	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(LasLFVardsystemTestIntegrationTest.class);

	private static final String DEFAULT_SERVICE_ADDRESS = getAddress("inbound.endpoint.http.apotekensservice.lf.LasLFVardsystem.v4");
	private static final String ERROR_LOG_QUEUE = "SOITOOLKIT.LOG.ERROR";
	private AbstractJmsTestUtil jmsUtil = null;

	public LasLFVardsystemTestIntegrationTest() {

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
				+ "services/LF-LasLFVardsystem-v4-apse-service.xml,"
				+ "teststub-services/LF-LasLFVardsystem-v4-apse-teststub-service.xml";
	}

	@Test
	public void testRequestSsnWithCompleteArgosHeader() throws Exception {
		String ssn = "196308212817";
		String to = "1234567";

		LasLFVardsystemResponseType response = new LasLFVardsystemTestConsumer(DEFAULT_SERVICE_ADDRESS)
				.requestIncludingCompleteArgosInformation(ssn, to);

		assertNotNull(response);
		assertNotNull(response.getPatient());
		assertTrue(response.getPatient().getEfternamn().equals("Efternamn"));
	}

	
	@Test
	public void testEncodingIsProperThroughIntegration() throws Exception {
		String ssn = "ÅÄÖ";
		String to = "1234567";

		LasLFVardsystemResponseType response = new LasLFVardsystemTestConsumer(DEFAULT_SERVICE_ADDRESS)
				.requestIncludingCompleteArgosInformation(ssn, to);

		assertNotNull(response);
		assertNotNull(response.getPatient());
		assertEquals(ssn, response.getPatient().getPersonnummer());
	}

}
