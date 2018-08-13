package com.arcao.geocaching.api.exception;

public class GeocachingApiException extends Exception {
    private static final long serialVersionUID = -2700625070490624511L;

    public GeocachingApiException(String message) {
        super(message);
    }

    public GeocachingApiException(String message, Throwable cause) {
        super(message, cause);
    }
}
