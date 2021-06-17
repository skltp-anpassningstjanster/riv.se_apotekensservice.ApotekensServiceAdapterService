package se.skltp.adapterservices.apse.constants;

public class HttpHeaders {

    private HttpHeaders() {
        //To hide implicit public constructor. Sonar suggestion.
    }

    /**
     * HTTP Header holding producer response time, forwarded to consumer.
     * <p>
     *
     * @since VP-2.2.1
     */
    public static final String X_SKLTP_PRODUCER_RESPONSETIME = "x-skltp-prt";
    /**
     * HTTP Header holding correlation id forwarded to producer.
     * <p>
     *
     * @since VP-2.2.12
     */
    public static final String X_SKLTP_CORRELATION_ID = "x-skltp-correlation-id";
    /**
     * HTTP Header forwarded to producer. Note that header represent original consumer and should not be used for routing or authorization
     * in SKLTP VP. For routing and authorization use X_VP_SENDER_ID.
     * <p>
     *
     * @since VP-2.0
     */
    public static final String X_RIVTA_ORIGINAL_SERVICE_CONSUMER_HSA_ID = "x-rivta-original-serviceconsumer-hsaid";
    /**
     * HTTP Header x-vp-sender-id, identifies the consumer doing the actual call to SKLTP VP. The header x-vp-sender-id
     * should always exist in outbound calls for other SKLTP component that uses it.
     *
     * @since VP-2.2.3
     */
    public static final String X_VP_SENDER_ID = "x-vp-sender-id";
    /**
     * HTTP header x-vp-instance-id, carrying information regarding the VP instance id, either incoming requests
     * or outgoing. This header can be used by other VP instances to make sure VP internal http headers are not
     * processed.
     *
     * @since VP-2.2.4
     */
    public static final String X_VP_INSTANCE_ID = "x-vp-instance-id";
    /**
     * Incoming HTTP Header x-vp-auth-cert, carrying a X509 certificate, used when implementing a reverse proxy.
     *
     * @since VP-1.3
     */
    public static final String CERTIFICATE_FROM_REVERSE_PROXY 	= "x-vp-auth-cert";

    public static final String HEADER_USER_AGENT = "User-Agent";

    public static final String HEADER_CONTENT_TYPE = "Content-Type";

    public static final String SOAP_ACTION = "SOAPAction";

}