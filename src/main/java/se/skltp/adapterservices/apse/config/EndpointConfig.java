/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.skltp.adapterservices.apse.config;

import lombok.Data;
import org.apache.logging.log4j.Level;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Log4j2
@Data
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "apse.endpoint")
public class EndpointConfig {

    Map<String, Map<String, String>> outbound;
    Map<String, String> inbound;
    private static Level endpointConfigLoggingLevel = Level.DEBUG;

    EndpointConfig() {
        logSettings();
    }

    public void logSettings() {
        if (log.isEnabled(endpointConfigLoggingLevel)) {
            if (outbound != null) {
                outbound.forEach((key, val) -> {
                    log.log(endpointConfigLoggingLevel, "###  service: " + key);
                    val.forEach((k, v) -> {
                        log.log(endpointConfigLoggingLevel, "###     interaction" + key + ", url: " + val);
                    });
                    log.log(endpointConfigLoggingLevel, "##############");
                });
            }
        }
    }
}
