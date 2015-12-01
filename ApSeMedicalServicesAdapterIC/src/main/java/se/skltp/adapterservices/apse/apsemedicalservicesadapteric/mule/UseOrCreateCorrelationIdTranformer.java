package se.skltp.adapterservices.apse.apsemedicalservicesadapteric.mule;

import org.mule.api.MuleMessage;
import org.mule.api.transformer.TransformerException;
import org.mule.api.transport.PropertyScope;
import org.mule.transformer.AbstractMessageTransformer;
import org.mule.util.UUID;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * UseOrCreateCorrelationIdTransformer responsible to extract correlation id or create a new one and save in a session variable.
 * 
 */
public class UseOrCreateCorrelationIdTranformer extends AbstractMessageTransformer {

	private static final Logger log = LoggerFactory.getLogger(UseOrCreateCorrelationIdTranformer.class);
	public static final String CORRELATION_ID 			= "soitoolkit_correlationId";
	public static final String INTEGRATION_SCENARIO 	= "soitoolkit_integrationScenario";
	public static final String X_SKLTP_CORRELATION_ID 	= "x-skltp-correlation-id";
	
    /**
     * Message aware transformer that handle correlation id 
     */
    @Override
    public Object transformMessage(MuleMessage message, String outputEncoding) throws TransformerException {
    		
		String correlationId = message.getProperty(X_SKLTP_CORRELATION_ID, PropertyScope.INBOUND, null);
		
		if (correlationId == null) {
			log.debug("Correlation id not found in http header create a new one!");			
			correlationId = UUID.getUUID();
		}	
		message.setProperty(CORRELATION_ID, correlationId, PropertyScope.SESSION);
        
		// Also set integrationscenario id as previously done.
		message.setProperty(INTEGRATION_SCENARIO, "npo-adapter-type-1", PropertyScope.SESSION);
		
        return message;
    }
}
