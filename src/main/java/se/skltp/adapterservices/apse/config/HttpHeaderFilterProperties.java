package se.skltp.adapterservices.apse.config;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.apache.camel.Exchange;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@NoArgsConstructor
@Configuration
@EnableConfigurationProperties
@ConfigurationProperties(prefix = "headers.reg.exp")
public class HttpHeaderFilterProperties {

    // Following headers will be removed from request to producer
    String requestHeadersToRemove = "(?i)x-vp.*|PEER_CERTIFICATES|X-Forwarded.*|MULE_.*|X-MULE_.*|Connection|accept-encoding"
            + "|" + org.apache.camel.Exchange.HTTP_URI
            + "|" + Exchange.HTTP_PATH;

    // Following headers will be kept in request to producer
    String requestHeadersToKeep = "(?i)x-vp-sender-id|x-vp-instance-id";

    // Following headers will be removed from response to consumer
    String responseHeadersToRemove = "(?i)SOAPAction|MULE_.*|X-MULE_.*|LOCAL_CERTIFICATE|PEER_CERTIFICATES|http.method";

    // Following headers will be kept in response to consumer
    String responseHeadersToKeep="";

}