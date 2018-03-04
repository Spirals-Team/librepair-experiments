package edu.itu.cavabunga.exception;

public class AuthenticationNotFound extends RuntimeException{
    public AuthenticationNotFound(){

    }

    public AuthenticationNotFound(String message){
        super(message);
    }

    public AuthenticationNotFound(Throwable cause){
        super(cause);
    }

    public AuthenticationNotFound(String message, Throwable cause){
        super(message,cause);
    }

}
