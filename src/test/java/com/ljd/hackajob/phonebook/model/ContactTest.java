package com.ljd.hackajob.phonebook.model;

import static com.ljd.hackajob.phonebook.util.TestUtils.generateRandomAlphanumericString;
import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Test;

public class ContactTest extends ModelTest {
    private UUID id;
    private String firstName;
    private String lastName;
    
    private Contact contact;
    
    @Before
    public void beforeEach() {
        id = UUID.randomUUID();
        firstName = generateRandomAlphanumericString(10);
        lastName = generateRandomAlphanumericString(10);
        contact = new Contact.ContactBuilder(firstName)
                .withId(id)
                .withLastName(lastName)
                .build();
    }
    
    @Test
    public void testGetId() {
        assertEquals(id, contact.getId());
    }
    
    @Test
    public void testGetFirstName() {
        assertEquals(firstName, contact.getFirstName());
    }
    
    @Test
    public void testSetFirstName() {
        String newFirstName = generateRandomAlphanumericString(10);        
        contact.setFirstName(newFirstName);
        assertEquals(newFirstName, contact.getFirstName());
    }
    
    @Test
    public void testGetLastName() {
        assertEquals(lastName, contact.getLastName());
    }
    
    @Test
    public void testSetLastName() {
        String newLastName = generateRandomAlphanumericString(10);        
        contact.setLastName(newLastName);
        assertEquals(newLastName, contact.getLastName());
    }
    
    @Test
    public void testEquals() {
        Contact other = new Contact.ContactBuilder(contact)
                .withId(id)
                .build();
        
        assertEquals(contact, other);
    }
    
    @Test
    public void testIdNotEquals() {
        Contact other = new Contact.ContactBuilder(firstName)
                .withId(UUID.randomUUID())
                .withLastName(lastName)
                .build();
        
        assertNotEquals(contact, other);
    }
    
    @Test
    public void testHashCodeEquals() {
        Contact other = new Contact.ContactBuilder(firstName)
                .withId(id)
                .withLastName(lastName)
                .build();
        
        assertEquals(contact.hashCode(), other.hashCode());
    }
    
    @Test
    public void testHashCodeNotEquals() {
        Contact other = new Contact.ContactBuilder(firstName)
                .withId(id)
                .withLastName(generateRandomAlphanumericString(12))
                .build();
        
        assertNotEquals(contact.hashCode(), other.hashCode());
    }
    
    @Test
    public void testSerialisation() throws Exception {
        String json = mapper.writeValueAsString(contact);
        assertTrue(json.contains(id.toString()));
        assertTrue(json.contains(firstName));
        assertTrue(json.contains(lastName));        
    }
    
    @Test
    public void testDeserialisation() throws Exception {
        String json = String.format("{\"id\":\"%s\",\"firstName\":\"%s\",\"lastName\":\"%s\"}", id.toString(), firstName, lastName);
        Contact deserialised = mapper.readValue(json, Contact.class);
        assertEquals(id, deserialised.getId());
        assertEquals(firstName, deserialised.getFirstName());
        assertEquals(lastName, deserialised.getLastName());
    }
}
