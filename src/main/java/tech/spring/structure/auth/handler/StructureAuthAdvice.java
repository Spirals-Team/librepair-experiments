package tech.spring.structure.auth.handler;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@ControllerAdvice
public class StructureAuthAdvice {

    @ResponseStatus(value = UNAUTHORIZED)
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(AccessDeniedException exception) {
        return exception.getMessage();
    }

}
