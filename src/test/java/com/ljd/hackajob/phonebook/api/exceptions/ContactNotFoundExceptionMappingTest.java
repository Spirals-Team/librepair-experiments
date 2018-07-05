package com.ljd.hackajob.phonebook.api.exceptions;

import java.util.UUID;

import javax.ws.rs.core.Response.Status;

import com.ljd.hackajob.phonebook.model.exceptions.ContactNotFoundException;
import com.ljd.hackajob.phonebook.model.exceptions.Messages;
import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;

public class ContactNotFoundExceptionMappingTest extends PhonebookExceptionMapperTest {
    
    private UUID id = UUID.randomUUID();        

    @Override
    protected PhonebookException getException() {
        return new ContactNotFoundException(id);
    }

    @Override
    protected int getExpectedHTTPResponseCode() {
        return Status.NOT_FOUND.getStatusCode();
    }

    @Override
    protected String getExpectedMsgId() {
        return Messages.PBE0001;
    }

    @Override
    protected String getExpectedMessage() {
        return String.format("PBE0001: No Contact found with id '%s'", id.toString());
    }

}
