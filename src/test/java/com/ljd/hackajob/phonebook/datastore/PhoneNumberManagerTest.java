package com.ljd.hackajob.phonebook.datastore;

import static com.ljd.hackajob.phonebook.util.TestUtils.*;
import static org.junit.Assert.*;

import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ljd.hackajob.phonebook.model.Contact;
import com.ljd.hackajob.phonebook.model.PhoneNumber;
import com.ljd.hackajob.phonebook.model.exceptions.ContactNotFoundException;
import com.ljd.hackajob.phonebook.model.exceptions.DuplicateNumberException;
import com.ljd.hackajob.phonebook.model.exceptions.PhoneNumberNotFoundException;
import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;

public class PhoneNumberManagerTest extends MongoTest {

    private PhoneNumberManager mgr;
    private Contact contact;

    private String type;
    private String number;
    private PhoneNumber numberToCreate;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void beforeEach() {
        mgr = service.getPhoneNumberManager();

        contact = new Contact.ContactBuilder(generateRandomAlphanumericString(10))
                .withLastName(generateRandomAlphanumericString(8))
                .build();

        contact = service.getContactManager().createContact(contact);

        type = generateRandomAlphanumericString(8);
        number = generateRandomNumericString(12);

        numberToCreate = new PhoneNumber(type, number);
    }

    @Test
    public void testCreateNumber() throws PhonebookException {
        PhoneNumber created = mgr.addPhoneNumberToContact(contact.getId(), numberToCreate);
        assertEquals(numberToCreate, created);
        assertNotNull(created.getLinks().get("contact"));
    }
    
    @Test
    public void testCreateNumberForUnknownContact() throws PhonebookException {
        exception.expect(ContactNotFoundException.class);
        mgr.addPhoneNumberToContact(UUID.randomUUID(), numberToCreate);
    }
    
    @Test
    public void testCreateDuplicateNumber() throws PhonebookException {
        mgr.addPhoneNumberToContact(contact.getId(), numberToCreate);
        exception.expect(DuplicateNumberException.class);
        mgr.addPhoneNumberToContact(contact.getId(), numberToCreate);
    }

    @Test
    public void testGetNumber() throws PhonebookException {
        PhoneNumber created = mgr.addPhoneNumberToContact(contact.getId(), numberToCreate);
        PhoneNumber retrieved = mgr.getPhoneNumerForContact(contact.getId(), type);
        assertNotNull(retrieved);
        assertEquals(created, retrieved);
    }
    
    @Test
    public void testGetNonExistentNumber() throws PhonebookException {
        exception.expect(PhoneNumberNotFoundException.class);
        mgr.getPhoneNumerForContact(contact.getId(), generateRandomAlphanumericString(10));
    }
    
    @Test
    public void testGetNumberForNonExistentContact() throws PhonebookException {
        exception.expect(ContactNotFoundException.class);
        mgr.getPhoneNumerForContact(UUID.randomUUID(), type);
    }

    @Test
    public void testUpdateNumber() throws PhonebookException {
        mgr.addPhoneNumberToContact(contact.getId(), numberToCreate);
        PhoneNumber updates = new PhoneNumber(type, generateRandomNumericString(12));
        assertEquals(updates, mgr.updatePhoneNumberForContact(contact.getId(), type, updates));
        assertEquals(updates, mgr.getPhoneNumerForContact(contact.getId(), type));
    }
    
    @Test
    public void testUpdateNonExistentNumber() throws PhonebookException {
        exception.expect(PhoneNumberNotFoundException.class);
        mgr.updatePhoneNumberForContact(contact.getId(), type, numberToCreate);
    }
    
    @Test
    public void testUpdateNumberForUnknownContact() throws PhonebookException {
        exception.expect(ContactNotFoundException.class);
        mgr.updatePhoneNumberForContact(UUID.randomUUID(), type, numberToCreate);
    }

    @Test
    public void testDeleteNumber() throws PhonebookException {
        mgr.addPhoneNumberToContact(contact.getId(), numberToCreate);
        mgr.deletePhoneNumberForContact(contact.getId(), type);
        exception.expect(PhoneNumberNotFoundException.class);
        mgr.getPhoneNumerForContact(contact.getId(), type);
    }
    
    @Test
    public void testDeleteNumberForUnknownContact() throws PhonebookException {
        exception.expect(ContactNotFoundException.class);
        mgr.deletePhoneNumberForContact(UUID.randomUUID(), type);
    }
    
    @Test
    public void testDeleteUnknownNumber() throws PhonebookException {
        exception.expect(PhoneNumberNotFoundException.class);
        mgr.deletePhoneNumberForContact(contact.getId(), generateRandomAlphanumericString(10));
    }
}
