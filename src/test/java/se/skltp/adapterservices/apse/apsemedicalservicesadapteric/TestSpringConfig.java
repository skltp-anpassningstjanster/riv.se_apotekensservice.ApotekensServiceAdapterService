package se.skltp.adapterservices.apse.apsemedicalservicesadapteric;


import org.apache.camel.support.jsse.SSLContextParameters;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.info.BuildProperties;
import org.springframework.boot.test.context.SpringBootTest;
import se.skltp.adapterservices.apse.config.SSLContextParametersConfig;
import se.skltp.adapterservices.apse.config.SecurityProperties;

import static org.junit.Assert.assertEquals;
import static org.assertj.core.api.Assertions.assertThat;
@SpringBootTest
public class TestSpringConfig {

    @Autowired
    SecurityProperties securityProperties;

    @Test
    public void testOutgoingSSLContextParameters() throws Exception {
        assertThat(securityProperties).isNotNull();

        SSLContextParametersConfig ssl = new SSLContextParametersConfig(securityProperties);
        SSLContextParameters param = ssl.outgoingSSLContextParameters();

        String expected = securityProperties.getStore().getLocation()
                + securityProperties.getStore().getTruststore().getFile();
        assertThat(param.getTrustManagers().getKeyStore().getResource()).isEqualTo(expected);
    }

    @Test
    public void testOutgoingSSLContextParametersWithNullAsConsumer() throws Exception {
        assertThat(securityProperties).isNotNull();
        securityProperties.getStore().getConsumer().setFile(null);

        SSLContextParametersConfig ssl = new SSLContextParametersConfig(securityProperties);
        SSLContextParameters param = ssl.outgoingSSLContextParameters();
        assertThat(param.getKeyManagers()).isNull();
    }
    @Test
    public void testOutgoingSSLContextParametersWithEmptyConsumer() throws Exception {
        assertThat(securityProperties).isNotNull();
        securityProperties.getStore().getConsumer().setFile("");

        SSLContextParametersConfig ssl = new SSLContextParametersConfig(securityProperties);
        SSLContextParameters param = ssl.outgoingSSLContextParameters();
        assertThat(param.getKeyManagers()).isNull();
    }

    @Test
    public void testOutgoingSSLContextParametersWithNullAsTrustStore() throws Exception {
        assertThat(securityProperties).isNotNull();
        securityProperties.getStore().getTruststore().setFile(null);

        SSLContextParametersConfig ssl = new SSLContextParametersConfig(securityProperties);
        SSLContextParameters param = ssl.outgoingSSLContextParameters();
        assertThat(param.getTrustManagers()).isNull();
    }
    @Test
    public void testOutgoingSSLContextParametersWithEmptyTrustStore() throws Exception {
        assertThat(securityProperties).isNotNull();
        securityProperties.getStore().getTruststore().setFile("");

        SSLContextParametersConfig ssl = new SSLContextParametersConfig(securityProperties);
        SSLContextParameters param = ssl.outgoingSSLContextParameters();
        assertThat(param.getTrustManagers()).isNull();
    }
}
