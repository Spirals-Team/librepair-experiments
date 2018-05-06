package pl.hycom.ip2018.searchengine.googledrivesearch.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown in case of Internal Server Error
 */
@ResponseStatus(value = HttpStatus.INTERNAL_SERVER_ERROR, reason = "Internal Server Error")
public class GoogleDriveSearchException extends Exception {

    /**
     * Constructs a new exception with {@code null} as its detail message.
     */
    public GoogleDriveSearchException() {
    }

    /**
     * Constructs a new exception with the specified detail message.
     * @param message the detail message
     */
    public GoogleDriveSearchException(String message) {
        super("GoogleDriveSearchException: " + message);
    }
}
