package com.meteoradesigner.util.exception;

import com.meteoradesigner.util.validator.ServiceValidatorUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;


//TODO check if annotations parameter is needed.
/**
 * This @code{GlobalExceptionHandler} class represents global exception interceptor (handler).
 */
@RestControllerAdvice(annotations = RestController.class)
public class GlobalExceptionHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * Handles @code{ApplicationException} exceptions.
     *
     * @param exception to handle.
     * @param request   to response.
     * @return @code{ResponseEntity} with error information.
     */
    @ExceptionHandler(value = ApplicationException.class)
    private ResponseEntity<ErrorInfo> handleApplicationException(
            ApplicationException exception,
            HttpServletRequest request
    ) {
        return new ResponseEntity<>(
                logAndGetErrorInfo(
                        exception,
                        request.getRequestURI(),
                        true,
                        MessageUtil.getMessage(exception)),
                exception.getHttpStatus());
    }

    //second method without ResponseEntity

    /**
     * Handles @code{Exception} exceptions, except @code{ApplicationException} ones.
     *
     * @param exception to handle.
     * @param request   to response.
     * @return @code{ResponseEntity} with error information.
     */
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(value = Exception.class)
    private ErrorInfo handleException(
            Exception exception,
            HttpServletRequest request
    ) {
        return logAndGetErrorInfo(
                exception,
                request.getRequestURI(),
                false,
                exception.getLocalizedMessage());
    }

    /**
     * Constructs @code{ErrorInfo} instance and logs the exception data.
     *
     * @param exception              to handle.
     * @param uri                    of the request.
     * @param isApplicationException @code{boolean} value, true if the exception to handle
     *                               is @code{ApplicationException}.
     * @param description            of the exception.
     * @return @code{ErrorInfo} with error information.
     */
    private ErrorInfo logAndGetErrorInfo(Exception exception,
                                         String uri,
                                         boolean isApplicationException,
                                         String description) {
        Throwable primeCause = ServiceValidatorUtil.getPrimeCause(exception);

        if (isApplicationException) {
            LOGGER.warn("primeCause: {}; url: {}", primeCause, uri);
        } else {
            LOGGER.error("primeCause: {}; url: {}", primeCause, uri);
        }

        return new ErrorInfo(
                uri,
                description,
                primeCause.toString()
        );
    }

}