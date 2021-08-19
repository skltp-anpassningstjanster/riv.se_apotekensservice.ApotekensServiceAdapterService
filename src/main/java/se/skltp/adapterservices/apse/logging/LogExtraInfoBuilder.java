package se.skltp.adapterservices.apse.logging;

import io.netty.handler.codec.http.HttpHeaderNames;
import org.apache.camel.Exchange;
import se.skltp.adapterservices.apse.constants.ExchangeProperties;
import se.skltp.adapterservices.apse.constants.HttpHeaders;

import java.util.*;
import java.util.stream.Collectors;

import static se.skltp.adapterservices.apse.constants.HttpHeaders.CERTIFICATE_FROM_REVERSE_PROXY;

public class LogExtraInfoBuilder {

    public static final String SENDER_ID = ExchangeProperties.SENDER_ID;
    public static final String RECEIVER_ID = ExchangeProperties.RECEIVER_ID;
    public static final String RIV_VERSION = ExchangeProperties.RIV_VERSION;
    public static final String SERVICECONTRACT_NAMESPACE = ExchangeProperties.SERVICECONTRACT_NAMESPACE;
    public static final String WSDL_NAMESPACE = "wsdl_namespace";
    public static final String SENDER_IP_ADRESS = ExchangeProperties.SENDER_IP_ADRESS;
    public static final String VAGVAL_TRACE = ExchangeProperties.VAGVAL_TRACE;
    public static final String ANROPSBEHORIGHET_TRACE = ExchangeProperties.ANROPSBEHORIGHET_TRACE;
    public static final String OUT_ORIGINAL_SERVICE_CONSUMER_HSA_ID = "originalServiceconsumerHsaid";
    public static final String IN_ORIGINAL_SERVICE_CONSUMER_HSA_ID = "originalServiceconsumerHsaid_in";
    public static final String TIME_ELAPSED = "time.elapsed";
    public static final String TIME_PRODUCER = "time.producer";
    public static final String MESSAGE_LENGTH = "message.length";
    public static final String HEADERS = "Headers";
    public static final String ENDPOINT_URL = "endpoint_url";
    public static final String SOURCE = "source";
    public static final String VP_X_FORWARDED_HOST = ExchangeProperties.VP_X_FORWARDED_HOST;
    public static final String VP_X_FORWARDED_PROTO = ExchangeProperties.VP_X_FORWARDED_PROTO;
    public static final String VP_X_FORWARDED_PORT = ExchangeProperties.VP_X_FORWARDED_PORT;

    protected static final List<String> HEADERS_TO_FILTER = Arrays.asList(CERTIFICATE_FROM_REVERSE_PROXY, "x-fk-auth-cert");
    protected static final String FILTERED_TEXT = "<filtered>";


    private LogExtraInfoBuilder() {
        // Static utility class
    }

    public static Map<String, String> createExtraInfo(Exchange exchange) {
        ExtraInfoMap<String, String> extraInfo = new ExtraInfoMap<>();

        extraInfo.put(SENDER_ID, exchange.getProperty(ExchangeProperties.SENDER_ID, String.class));
        extraInfo.put(RECEIVER_ID, exchange.getProperty(ExchangeProperties.RECEIVER_ID, String.class));
        extraInfo.put(OUT_ORIGINAL_SERVICE_CONSUMER_HSA_ID,
                exchange.getProperty(ExchangeProperties.OUT_ORIGINAL_SERVICE_CONSUMER_HSA_ID, String.class));
        extraInfo.putNotEmpty(IN_ORIGINAL_SERVICE_CONSUMER_HSA_ID,
                nullValue2Blank(exchange.getProperty(ExchangeProperties.IN_ORIGINAL_SERVICE_CONSUMER_HSA_ID, String.class)));
        extraInfo.put(SENDER_IP_ADRESS, exchange.getProperty(ExchangeProperties.SENDER_IP_ADRESS, String.class));

        String serviceContractNS = exchange.getProperty(ExchangeProperties.SERVICECONTRACT_NAMESPACE, String.class);
        String rivVersion = exchange.getProperty(ExchangeProperties.RIV_VERSION, String.class);
        extraInfo.put(SERVICECONTRACT_NAMESPACE, serviceContractNS);
        extraInfo.put(RIV_VERSION, rivVersion);
        extraInfo.put(WSDL_NAMESPACE, createWsdlNamespace(serviceContractNS, rivVersion));

        extraInfo.put(HEADERS, getHeadersAsString(exchange.getIn().getHeaders()));

        Long elapsed = getElapsedTime(exchange);
        if (elapsed != null) {
            extraInfo.put(TIME_ELAPSED, elapsed.toString());
        } else {
            extraInfo.put(TIME_ELAPSED, "null");
        }

        extraInfo.putNotNull(VAGVAL_TRACE, exchange.getProperty(ExchangeProperties.VAGVAL_TRACE, String.class));
        extraInfo.putNotNull(ANROPSBEHORIGHET_TRACE, exchange.getProperty(ExchangeProperties.ANROPSBEHORIGHET_TRACE, String.class));
        extraInfo.putNotNull(ENDPOINT_URL, exchange.getProperty(ExchangeProperties.VAGVAL, String.class));

        String timeProducer = exchange.getIn().getHeader(HttpHeaders.X_SKLTP_PRODUCER_RESPONSETIME, String.class);
        extraInfo.putNotNull(TIME_PRODUCER, timeProducer);

        String messageSize = exchange.getIn().getHeader(HttpHeaderNames.CONTENT_LENGTH.toString(), String.class);
        extraInfo.putNotNull(MESSAGE_LENGTH, messageSize);

        addHttpForwardHeaders(exchange, extraInfo);

        final Boolean isError = exchange.getProperty(ExchangeProperties.SESSION_ERROR, Boolean.class);
        if (isError != null && isError) {
            addErrorInfo(exchange, extraInfo);
        }
        return extraInfo;
    }

