package com.ljd.hackajob.phonebook.model.exceptions;

import java.util.UUID;

import javax.ws.rs.core.Response.Status;

public class ContactNotFoundException extends PhonebookException {
    private static final long serialVersionUID = -3135033199704133876L;

    private final UUID contactId;

    public ContactNotFoundException(UUID id) {
        super(Status.NOT_FOUND.getStatusCode(), Messages.PBE0001);
        this.contactId = id;
    }

    @Override
    public Object[] getMessageInserts() {
        return new String[] { contactId.toString() };
    }
}
