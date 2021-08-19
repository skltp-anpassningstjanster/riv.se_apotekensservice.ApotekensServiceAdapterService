package se.skltp.adapterservices.apse.logging.logentry;

import lombok.Data;

@Data
public class LogMessageExceptionType {
    protected String exceptionClass;
    protected String exceptionMessage;
    protected String stackTrace;


}
