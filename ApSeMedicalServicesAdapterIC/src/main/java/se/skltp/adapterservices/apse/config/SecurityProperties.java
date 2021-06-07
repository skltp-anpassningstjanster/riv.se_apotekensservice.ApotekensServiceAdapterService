/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.skltp.adapterservices.apse.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.log4j.Log4j2;

import java.util.List;

@Log4j2
@Data
@NoArgsConstructor
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "apse.tls")
public class SecurityProperties {

    private String allowedIncomingProtocols;
    private String allowedOutgoingProtocols;
    private Store store;

    @Data
    public static class Store {
        private String location;
        private Producer producer;
        private Consumer consumer;
        private Truststore truststore;

        @Data
        public static class Producer {
            private String file;
            private String password;
            private String keyPassword;
        }

        @Data
        public static class Consumer {
            private String file;
            private String password;
            private String keyPassword;
            private List<String> hostNameVerifySkipList;
        }

        @Data
        public static class Truststore {
            private String file;
            private String password;
        }
    }
}