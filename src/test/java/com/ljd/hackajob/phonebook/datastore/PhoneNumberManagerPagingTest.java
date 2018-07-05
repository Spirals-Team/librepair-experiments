package com.ljd.hackajob.phonebook.datastore;

import static com.ljd.hackajob.phonebook.util.TestUtils.generateRandomAlphanumericString;
import static com.ljd.hackajob.phonebook.util.TestUtils.generateRandomNumericString;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ljd.hackajob.phonebook.model.Contact;
import com.ljd.hackajob.phonebook.model.Page;
import com.ljd.hackajob.phonebook.model.PhoneNumber;
import com.ljd.hackajob.phonebook.model.exceptions.ContactNotFoundException;
import com.ljd.hackajob.phonebook.model.exceptions.PhonebookException;

public class PhoneNumberManagerPagingTest extends MongoTest {
    private List<PhoneNumber> numbers;
    private long totalCount = 10;
    private Contact contact;

    private PhoneNumberManager mgr;

    @Rule
    public ExpectedException exception = ExpectedException.none();

    @Before
    public void beforeEach() throws PhonebookException {
        mgr = service.getPhoneNumberManager();

        contact = new Contact.ContactBuilder(generateRandomAlphanumericString(8))
                .withLastName(generateRandomAlphanumericString(12))
                .build();

        contact = service.getContactManager().createContact(contact);

        numbers = new ArrayList<>();

        for (long i=0; i<totalCount; i++) {
            PhoneNumber next = new PhoneNumber(generateRandomAlphanumericString(6), generateRandomNumericString(12));
            numbers.add(mgr.addPhoneNumberToContact(contact.getId(), next));
        }
    }

    @Test
    public void testGetAllPaged() throws PhonebookException {
        List<PhoneNumber> retrieved = new ArrayList<>();
        int limit = 3;
        int offset = 0;

        while (true) {
            Page<PhoneNumber> page = mgr.getPhoneNumbersForContact(contact.getId(), offset, limit);
            assertEquals(totalCount, page.getTotalCount());
            assertTrue(page.getResults().size() <= limit);

            if (page.getResults().isEmpty()) {
                break;
            }

            retrieved.addAll(page.getResults());
            offset += limit;
        }

        assertEquals(numbers, retrieved);
    }

    @Test
    public void testGetAllForUnkownContact() throws PhonebookException {
        exception.expect(ContactNotFoundException.class);
        mgr.getPhoneNumbersForContact(UUID.randomUUID(), 0, 10);
    }
}
