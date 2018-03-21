package com.objectia.twostep.exception;

public class APIException extends TwostepException {

    private static final long serialVersionUID = 1L;

    private final int status;

    public APIException(int status, String message) {
        super(message);
        this.status = status;
    }

    public int getStatus() {
        return status;
    }
}