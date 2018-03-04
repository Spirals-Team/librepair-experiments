package pl.wasper.bandmanagement.handler;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import pl.wasper.bandmanagement.exception.ElementNotFoundException;

import javax.servlet.http.HttpServletRequest;

@ControllerAdvice
public class GlobalControllerExceptionHandler {
    @ExceptionHandler(ElementNotFoundException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorInfo elementNotFound(HttpServletRequest req, Exception e) {
        return new ErrorInfo(
                HttpStatus.NOT_FOUND.value(),
                req.getMethod(),
                e.getLocalizedMessage(),
                req.getRequestURL().toString()
        );
    }
}
