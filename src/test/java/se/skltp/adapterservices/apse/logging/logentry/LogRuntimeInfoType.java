package se.skltp.adapterservices.apse.logging.logentry;
import lombok.Data;

@Data
public class LogRuntimeInfoType {
    protected String componentId;
    protected String messageId;
    protected String businessCorrelationId;

}
