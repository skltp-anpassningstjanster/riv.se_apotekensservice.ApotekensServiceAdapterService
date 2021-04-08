/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package se.skltp.adapterservice.apse.utils;

import java.io.ByteArrayOutputStream;
import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.springframework.stereotype.Service;
import lombok.extern.log4j.Log4j2;
import org.apache.camel.Expression;
import org.apache.camel.Message;
import org.apache.camel.component.netty.http.NettyChannelBufferStreamCache;
import org.apache.camel.processor.TransformProcessor;

/**
 *
 * @author jonmat
 */
@Service
@Log4j2
public class ConvertArgosHeaderToSamlProcessor implements Processor {
    @Override
    public void process(Exchange exchange) throws Exception {
        log.info("ConvertArgosHeaderToSamlProcessor.process()");
        
        exchange.getIn().setBody("<samlData></samlData>");
    }
}

