package io.descoped.client.exception;

/**
 * @author Ove Ranheim (oranheim@gmail.com)
 * @since 22/11/2017
 */
public class APIClientException extends RuntimeException {

    public APIClientException() {
    }

    public APIClientException(String message) {
        super(message);
    }

    public APIClientException(String message, Throwable cause) {
        super(message, cause);
    }

    public APIClientException(Throwable cause) {
        super(cause);
    }

    public APIClientException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

    public APIClientException(int statusCode, String message) {
        this(String.format("HttpRequest error: (%s) %s", statusCode, message));
    }

}
