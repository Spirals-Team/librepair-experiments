package com.gdc.aerodev.service.exception;

/**
 * Specific exception for mark 'service' module
 *
 * @author Yusupov Danil
 */
public class ServiceException extends RuntimeException{

    public ServiceException(String message) {
        super(message);
    }

    public ServiceException(String message, Throwable cause) {
        super(message, cause);
    }
}
