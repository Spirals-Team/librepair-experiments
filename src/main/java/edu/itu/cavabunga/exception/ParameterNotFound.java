package edu.itu.cavabunga.exception;

public class ParameterNotFound extends RuntimeException {
    public ParameterNotFound(){

    }

    public ParameterNotFound(String message){
        super(message);
    }

    public ParameterNotFound(Throwable cause){
        super(cause);
    }

    public ParameterNotFound(Integer code, String message){
        super(message);
        this.errorCode = code;
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }

    private Integer errorCode;

}
