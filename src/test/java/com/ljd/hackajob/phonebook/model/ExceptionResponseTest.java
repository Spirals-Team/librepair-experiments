package com.ljd.hackajob.phonebook.model;

import static com.ljd.hackajob.phonebook.util.TestUtils.generateRandomAlphanumericString;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class ExceptionResponseTest {
    private String errorCode;
    private String errorMessage;
    
    private ExceptionResponse response;
    
    @Before
    public void beforeEach() {
        errorCode = generateRandomAlphanumericString(6);
        errorMessage = generateRandomAlphanumericString(32);
        response = new ExceptionResponse(errorCode, errorMessage);
    }
    
    @Test
    public void testGetErrorCode() {
        assertEquals(errorCode, response.getErrorCode());
    }
    
    @Test
    public void testGetErrorMessage() {
        assertEquals(errorMessage, response.getErrorMessage());
    }
}
