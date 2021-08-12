package se.skltp.adapterservices.apse.logging.logentry;

import lombok.Data;

@Data
public class LogMetadataInfoType {
  protected String endpoint;
  protected String serviceImplementation;

}
