package se.skltp.camel.utils.errorhandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import se.skltp.camel.utils.exceptions.SemanticErrorCodeEnum;
import se.skltp.camel.utils.exceptions.SemanticException;

@Service
public class ExceptionUtil {

    CodeMessages codeMessages;

    @Autowired
    public ExceptionUtil(CodeMessages codeMessages) {
        this.codeMessages = codeMessages;
    }


    public SemanticException createSemanticException(SemanticErrorCodeEnum codeEnum){
        return createSemanticException(codeEnum, null);
    }

    public SemanticException createSemanticException(SemanticErrorCodeEnum codeEnum, Object ...suffix){
        String exceptionMessage = createMessage(codeEnum, suffix);
        return new SemanticException(exceptionMessage, codeEnum);
    }

    public String createMessage(SemanticErrorCodeEnum codeEnum, Object ...suffix) {
        String errorMsg = codeMessages.getMessage(codeEnum);
        return codeEnum + " " + String.format(errorMsg, suffix);
    }



}