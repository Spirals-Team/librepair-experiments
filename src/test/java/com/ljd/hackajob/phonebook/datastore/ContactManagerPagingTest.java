package com.ljd.hackajob.phonebook.datastore;

import static com.ljd.hackajob.phonebook.util.TestUtils.generateRandomAlphanumericString;
import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.ljd.hackajob.phonebook.model.Contact;
import com.ljd.hackajob.phonebook.model.Page;

public class ContactManagerPagingTest extends MongoTest {

    private List<Contact> contacts;
    private long totalCount = 10;
    private ContactManager mgr;

    @Before
    public void beforeEach() {
        mgr = service.getContactManager();

        contacts = new ArrayList<>();
        for (long i=0L; i<totalCount; i++) {
            Contact next = new Contact.ContactBuilder(generateRandomAlphanumericString(8)).withLastName(generateRandomAlphanumericString(10)).build();
            contacts.add(mgr.createContact(next));
        }
    }

    @Test
    public void testGetAllPaged() throws Exception {
        List<Contact> retrieved = new ArrayList<>();
        int limit = 3;
        int offset = 0;

        while (true) {
            Page<Contact> page = mgr.getContacts(offset, limit);
            assertEquals(totalCount, page.getTotalCount());
            assertTrue(page.getResults().size() <= limit);

            if (page.getResults().isEmpty()) {
                break;
            }

            retrieved.addAll(page.getResults());
            offset += limit;
        }

        assertEquals(contacts, retrieved);
    }
}
