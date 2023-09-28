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
}
