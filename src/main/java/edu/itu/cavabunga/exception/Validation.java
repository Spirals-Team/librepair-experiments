package edu.itu.cavabunga.exception;

public class Validation extends RuntimeException {
    public Validation(){

    }

    public Validation(String message) { super(message); }
    public Validation(Throwable cause) { super(cause); }
}
