package com.ljd.hackajob.phonebook.model.exceptions;

import java.util.UUID;

import javax.ws.rs.core.Response.Status;

import org.junit.Before;

public class ContactNotFoundExceptionTest extends PhonebookExceptionTest {

    private UUID id;
    private ContactNotFoundException exception;

    @Before
    public void beforeEach() {
        id = UUID.randomUUID();
        exception = new ContactNotFoundException(id);
    }

    @Override
    public int getExpectedHTTPStatus() {
        return Status.NOT_FOUND.getStatusCode();
    }

    @Override
    public String getExpectedMessageKey() {
        return Messages.PBE0001;
    }

    @Override
    public Object[] getExpectedMessageInserts() {
        return new String[] { id.toString() };
    }

    @Override
    public PhonebookException getException() {
        return exception;
    }
}
