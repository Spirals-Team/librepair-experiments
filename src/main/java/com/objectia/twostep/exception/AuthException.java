package com.objectia.twostep.exception;

public class AuthException extends TwostepException {

    private static final long serialVersionUID = 1L;

    public AuthException(String message) {
        super(message);
    }

    public AuthException(String message, Throwable e) {
        super(message, e);
    }

}