package com.ljd.hackajob.phonebook.model.exceptions;

import static com.ljd.hackajob.phonebook.util.TestUtils.*;

import javax.ws.rs.core.Response.Status;
public class PhoneNumberNotFoundExceptionTest extends PhonebookExceptionTest {

    private String type = generateRandomAlphanumericString(8);

    @Override
    public int getExpectedHTTPStatus() {
        return Status.NOT_FOUND.getStatusCode();
    }

    @Override
    public String getExpectedMessageKey() {
        return Messages.PBE0002;
    }

    @Override
    public Object[] getExpectedMessageInserts() {
        return new String[] { type };
    }

    @Override
    public PhonebookException getException() {
        return new PhoneNumberNotFoundException(type);
    }

}
