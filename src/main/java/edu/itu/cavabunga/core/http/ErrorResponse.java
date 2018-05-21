package edu.itu.cavabunga.core.http;

import lombok.Getter;
import lombok.Setter;

/**
 * Response for any kind of error
 */
@Getter
@Setter
public class ErrorResponse extends Response {

    /**
     * related error object
     */
    private Object data;

    /**
     * Response object that contains single component in data field
     *
     * @param code application status code
     * @param message application status message
     * @param data related error data
     */
    public ErrorResponse(Integer code, String message, Object data) {
        super(code, message);
        this.data = data;
    }
}
