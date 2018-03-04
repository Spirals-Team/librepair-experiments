package edu.itu.cavabunga.exception;

public class ComponentNotFound extends RuntimeException{
    public ComponentNotFound(){

    }

    public ComponentNotFound(String message){
        super(message);
    }

    public ComponentNotFound(Throwable cause){
        super(cause);
    }

    public ComponentNotFound(Integer code, String message){
        super(message);
        this.errorCode = code;
    }

    private Integer errorCode;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
