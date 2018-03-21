package com.objectia.twostep.exception;

public abstract class TwostepException extends Exception {

    private static final long serialVersionUID = 1L;

    public TwostepException() {
        super();
    }

    public TwostepException(String message) {
        super(message, null);
    }

    public TwostepException(String message, Throwable e) {
        super(message, e);
    }

}