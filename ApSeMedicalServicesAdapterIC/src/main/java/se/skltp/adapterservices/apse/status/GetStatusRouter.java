package se.skltp.adapterservices.apse.status;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.builder.RouteBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Log4j2
@Component
public class GetStatusRouter extends RouteBuilder {

    public static final String HTTP_GET = "get-status";

    public static final String NETTY_HTTP_GET = "netty-http:{{apse.status.url}}"
            + "?chunkedMaxContentLength={{apse.max.receive.length}}";

    @Autowired
    GetStatusProcessor getStatusProcessor;

    @Override
    public void configure() throws Exception {
        from(NETTY_HTTP_GET).routeId(HTTP_GET)
                .process(getStatusProcessor);
    }
}