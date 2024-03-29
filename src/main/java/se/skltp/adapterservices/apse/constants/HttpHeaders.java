/**
 * Copyright (c) 2013 Center for eHalsa i samverkan (CeHis).
 * <http://cehis.se/>
 * <p>
 * This file is part of SKLTP.
 * <p>
 * This library is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 2.1 of the License, or (at your option) any later version.
 * <p>
 * This library is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 * <p>
 * You should have received a copy of the GNU Lesser General Public
 * License along with this library; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301  USA
 */
package se.skltp.adapterservices.apse.constants;

public class HttpHeaders {

    /**
     * HTTP Header holding producer response time, forwarded to consumer.
     * <p>
     */
    public static final String X_SKLTP_PRODUCER_RESPONSETIME = "x-skltp-prt";
    /**
     * HTTP Header holding correlation id forwarded to producer.
     * <p>
     */
    public static final String X_SKLTP_CORRELATION_ID = "x-skltp-correlation-id";
    /**
     * HTTP Header forwarded to producer. Note that header represent original consumer and should not be used for routing or authorization
     * in SKLTP VP. For routing and authorization use X_VP_SENDER_ID.
     * <p>
     */
    public static final String X_RIVTA_ORIGINAL_SERVICE_CONSUMER_HSA_ID = "x-rivta-original-serviceconsumer-hsaid";
    /**
     * HTTP Header x-vp-sender-id, identifies the consumer doing the actual call to SKLTP VP. The header x-vp-sender-id
     * should always exist in outbound calls for other SKLTP component that uses it.
     */
    public static final String X_VP_SENDER_ID = "x-vp-sender-id";
    /**
     * HTTP header x-vp-instance-id, carrying information regarding the VP instance id, either incoming requests
     * or outgoing. This header can be used by other VP instances to make sure VP internal http headers are not
     * processed.
     */
    public static final String X_VP_INSTANCE_ID = "x-vp-instance-id";
    /**
     * Incoming HTTP Header x-vp-auth-cert, carrying a X509 certificate, used when implementing a reverse proxy.
     */
    public static final String CERTIFICATE_FROM_REVERSE_PROXY = "x-vp-auth-cert";
    public static final String HEADER_USER_AGENT = "User-Agent";
    public static final String HEADER_CONTENT_TYPE = "Content-Type";
    public static final String SOAP_ACTION = "SOAPAction";

    private HttpHeaders() {
        //To hide implicit public constructor. Sonar suggestion.
    }

}
