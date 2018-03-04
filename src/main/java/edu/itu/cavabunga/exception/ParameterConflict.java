package edu.itu.cavabunga.exception;

public class ParameterConflict extends RuntimeException {
    public ParameterConflict(){

    }

    public ParameterConflict(String message){
        super(message);
    }

    public ParameterConflict(Throwable cause){
        super(cause);
    }

    public ParameterConflict(Integer code, String message){
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
