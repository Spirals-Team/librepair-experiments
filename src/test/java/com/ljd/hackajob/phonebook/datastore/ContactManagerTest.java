package com.ljd.hackajob.phonebook.datastore;

import static com.ljd.hackajob.phonebook.util.TestUtils.generateRandomAlphanumericString;
import static org.junit.Assert.assertEquals;

import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ljd.hackajob.phonebook.model.Contact;
import com.ljd.hackajob.phonebook.model.exceptions.ContactNotFoundException;
import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;

public class ContactManagerTest extends MongoTest {

    private ContactManager mgr;
    private String firstName;
    private String lastName;
    private Contact contactToCreate;
    
    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void beforeEach() {
        mgr = service.getContactManager();
        firstName = generateRandomAlphanumericString(6);
        lastName = generateRandomAlphanumericString(10);
        contactToCreate = new Contact.ContactBuilder(firstName).withLastName(lastName).build();
    }

    @Test
    public void testCreateContact() throws Exception {
        Contact created = mgr.createContact(contactToCreate);        
        assertEquals(contactToCreate.getFirstName(), created.getFirstName());
        assertEquals(contactToCreate.getLastName(), created.getLastName());
    }

    @Test
    public void testGetContact() throws PhonebookException {
        Contact created = mgr.createContact(contactToCreate);
        assertEquals(created, mgr.getContact(created.getId()));
    }

    @Test
    public void testGetNonExistentContact() throws PhonebookException {
        exception.expect(ContactNotFoundException.class);
        mgr.getContact(UUID.randomUUID());
    }
    
    @Test
    public void testUpdateContact() throws PhonebookException {
        Contact created = mgr.createContact(contactToCreate);
        Contact updates = new Contact.ContactBuilder(created).build();
        updates.setFirstName(generateRandomAlphanumericString(10));
        Contact updated = mgr.updateContact(created.getId(), updates);
        
        assertEquals(created.getId(), updated.getId());
        assertEquals(updates.getFirstName(), updated.getFirstName());
        assertEquals(updates.getLastName(), updated.getLastName());
        
        Contact retrieved = mgr.getContact(updated.getId());
        
        assertEquals(updated, retrieved);
    }
    
    @Test
    public void testUpdateNonExistentContact() throws PhonebookException {
        exception.expect(ContactNotFoundException.class);
        mgr.updateContact(UUID.randomUUID(), contactToCreate);
    }
    
    @Test
    public void testDeleteContact() throws PhonebookException {
        Contact created = mgr.createContact(contactToCreate);
        mgr.deleteContact(created.getId());
        exception.expect(ContactNotFoundException.class);
        mgr.getContact(created.getId());
    }
    
    @Test
    public void testDeleteNonExistentContact() throws PhonebookException {
        exception.expect(ContactNotFoundException.class);
        mgr.deleteContact(UUID.randomUUID());
    }
}
