package se.skltp.adapterservices.apse.logging.logentry;

import lombok.Data;

@Data
public class LogMessageType {
  protected String message;
  protected LogMessageExceptionType exception;
}
