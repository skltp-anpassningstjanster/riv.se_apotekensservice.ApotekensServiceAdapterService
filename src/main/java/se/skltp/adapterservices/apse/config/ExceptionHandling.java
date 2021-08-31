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
@ConfigurationProperties(prefix = "apse.soapfault")
public class ExceptionHandling {
    private Map<String, Integer> httpStatus;
    private int defaultHttpStatus = 500;

    public int getHttpStatus(String key) {
        return getHttpStatus(key, defaultHttpStatus);
    }

    public int getHttpStatus(String key, int defaultValue) {
        if (httpStatus != null) {
            return httpStatus.getOrDefault(key, defaultValue);
        } else {
            return defaultValue;
        }
    }
}
