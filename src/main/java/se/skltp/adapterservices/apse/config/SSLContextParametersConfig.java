package se.skltp.adapterservices.apse.config;

import org.apache.camel.support.jsse.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class SSLContextParametersConfig {
    public static final String DELIMITER = ",";
    @Bean
    public SSLContextParameters outgoingSSLContextParameters() {
        return new SSLContextParameters();
    }
}
