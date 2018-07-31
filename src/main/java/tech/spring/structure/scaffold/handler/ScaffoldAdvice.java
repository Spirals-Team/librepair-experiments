package tech.spring.structure.scaffold.handler;

import static org.springframework.http.HttpStatus.NOT_FOUND;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import tech.spring.structure.scaffold.exception.ScaffoldNotFoundException;

@RestController
@ControllerAdvice
public class ScaffoldAdvice {

    @ResponseStatus(value = NOT_FOUND)
    @ExceptionHandler(ScaffoldNotFoundException.class)
    public String handleScaffoldNotFoundException(ScaffoldNotFoundException exception) {
        return exception.getMessage();
    }

}
