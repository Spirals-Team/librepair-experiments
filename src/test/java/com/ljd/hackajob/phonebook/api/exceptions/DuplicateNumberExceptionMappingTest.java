package com.ljd.hackajob.phonebook.api.exceptions;

import javax.ws.rs.core.Response.Status;

import com.ljd.hackajob.phonebook.model.exceptions.DuplicateNumberException;
import com.ljd.hackajob.phonebook.model.exceptions.Messages;
import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;
import com.ljd.hackajob.phonebook.util.TestUtils;

public class DuplicateNumberExceptionMappingTest extends PhonebookExceptionMapperTest {

    private String type = TestUtils.generateRandomAlphanumericString(8);

    @Override
    protected PhonebookException getException() {
        return new DuplicateNumberException(type);
    }

    @Override
    protected int getExpectedHTTPResponseCode() {
        return Status.CONFLICT.getStatusCode();
    }

    @Override
    protected String getExpectedMsgId() {
        return Messages.PBE0003;
    }

    @Override
    protected String getExpectedMessage() {
        return String.format("PBE0003: A Phone Number with type '%s' already exists in this Contact.", type);
    }

}
