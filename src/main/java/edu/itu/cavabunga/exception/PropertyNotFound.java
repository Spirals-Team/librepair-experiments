package edu.itu.cavabunga.exception;

public class PropertyNotFound extends RuntimeException {
    public PropertyNotFound(){

    }

    public PropertyNotFound(String message){
        super(message);
    }

    public PropertyNotFound(Throwable cause){
        super(cause);
    }

    public PropertyNotFound(Integer code, String message){
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
