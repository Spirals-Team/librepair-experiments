package edu.itu.cavabunga.advice;

import edu.itu.cavabunga.core.http.ErrorResponse;
import edu.itu.cavabunga.exception.Conflict;
import edu.itu.cavabunga.exception.NotFound;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionHandlerAdvice {

    /**
     * Advice for uncaught Not Found exceptions
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(NotFound.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ErrorResponse handleNotFound(NotFound e){
        System.out.printf(e.getMessage());
        return new ErrorResponse(1,e.getMessage(), null);
    }

    /**
     * Advice for uncaught Conflict exceptions
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(Conflict.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public ErrorResponse handleConflict(Conflict e){
        System.out.printf(e.getMessage());
        return new ErrorResponse(1,e.getMessage(),null);
    }

    /**
     * Advice for uncaught Illegal state exceptions
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(IllegalStateException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalState(IllegalStateException e){
        System.out.printf(e.getMessage());
        return new ErrorResponse(1,e.getMessage(),null);
    }

    /**
     * Advice for uncaught Bad Request exceptions
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public ErrorResponse handleIllegalArgument(IllegalArgumentException e){
        System.out.printf(e.getMessage());
        return new ErrorResponse(1,e.getMessage(),null);
    }

    /**
     * Generic advice for any unknown uncaught exceptions
     *
     * @param e caught exception
     * @return ErrorResponse with exception message with related HTTP status
     */
    @ExceptionHandler(Exception.class)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    public ErrorResponse handleException(Exception e){
        System.out.printf(e.getMessage());
        return new ErrorResponse(1,e.getMessage(), null);
    }


}
