package edu.itu.cavabunga.exception;

public class ComponentConflict extends RuntimeException {
    public ComponentConflict(){

    }

    public ComponentConflict(String message){
        super(message);
    }

    public ComponentConflict(Throwable cause){
        super(cause);
    }

    public ComponentConflict(Integer code, String message){
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
