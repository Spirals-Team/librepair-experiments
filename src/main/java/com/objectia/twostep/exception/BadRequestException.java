package com.objectia.twostep.exception;

public class BadRequestException extends APIException {

    private static final long serialVersionUID = 1L;

    public BadRequestException(String body) {
        super(body);
    }
}