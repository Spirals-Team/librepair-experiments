package com.isa.instaticketapi.web.rest.vm;

/**
 * Representing super class for reponse from API
 * providing additional message with response
 *
 * @author sansajn
 */
public abstract class AbstractResponse {

    private String message;

    public AbstractResponse() {

    }

    public AbstractResponse(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
