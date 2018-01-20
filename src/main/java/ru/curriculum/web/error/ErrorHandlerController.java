package ru.curriculum.web.error;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class ErrorHandlerController extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    protected String handleConflict(Model model, RuntimeException ex, WebRequest request) {
        model.addAttribute("exception", ex);

        return "error/errorPage";
    }
}
