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
package se.skltp.adapterservices.apse;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.LoggingLevel;
import org.apache.camel.ShutdownRunningTask;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.http.HttpComponent;
import org.apache.http.conn.ssl.DefaultHostnameVerifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import se.skltp.adapterservices.apse.config.EndpointConfig;
import se.skltp.adapterservices.apse.config.SecurityProperties;
import se.skltp.adapterservices.apse.constants.ExchangeProperties;
import se.skltp.adapterservices.apse.utils.SamlHeaderFromArgosProcessor;
import se.skltp.adapterservices.apse.logging.MessageInfoLogger;


import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLPeerUnverifiedException;
import javax.net.ssl.SSLSession;
import javax.security.cert.X509Certificate;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * A simple Camel route that triggers from a timer and calls a bean and prints
 * to system out.
 * <p/>
 * Use <tt>@Component</tt> to make Camel auto detect this route when starting.
 */
@Log4j2
@Component
public class ApSeRouter extends RouteBuilder {

    public static final String NETTY_HTTP_FROM = "netty-http:%s?matchOnUriPrefix=true";

    public static final String LOG_ERROR_METHOD = "logError(*,${exception.stacktrace})";
    public static final String LOG_RESP_OUT_METHOD = "logRespOut(*)";
    public static final String LOG_REQ_IN_METHOD = "logReqIn(*)";
    public static final String LOG_REQ_OUT_METHOD = "logReqOut(*)";
    public static final String LOG_RESP_IN_METHOD = "logRespIn(*)";


    @Autowired
    SamlHeaderFromArgosProcessor samlHeaderFromArgosProcessor;
    @Autowired
    EndpointResolverProcessor resolveEndpoint;

    @Autowired
    SecurityProperties securityProperties;

    @Autowired
    EndpointConfig endpointConfig;

    @Autowired
    SecurityProperties sslConfig;

    @Override
    public void configure() throws Exception {
        log.debug("Configuring routes for ApSe");
        if (endpointConfig.getInbound() == null) {
            log.error("no inbound values, check configuration.");
            System.exit(-404);
        }

        resolveHostnameVerifier();

        onException(Exception.class)
                .bean(MessageInfoLogger.class, LOG_ERROR_METHOD)
                .handled(false);


        endpointConfig.getInbound().forEach((service, consumerUrl) -> {
            log.info("setting up inbound consumer " + consumerUrl);
            fromF(NETTY_HTTP_FROM, consumerUrl)
                    .shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                    .routeId(service + " flow")
                    .setProperty("InboundService", simple(service))
                    .to("direct:transform")
                    ;
        });

        from("direct:transform").routeId("transform header")
                .setProperty(ExchangeProperties.EXCHANGE_CREATED,  simple("${date:exchangeCreated}"))
                .log("received connection")
                .shutdownRunningTask(ShutdownRunningTask.CompleteAllTasks)
                .bean(MessageInfoLogger.class, LOG_REQ_IN_METHOD)
                .process(samlHeaderFromArgosProcessor)
                .process(resolveEndpoint)
                .removeHeader(Exchange.HTTP_URI)
                //.bean(MessageInfoLogger.class, LOG_REQ_OUT_METHOD)
                //.log(LoggingLevel.INFO, "executing request for \"${exchangeProperty[servicecontract_namespace]}\" on url: ${exchangeProperty[outbound_url]}")
                .toD("${exchangeProperty[outbound_url]}?sslContextParameters=#outgoingSSLContextParameters")
                ;//.bean(MessageInfoLogger.class, LOG_RESP_IN_METHOD);
    }

    private void resolveHostnameVerifier() {
        getContext().getComponent("https", HttpComponent.class)
            .setX509HostnameVerifier(new HostnameVerifier() {

                @Override
                public boolean verify(String hostname, SSLSession session) {
                    AtomicBoolean returnValue = new AtomicBoolean(false);
                    DefaultHostnameVerifier defaultHostnameVerifier = new DefaultHostnameVerifier();
                    boolean defaultVerifyResponse = defaultHostnameVerifier.verify(hostname, session);
                    if (defaultVerifyResponse) {
                        returnValue.set(true);
                    }else{
                        X509Certificate[] pc;
                        String subj = "----";
                        try {
                            pc = session.getPeerCertificateChain();
                            subj = pc[0].getSubjectDN().getName();
                        } catch (SSLPeerUnverifiedException e) {
                            e.printStackTrace();
                        }
                        log.warn(String.format("Hostname verification failed, the host \"%s\" presented a certificate with subject: \"%s\"", subj, hostname, subj));
                        if (securityProperties.getStore().getConsumer().getHostNameVerifySkipList() != null) {
                            securityProperties.getStore().getConsumer().getHostNameVerifySkipList().forEach(
                                    skipMe -> {
                                        if (skipMe.equals(hostname)) {
                                            log.error("Despite a failed host name verification the host was accepted. this should not be allowed outside a development environment");
                                            returnValue.set(true);
                                        }
                                    }
                            );
                        }
                    }
                    return returnValue.get();
                }
            }
        );
    }
}
