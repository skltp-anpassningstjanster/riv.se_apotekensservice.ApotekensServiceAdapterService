/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.skltp.adapterservice.apse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.support.jsse.KeyManagersParameters;
import org.apache.camel.support.jsse.KeyStoreParameters;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.apache.camel.support.jsse.TrustManagersParameters;
import static org.apache.commons.lang.StringUtils.strip;

@Log4j2
@Data
@NoArgsConstructor
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "ssl.outbound.truststore")
public class SslConfig {

    String type;
    String file;
    String password;

    public SSLContextParameters getSSLContext() {
        KeyStoreParameters ksp = new KeyStoreParameters();
        ksp.setResource(file);
        ksp.setType(type);
        ksp.setPassword(password);
        
        KeyManagersParameters kmp = new KeyManagersParameters();
        kmp.setKeyStore(ksp);
        kmp.setKeyPassword(password);

        TrustManagersParameters trustManagersParameters = new TrustManagersParameters();
        trustManagersParameters.setKeyStore(ksp);

        SSLContextParameters scp = new SSLContextParameters();
        scp.setKeyManagers(kmp);
        scp.setTrustManagers(trustManagersParameters);
        return scp;
    }
}
