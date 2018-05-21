package edu.itu.cavabunga.core.http;

import lombok.Data;

/**
 * Generic Response object
 */
@Data
public class Response {

    /**
     * application status code
     */
    private Integer code;

    /**
     * application status message
     */
    private String message;

    /**
     * Generic Response object constructor
     *
     * @param code application status code
     * @param message application status message
     */
    public Response(Integer code, String message){
        this.code = code;
        this.message = message;
    }

}
