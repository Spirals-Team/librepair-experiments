package com.ljd.hackajob.phonebook.model.exceptions;

import static org.junit.Assert.*;

import org.junit.Test;

public abstract class PhonebookExceptionTest {
    public abstract int getExpectedHTTPStatus();
    public abstract String getExpectedMessageKey();
    public abstract Object[] getExpectedMessageInserts();
    public abstract PhonebookException getException();
    
    @Test
    public void testHttpStatus() {
        assertEquals(getExpectedHTTPStatus(), getException().getHttpStatus());
    }
    
    @Test
    public void testMessageKey() {
        assertEquals(getExpectedMessageKey(), getException().getMessageKey());
    }
    
    @Test
    public void testMessageInserts() {
        assertArrayEquals(getExpectedMessageInserts(), getException().getMessageInserts());
    }
}
