package com.ljd.hackajob.phonebook.model.exceptions;

public abstract class PhonebookException extends Exception {
    private static final long serialVersionUID = 6232796731075398933L;
        
    private final int httpStatus;
    private final String messageBundle;
    private final String messageKey;

    public PhonebookException(int httpStatus, String messageKey) {
        this.messageBundle = Messages.BUNDLE_NAME;
        this.httpStatus = httpStatus;
        this.messageKey = messageKey;        
    }

    public int getHttpStatus() {        
        return httpStatus;
    }
    
    public String getMessageBundle() {
        return messageBundle;
    }
    
    public String getMessageKey() {
        return messageKey;
    }
    
    public abstract Object[] getMessageInserts();
}
