package com.meteoradesigner.util.exception;

import org.springframework.http.HttpStatus;

//TODO documentation
public class NotFoundException extends ApplicationException {
    private static final String NOT_FOUND_EXCEPTION = "notFound";

    public NotFoundException(String msg) {
        super(NOT_FOUND_EXCEPTION, HttpStatus.UNPROCESSABLE_ENTITY, msg);
    }
}