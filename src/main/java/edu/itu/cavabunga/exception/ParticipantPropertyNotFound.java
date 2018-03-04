package edu.itu.cavabunga.exception;

public class ParticipantPropertyNotFound extends RuntimeException {

        public ParticipantPropertyNotFound(){

        }

        public ParticipantPropertyNotFound(String message){
            super(message);
        }

        public ParticipantPropertyNotFound(Throwable cause){
            super(cause);
        }

        public ParticipantPropertyNotFound(String message, Throwable cause){
            super(message,cause);
        }

}
