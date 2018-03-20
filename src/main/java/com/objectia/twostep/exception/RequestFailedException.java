package com.objectia.twostep.exception;

public class RequestFailedException extends APIException {

    private static final long serialVersionUID = 1L;

    public RequestFailedException(String body) {
        super(body);
    }

}