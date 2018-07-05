package com.ljd.hackajob.phonebook.model.exceptions;

import javax.ws.rs.core.Response.Status;

public class PhoneNumberNotFoundException extends PhonebookException {
    private static final long serialVersionUID = 1733590427040284263L;
    
    private final String type;

    public PhoneNumberNotFoundException(String type) {
        super(Status.NOT_FOUND.getStatusCode(), Messages.PBE0002);
        this.type = type;
    }

    @Override
    public Object[] getMessageInserts() {
        return new String[] { type };
    }

}
