package de.adorsys.smartanalytics.exception;


import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(
        value = HttpStatus.BAD_REQUEST,
        reason = "INVALID_RULES"
)
public class InvalidRulesException extends ParametrizedMessageException {

    public InvalidRulesException(String message) {
        super("unable import rule(s)");
        this.addParam("message", message);
    }

}
