package com.objectia.twostep.exception;

public class NotFoundException extends APIException {

    private static final long serialVersionUID = 1L;

    public NotFoundException(String body) {
        super(body);
    }

}