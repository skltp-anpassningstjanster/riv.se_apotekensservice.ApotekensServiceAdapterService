/*
 * Licensed to the Apache Software Foundation (ASF) under one or more
 * contributor license agreements.  See the NOTICE file distributed with
 * this work for additional information regarding copyright ownership.
 * The ASF licenses this file to You under the Apache License, Version 2.0
 * (the "License"); you may not use this file except in compliance with
 * the License.  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package se.skltp.adapterservice.apse;

import se.skltp.adapterservice.apse.utils.SamlHeaderFromArgosProcessor;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpComponent;
import org.apache.camel.support.jsse.SSLContextParameters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skltp.adapterservice.apse.config.EndpointConfig;
import se.skltp.adapterservice.apse.config.SslConfig;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints
 * to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Log4j2
@Component
public class ApSeRouter extends RouteBuilder {

    public static final String NETTY_HTTP_FROM = "netty-http:%s"
            + "?matchOnUriPrefix=true";
    public static final String HTTPS_OUTGOING_TOD = "${exchangeProperty[outbound_url]}";
//            + "?connectTimeout={{endpoint.connect.timeout}}";

    @Autowired
    SamlHeaderFromArgosProcessor samlHeaderFromArgosProcessor;
    @Autowired
    EndpointResolverProcessor resolveEndpoint;

    @Autowired
    EndpointConfig endpointConfig;

    @Autowired
    SslConfig sslConfig;

    @Override
    public void configure() throws Exception {

        HttpComponent httpComponent = getContext().getComponent("https", HttpComponent.class);
        SSLContextParameters scp = sslConfig.getSSLContext();
        httpComponent.setSslContextParameters(scp);

        if (endpointConfig.getInbound() == null) {
            log.info("no inbound values, check config.");
            System.exit(-404);
        }
        
        endpointConfig.getInbound().forEach((service, consumerUrl) -> {
            log.info("setting up inbound consumer " + consumerUrl);
            fromF(NETTY_HTTP_FROM, consumerUrl)
                    .routeId(service + " flow")
                    .setProperty("InboundService", simple(service))
                    .to("direct:transform");
        });

        from("direct:transform").routeId("transform header")
                .process(samlHeaderFromArgosProcessor)
                .process(resolveEndpoint)
                .removeHeader(Exchange.HTTP_URI)
                .toD("${exchangeProperty[outbound_url]}");
        
    }
}
