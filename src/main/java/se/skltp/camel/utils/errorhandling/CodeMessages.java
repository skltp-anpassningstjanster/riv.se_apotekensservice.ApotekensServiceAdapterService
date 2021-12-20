package se.skltp.camel.utils.errorhandling;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import se.skltp.camel.utils.exceptions.SemanticErrorCodeEnum;

@Configuration
@PropertySource("classpath:vp-messages.properties")
public class CodeMessages {

    Environment env;

    @Autowired
    public CodeMessages(Environment env) {
        this.env = env;
    }

    public String getMessage(SemanticErrorCodeEnum vpSemanticErrorCodeEnum) {
        return env.getProperty(vpSemanticErrorCodeEnum.getCode());
    }

    public String getMessage(String key) {
        return env.getProperty(key);
    }

}