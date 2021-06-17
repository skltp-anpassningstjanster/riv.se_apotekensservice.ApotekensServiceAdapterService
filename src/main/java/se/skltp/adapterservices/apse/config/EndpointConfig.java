/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.skltp.adapterservices.apse.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import java.util.Map;

@Log4j2
@Data
@NoArgsConstructor
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "apse.endpoint")
public class EndpointConfig {

    Map<String, Map<String, String>> outbound;
    Map<String, String> inbound;

    public void logSettings() {
        if (outbound != null) {
            outbound.forEach((key, val) -> {
                log.info("###  service: " + key );
                val.forEach((k,v) -> { 
                    log.info("###     interaction" + key + ", url: " + val);
                });
                log.info("##############");
            });
        }
    }
    
}
