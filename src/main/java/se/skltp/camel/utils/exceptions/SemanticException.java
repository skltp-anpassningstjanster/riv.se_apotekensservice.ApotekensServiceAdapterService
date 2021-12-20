package se.skltp.camel.utils.exceptions;

public class SemanticException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    private SemanticErrorCodeEnum errorCode;

    public SemanticException(String message, SemanticErrorCodeEnum errorCode) {
        super(message);
        this.errorCode = errorCode;
    }

    public SemanticException(String message, SemanticErrorCodeEnum errorCode, Throwable cause) {
        super(message, cause);
        this.errorCode = errorCode;
    }

    public SemanticErrorCodeEnum getErrorCode() {
        // never return null, eliminate need for null-checking
        if (errorCode != null) {
            return errorCode;
        }
        return SemanticErrorCodeEnum.UNSET;
    }
}
