package se.skltp.adapterservices.se.apotekensservice.apsemedicalservicesadaptericteststub;

 
import org.soitoolkit.commons.mule.test.StandaloneMuleServer;

import org.soitoolkit.commons.mule.util.RecursiveResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ApSeMedicalServicesAdapterICTeststubTeststubMuleServer {


	public static final String MULE_SERVER_ID   = "ApSeMedicalServicesAdapterIC-teststub-teststub";
 

	private static final Logger logger = LoggerFactory.getLogger(ApSeMedicalServicesAdapterICTeststubTeststubMuleServer.class);
	private static final RecursiveResourceBundle rb = new RecursiveResourceBundle("ApSeMedicalServicesAdapterIC-config");

	public static void main(String[] args) throws Exception {

 
        // Configure the mule-server
        // Note: do not activate the "soitoolkit-teststubs" profile here since it sets a
        // system property, which is JVM global and may activate all teststubs in a Mule-instance.
        // Teststubs are always loaded by the top-level config.xml file.
        StandaloneMuleServer muleServer = new StandaloneMuleServer(MULE_SERVER_ID, false, true);
 
        // Start the server
		muleServer.run();
	}

    /**
     * Address based on usage of the servlet-transport and a config-property for the URI-part
     * 
     * @param serviceUrlPropertyName
     * @return
     */
    public static String getAddress(String serviceUrlPropertyName) {

        String url = rb.getString(serviceUrlPropertyName);

	    logger.info("URL: {}", url);
    	return url;
 
    }	
}