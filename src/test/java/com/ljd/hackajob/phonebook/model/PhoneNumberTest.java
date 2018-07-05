package com.ljd.hackajob.phonebook.model;

import static com.ljd.hackajob.phonebook.util.TestUtils.*;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class PhoneNumberTest extends ModelTest {
    
    private String type;
    private String number;
    private PhoneNumber phoneNumber;
    
    @Before
    public void beforeEach() {
        type = generateRandomAlphanumericString(8);
        number = generateRandomNumericString(12);
        phoneNumber = new PhoneNumber(type, number);
    }
    
    @Test
    public void testGetType() {
        assertEquals(type, phoneNumber.getType());        
    }
    
    @Test
    public void testGetNumber() {
        assertEquals(number, phoneNumber.getNumber());
    }
    
    @Test
    public void testSetNumber() {
        String newNumber = generateRandomNumericString(12);
        phoneNumber.setNumber(newNumber);
        assertEquals(newNumber, phoneNumber.getNumber());
    }
    
    @Test
    public void testSerialisation() throws Exception {
        String json = mapper.writeValueAsString(phoneNumber);
        assertTrue(json.contains(type));
        assertTrue(json.contains(number));        
    }
    
    @Test
    public void testDeserialisation() throws Exception {
        String json = String.format("{\"type\":\"%s\",\"number\":\"%s\"}", type, number);
        PhoneNumber deserialised = mapper.readValue(json, PhoneNumber.class);
        assertEquals(type, deserialised.getType());
        assertEquals(number, deserialised.getNumber());
    }
}
