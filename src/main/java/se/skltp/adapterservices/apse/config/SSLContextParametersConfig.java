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
    SecurityProperties securityProperties;

    @Bean
    public SSLContextParameters incomingSSLContextParameters() {
        KeyStoreParameters ksp = new KeyStoreParameters();
        KeyManagersParameters kmp = new KeyManagersParameters();
        FileSystems.getDefault().getPath("logs", "access.log");
        ksp.setResource(securityProperties.getStore().getLocation() + securityProperties.getStore().getProducer().getFile());
        ksp.setPassword(securityProperties.getStore().getProducer().getPassword());

        kmp.setKeyPassword(securityProperties.getStore().getProducer().getKeyPassword());
        kmp.setKeyStore(ksp);

        SSLContextParameters sslContextParameters = new SSLContextParameters();
        sslContextParameters.setKeyManagers(kmp);

        TrustManagersParameters trustManagersParameters = createTrustManagerParameters();
        sslContextParameters.setTrustManagers(trustManagersParameters);

        SecureSocketProtocolsParameters secureProtocolParameters = createSecureProtocolParameters(securityProperties.getAllowedIncomingProtocols());
        sslContextParameters.setSecureSocketProtocols(secureProtocolParameters);
        return sslContextParameters;
    }

    @Bean
    public SSLContextParameters outgoingSSLContextParameters() {
        KeyStoreParameters ksp = new KeyStoreParameters();
        ksp.setResource(securityProperties.getStore().getLocation() + securityProperties.getStore().getConsumer().getFile());
        ksp.setPassword(securityProperties.getStore().getConsumer().getPassword());
        KeyManagersParameters kmp = new KeyManagersParameters();
        kmp.setKeyPassword(securityProperties.getStore().getConsumer().getKeyPassword());
        kmp.setKeyStore(ksp);

        SSLContextParameters sslContextParameters = new SSLContextParameters();
        sslContextParameters.setKeyManagers(kmp);

        TrustManagersParameters trustManagersParameters = createTrustManagerParameters();
        sslContextParameters.setTrustManagers(trustManagersParameters);


        SecureSocketProtocolsParameters sspp = createSecureProtocolParameters(securityProperties.getAllowedOutgoingProtocols());
        sslContextParameters.setSecureSocketProtocols(sspp);
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