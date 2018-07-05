package com.ljd.hackajob.phonebook.model.exceptions;

import javax.ws.rs.core.Response.Status;

public class DuplicateNumberException extends PhonebookException {
    private static final long serialVersionUID = -4141463832542831043L;

    private final String type;

    public DuplicateNumberException(String type) {
        super(Status.CONFLICT.getStatusCode(), Messages.PBE0003);
        this.type = type;
    }

    @Override
    public Object[] getMessageInserts() {
        return new String[] { type };
    }
}
