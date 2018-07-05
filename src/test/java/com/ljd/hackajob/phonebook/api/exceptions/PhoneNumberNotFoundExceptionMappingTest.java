package com.ljd.hackajob.phonebook.api.exceptions;

import javax.ws.rs.core.Response.Status;

import com.ljd.hackajob.phonebook.model.exceptions.Messages;
import com.ljd.hackajob.phonebook.model.exceptions.PhoneNumberNotFoundException;
import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;
import com.ljd.hackajob.phonebook.util.TestUtils;

public class PhoneNumberNotFoundExceptionMappingTest extends PhonebookExceptionMapperTest {

    private String type = TestUtils.generateRandomAlphanumericString(8);

    @Override
    protected PhonebookException getException() {
        return new PhoneNumberNotFoundException(type);
    }

    @Override
    protected int getExpectedHTTPResponseCode() {
        return Status.NOT_FOUND.getStatusCode();
    }

    @Override
    protected String getExpectedMsgId() {
        return Messages.PBE0002;
    }

    @Override
    protected String getExpectedMessage() {
        return String.format("PBE0002: No Phone Number found with type '%s'", type);
    }

}
