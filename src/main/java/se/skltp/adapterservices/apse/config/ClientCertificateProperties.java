package se.skltp.adapterservices.apse.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "apse.client-certificate")
public class ClientCertificateProperties {

    boolean useClientCertificate;
    String keystoreFile;
    String keystorePassword;
    String keyPassword;
}
