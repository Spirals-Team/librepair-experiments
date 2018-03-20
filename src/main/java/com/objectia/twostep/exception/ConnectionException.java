package com.objectia.twostep.exception;

public class ConnectionException extends TwostepException {

    private static final long serialVersionUID = 1L;

    public ConnectionException(String message) {
        super(message);
    }

    public ConnectionException(String message, Throwable e) {
        super(message, e);
    }

}