    private static void addHttpForwardHeaders(Exchange exchange, ExtraInfoMap<String, String> extraInfo) {
        // Following should be logged only once

        String httpXForwardedProto = exchange.getProperty(ExchangeProperties.VP_X_FORWARDED_PROTO, String.class);
        if (httpXForwardedProto != null) {
            extraInfo.put(VP_X_FORWARDED_PROTO, httpXForwardedProto);
            exchange.removeProperty(ExchangeProperties.VP_X_FORWARDED_PROTO);
        }
        String httpXForwardedHost = exchange.getProperty(ExchangeProperties.VP_X_FORWARDED_HOST, String.class);
        if (httpXForwardedHost != null) {
            extraInfo.put(VP_X_FORWARDED_HOST, httpXForwardedHost);
            exchange.removeProperty(ExchangeProperties.VP_X_FORWARDED_HOST);
        }
        String httpXForwardedPort = exchange.getProperty(ExchangeProperties.VP_X_FORWARDED_PORT, String.class);
        if (httpXForwardedPort != null) {
            extraInfo.put(VP_X_FORWARDED_PORT, httpXForwardedPort);
            exchange.removeProperty(ExchangeProperties.VP_X_FORWARDED_PORT);
        }
    }

    private static String createWsdlNamespace(String serviceContractNS, String profile) {
        //  Convert from interaction target namespace
        //    urn:${domänPrefix}:${tjänsteDomän}:${tjänsteInteraktion}${roll}:${m}
        //  to wsdl target namespace
        //    urn:riv:${tjänsteDomän}:${tjänsteInteraktion}:m:${profilKortnamn}
        // See https://riv-ta.atlassian.net/wiki/spaces/RTA/pages/99593635/RIV+Tekniska+Anvisningar+Tj+nsteschema
        //   and https://riv-ta.atlassian.net/wiki/spaces/RTA/pages/77856888/RIV+Tekniska+Anvisningar+Basic+Profile+2.1
        if (serviceContractNS == null || profile == null) {
            return null;
        }
        return serviceContractNS.replace("Responder", "").concat(":").concat(profile);
    }

    private static Long getElapsedTime(Exchange exchange) {
        Date created = exchange.getProperty(ExchangeProperties.EXCHANGE_CREATED, Date.class);
        if (created != null) {
            return new Date().getTime() - created.getTime();
        }
        return null;
    }

    private static void addErrorInfo(Exchange exchange, ExtraInfoMap<String, String> extraInfo) {
        Exception exception = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Exception.class);
        String errorDescription = exception != null ? exception.getMessage() : "";
        String technicalDescription = exception != null ? exception.toString() : "";
        String errorCode = exchange.getProperty(ExchangeProperties.SESSION_ERROR_CODE, String.class);
        String htmlStatus = exchange.getProperty(ExchangeProperties.SESSION_HTML_STATUS, String.class);

        extraInfo.put(ExchangeProperties.SESSION_ERROR, "true");
        extraInfo.put(ExchangeProperties.SESSION_ERROR_DESCRIPTION, nullValue2Blank(errorDescription));
        extraInfo.put(ExchangeProperties.SESSION_ERROR_TECHNICAL_DESCRIPTION, nullValue2Blank(technicalDescription));
        extraInfo.put(ExchangeProperties.SESSION_ERROR_CODE, nullValue2Blank(errorCode));
        extraInfo.putNotEmpty(ExchangeProperties.SESSION_HTML_STATUS, htmlStatus);

    }

    private static String nullValue2Blank(String s) {
        return (s == null) ? "" : s;
    }

    private static String getHeadersAsString(Map<String, ?> headersMap) {
        return headersMap.keySet().stream()
                .map(key -> key + "=" + filterHeaderValue(key, headersMap))
                .collect(Collectors.joining(", ", "{", "}"));
    }

    private static String filterHeaderValue(String key, Map<String, ?> headersMap) {
        return HEADERS_TO_FILTER.contains(key) ? FILTERED_TEXT : "" + headersMap.get(key);
    }

    private static class ExtraInfoMap<K, V> extends HashMap<K, V> {

        public V putNotNull(K key, V value) {
            return value == null ? null : put(key, value);
        }

        public V putNotEmpty(K key, V value) {
            return (value == null || ((String) value).isEmpty()) ? null : put(key, value);
        }
    }
}
