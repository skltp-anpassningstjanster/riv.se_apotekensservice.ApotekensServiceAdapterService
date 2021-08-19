package se.skltp.adapterservices.apse.logging.logentry;

import lombok.Data;

import java.util.Map;

@Data
public class LogEntry {

    protected LogMetadataInfoType metadataInfo;
    protected LogRuntimeInfoType runtimeInfo;
    protected LogMessageType messageInfo;
    protected String payload;
    protected Map<String, String> extraInfo;

}