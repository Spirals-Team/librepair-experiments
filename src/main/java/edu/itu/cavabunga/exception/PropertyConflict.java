package edu.itu.cavabunga.exception;

public class PropertyConflict extends RuntimeException {
    public PropertyConflict(){

    }

    public PropertyConflict(String message){
        super(message);
    }

    public PropertyConflict(Throwable cause){
        super(cause);
    }

    public PropertyConflict(Integer code, String message){
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
