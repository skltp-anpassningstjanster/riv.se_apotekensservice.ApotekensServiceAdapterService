package se.skltp.adapterservices.apse.apsemedicalservicesadapteric;


import org.apache.camel.support.jsse.SSLContextParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import se.skltp.adapterservices.apse.config.SSLContextParametersConfig;
import se.skltp.adapterservices.apse.config.SecurityProperties;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class TestSpringConfig {

    @Autowired
    SecurityProperties securityProperties;

    @Test
    public void testOutgoingSSLContextParameters() throws Exception {
        assert( securityProperties != null);

        SSLContextParametersConfig ssl = new SSLContextParametersConfig(securityProperties);
        SSLContextParameters param = ssl.outgoingSSLContextParameters();

        Assertions.assertEquals(securityProperties.getStore().getLocation() + securityProperties.getStore().getTruststore().getFile(), param.getTrustManagers().getKeyStore().getResource());
    }

    @Test
    public void testOutgoingSSLContextParameters2() throws Exception {
        assert( securityProperties != null);
        securityProperties.getStore().getConsumer().setFile(null);

        SSLContextParametersConfig ssl = new SSLContextParametersConfig(securityProperties);
        SSLContextParameters param = ssl.outgoingSSLContextParameters();
        Assertions.assertEquals(null, param.getKeyManagers());
    }

    @Test
    public void testOutgoingSSLContextParameters3() throws Exception {
        assert( securityProperties != null);
        securityProperties.getStore().getTruststore().setFile("");

        SSLContextParametersConfig ssl = new SSLContextParametersConfig(securityProperties);
        SSLContextParameters param = ssl.outgoingSSLContextParameters();
        Assertions.assertEquals(null, param.getTrustManagers());
    }


}
