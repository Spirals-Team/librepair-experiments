package com.epam.brest.course.client.errorHandler;

/**
 *exception class.
 */
public class ServletDataAccessException extends RuntimeException {

    /**
     *
     * @param message massage.
     */
    public ServletDataAccessException(final String message) {
        super(message);
    }
}
