package edu.itu.cavabunga.exception;

public class ParticipantConflict extends RuntimeException {

    public ParticipantConflict(){

    }

    public ParticipantConflict(Integer code, String message){
        super(message);
        this.errorCode = code;
    }

    public ParticipantConflict(String message){
        super(message);
    }



    public ParticipantConflict(Throwable cause){
        super(cause);
    }



    private Integer errorCode;

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
