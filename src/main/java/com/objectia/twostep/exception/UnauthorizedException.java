package com.objectia.twostep.exception;

public class UnauthorizedException extends APIException {

    private static final long serialVersionUID = 1L;

    public UnauthorizedException(String body) {
        super(body);
    }

}