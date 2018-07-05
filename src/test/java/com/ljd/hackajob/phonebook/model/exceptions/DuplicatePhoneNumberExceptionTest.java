package com.ljd.hackajob.phonebook.model.exceptions;

import static com.ljd.hackajob.phonebook.util.TestUtils.*;

import javax.ws.rs.core.Response.Status;
public class DuplicatePhoneNumberExceptionTest extends PhonebookExceptionTest {

    private String type = generateRandomAlphanumericString(8);

    @Override
    public int getExpectedHTTPStatus() {
        return Status.CONFLICT.getStatusCode();
    }

    @Override
    public String getExpectedMessageKey() {
        return Messages.PBE0003;
    }

    @Override
    public Object[] getExpectedMessageInserts() {
        return new String[] { type };
    }

    @Override
    public PhonebookException getException() {
        return new DuplicateNumberException(type);
    }

}
