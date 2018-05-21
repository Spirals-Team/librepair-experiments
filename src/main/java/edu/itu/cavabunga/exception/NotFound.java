package edu.itu.cavabunga.exception;

public class NotFound extends RuntimeException {
    public NotFound(){

    }

    public NotFound(String message){
        super(message);
    }
    public NotFound(Throwable cause){
        super(cause);
    }
}
