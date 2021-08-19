package se.skltp.adapterservices.apse.logging;

import org.apache.camel.Exchange;
import se.skltp.adapterservices.apse.constants.ExchangeProperties;
import se.skltp.adapterservices.apse.logging.logentry.*;

import java.util.Map;

public class LogEntryBuilder {

    private LogEntryBuilder() {
        // Static utility class
    }

    public static LogEntry createLogEntry(
            String logMessageType,
            Exchange exchange) {

        LogRuntimeInfoType lri = createRunTimeInfo(exchange);
        LogMetadataInfoType lmi = createMetadataInfo(exchange);
        LogMessageType lm = createLogMessage(logMessageType);

        // Create the log entry object
        LogEntry logEntry = new LogEntry();
        logEntry.setMetadataInfo(lmi);
        logEntry.setRuntimeInfo(lri);
        logEntry.setMessageInfo(lm);

        Map<String, String> extraInfo = LogExtraInfoBuilder.createExtraInfo(exchange);
        logEntry.setExtraInfo(extraInfo);

        return logEntry;
    }

    public static LogMessageExceptionType createMessageException(Exchange exchange, String stackTrace) {
        Throwable throwable = exchange.getProperty(Exchange.EXCEPTION_CAUGHT, Throwable.class);
        if (throwable == null) {
            return null;
        }

        LogMessageExceptionType lme = new LogMessageExceptionType();
        lme.setExceptionClass(throwable.getClass().getName());
        lme.setExceptionMessage(throwable.getMessage());
        lme.setStackTrace(stackTrace);

        return lme;
    }

    private static LogMessageType createLogMessage(String logMessageType) {
        LogMessageType lm = new LogMessageType();
        lm.setMessage(logMessageType);
        return lm;
    }

    private static LogMetadataInfoType createMetadataInfo(Exchange exchange) {

        String serviceImplementation = "";
        String endpoint = "";

        if (exchange != null) {
            serviceImplementation = exchange.getFromRouteId();
            String endpointURI = exchange.getProperty(ExchangeProperties.HTTP_URL_IN, String.class);
            endpoint = (endpointURI == null) ? "" : endpointURI;

        }

        LogMetadataInfoType lmi = new LogMetadataInfoType();
        lmi.setServiceImplementation(serviceImplementation);
        lmi.setEndpoint(endpoint);

        return lmi;
    }

    private static LogRuntimeInfoType createRunTimeInfo(Exchange exchange) {
        String messageId = "";
        String businessCorrelationId = "";
        String componentId = "";

        if (exchange != null) {
            businessCorrelationId = exchange.getProperty(ExchangeProperties.SKLTP_CORRELATION_ID, "", String.class);
            messageId = exchange.getMessage().getMessageId();

            // The context name is set in property "camel.springboot.name"
            componentId = exchange.getContext().getName();
        }

        LogRuntimeInfoType lri = new LogRuntimeInfoType();
        lri.setComponentId(componentId);
        lri.setMessageId(messageId);
        lri.setBusinessCorrelationId(businessCorrelationId);
        return lri;
    }


}
