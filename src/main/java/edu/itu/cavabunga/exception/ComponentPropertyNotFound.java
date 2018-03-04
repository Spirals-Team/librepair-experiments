package edu.itu.cavabunga.exception;

public class ComponentPropertyNotFound extends RuntimeException {
    public ComponentPropertyNotFound(){

    }

    public ComponentPropertyNotFound(String message){
        super(message);
    }

    public ComponentPropertyNotFound(Throwable cause){
        super(cause);
    }

    public ComponentPropertyNotFound(String message, Throwable cause){
        super(message,cause);
    }
}
