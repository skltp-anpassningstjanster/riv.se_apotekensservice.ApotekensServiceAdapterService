package se.skltp.adapterservices.apse.config;

import org.apache.camel.support.jsse.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.nio.file.FileSystems;
import java.util.ArrayList;
import java.util.List;

@Configuration
public class SSLContextParametersConfig {

    public static final String DELIMITER = ",";

    @Autowired
    public SSLContextParametersConfig(SecurityProperties secProp) {
        securityProperties = secProp;
    }

    SecurityProperties securityProperties;

    @Bean
    public SSLContextParameters incomingSSLContextParameters() {
        KeyStoreParameters ksp = new KeyStoreParameters();
        KeyManagersParameters kmp = new KeyManagersParameters();
        FileSystems.getDefault().getPath("logs", "access.log");

        SSLContextParameters sslContextParameters = new SSLContextParameters();
        String producerCertFile = securityProperties.getStore().getProducer().getFile();
        String incomingProtocols = securityProperties.getAllowedOutgoingProtocols();
        if(producerCertFile != null && !producerCertFile.isEmpty()) {
            ksp.setResource(securityProperties.getStore().getLocation() + securityProperties.getStore().getProducer().getFile());
            ksp.setPassword(securityProperties.getStore().getProducer().getPassword());

            kmp.setKeyPassword(securityProperties.getStore().getProducer().getKeyPassword());
            kmp.setKeyStore(ksp);
            sslContextParameters.setKeyManagers(kmp);
        }

        TrustManagersParameters trustManagersParameters = createTrustManagerParameters();
        sslContextParameters.setTrustManagers(trustManagersParameters);

        if (incomingProtocols != null && !incomingProtocols.isEmpty()) {
            SecureSocketProtocolsParameters secureProtocolParameters = createSecureProtocolParameters(securityProperties.getAllowedIncomingProtocols());
            sslContextParameters.setSecureSocketProtocols(secureProtocolParameters);
        }
        return sslContextParameters;
    }

    @Bean
    public SSLContextParameters outgoingSSLContextParameters() {
        KeyStoreParameters ksp = new KeyStoreParameters();
        SSLContextParameters sslContextParameters = new SSLContextParameters();

        String trustStoreFile = securityProperties.getStore().getTruststore().getFile();
        String consumerCertFile = securityProperties.getStore().getConsumer().getFile();
        String outgoingProtocols = securityProperties.getAllowedOutgoingProtocols();

        if(consumerCertFile != null && !consumerCertFile.isEmpty()) {
            ksp.setResource(securityProperties.getStore().getLocation() + securityProperties.getStore().getConsumer().getFile());
            ksp.setPassword(securityProperties.getStore().getConsumer().getPassword());
            KeyManagersParameters kmp = new KeyManagersParameters();
            kmp.setKeyPassword(securityProperties.getStore().getConsumer().getKeyPassword());
            kmp.setKeyStore(ksp);

            sslContextParameters.setKeyManagers(kmp);
        }
        if(trustStoreFile != null && !trustStoreFile.isEmpty()) {
            TrustManagersParameters trustManagersParameters = createTrustManagerParameters();
            sslContextParameters.setTrustManagers(trustManagersParameters);
        }
        if (outgoingProtocols != null && !outgoingProtocols.isEmpty()) {
            SecureSocketProtocolsParameters sspp = createSecureProtocolParameters(securityProperties.getAllowedOutgoingProtocols());
            sslContextParameters.setSecureSocketProtocols(sspp);
        }
        return sslContextParameters;
    }

    private SecureSocketProtocolsParameters createSecureProtocolParameters(String allowedProtocolsString) {
        SecureSocketProtocolsParameters secureSocketProtocolsParameters = new SecureSocketProtocolsParameters();
        List<String> allowedProtocols = new ArrayList<>();
        for (String protocol : allowedProtocolsString.split(DELIMITER)) {
            if (!protocol.trim().isEmpty()) {
                allowedProtocols.add(protocol);
            }
        }
        secureSocketProtocolsParameters.setSecureSocketProtocol(allowedProtocols);
        return secureSocketProtocolsParameters;
    }

    private TrustManagersParameters createTrustManagerParameters() {
        KeyStoreParameters tsp = new KeyStoreParameters();
        tsp.setResource(securityProperties.getStore().getLocation() + securityProperties.getStore().getTruststore().getFile());
        tsp.setPassword(securityProperties.getStore().getTruststore().getPassword());
        TrustManagersParameters tmp = new TrustManagersParameters();
        tmp.setKeyStore(tsp);
        return tmp;
    }


}
