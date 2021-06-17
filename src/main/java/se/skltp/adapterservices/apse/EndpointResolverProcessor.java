/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.skltp.adapterservices.apse;

import lombok.extern.log4j.Log4j2;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.skltp.adapterservices.apse.config.EndpointConfig;
import se.skltp.adapterservices.apse.utils.SamlHeaderFromArgosProcessor;

/**
 *
 * @author jonmat
 */
@Service
@Log4j2
class EndpointResolverProcessor implements Processor {

    @Autowired
    EndpointConfig endpointConfig;
    
    @Override
    public void process(Exchange exchange) throws Exception {
        
        String reqInteractionName = exchange.getProperty(SamlHeaderFromArgosProcessor.SERVICECONTRACT_NAMESPACE, String.class);
        String service = exchange.getProperty("InboundService", String.class);
        String outboundUrl = endpointConfig.getOutbound().get(service).get(reqInteractionName);
        if (outboundUrl == null) {

        } else {
            exchange.setProperty("outbound_url", outboundUrl);
        }
    }
    
}
