package edu.itu.cavabunga.exception;

public class ParticipantNotFound extends RuntimeException {
    public ParticipantNotFound(){

    }

    public ParticipantNotFound(String message){
        super(message);
    }

    public ParticipantNotFound(Throwable cause){
        super(cause);
    }

    public ParticipantNotFound(Integer code, String message){
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
