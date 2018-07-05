package com.ljd.hackajob.phonebook.api.exceptions;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import javax.ws.rs.core.Response;

import org.junit.Before;
import org.junit.Test;

import com.ljd.hackajob.phonebook.model.ExceptionResponse;
import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;

public abstract class PhonebookExceptionMapperTest {

    private Response response;
        
    protected abstract PhonebookException getException();    
    protected abstract int getExpectedHTTPResponseCode();
    protected abstract String getExpectedMsgId();
    protected abstract String getExpectedMessage();
    
    @Before
    public void beforeEach() {
        PhonebookExceptionMapper mapper = new PhonebookExceptionMapper();
        response = mapper.toResponse(getException());
    }
    
    @Test
    public void testResponseCode() {
        assertEquals(getExpectedHTTPResponseCode(), response.getStatus());
    }
    
    @Test
    public void testResponseType() {
        Object entity = response.getEntity();
        assertNotNull(entity);
        assertTrue(entity instanceof ExceptionResponse);
    }
    
    @Test
    public void validateResponseMsgId() {
        Object entity = response.getEntity();
        ExceptionResponse exResponse = ExceptionResponse.class.cast(entity);
        assertEquals(getExpectedMsgId(), exResponse.getErrorCode());
    }
    
    @Test
    public void testResponseMessage() {
        Object entity = response.getEntity();
        ExceptionResponse exResponse = ExceptionResponse.class.cast(entity);
        assertEquals(getExpectedMessage(), exResponse.getErrorMessage());
        System.out.println(exResponse.getErrorMessage());
    }
}
