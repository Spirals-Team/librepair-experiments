package com.arcao.geocaching.api.exception;

public class InvalidCredentialsException extends GeocachingApiException {
    private static final long serialVersionUID = 8368860316634438261L;

    public InvalidCredentialsException(String message) {
        super(message);
    }

    public InvalidCredentialsException(String message, Throwable cause) {
        super(message, cause);
    }
}
