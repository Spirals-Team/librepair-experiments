package com.ljd.hackajob.phonebook.datastore;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import org.mongodb.morphia.Datastore;
import org.mongodb.morphia.aggregation.Accumulator;
import org.mongodb.morphia.aggregation.AggregationPipeline;
import org.mongodb.morphia.aggregation.Group;
import org.mongodb.morphia.aggregation.Projection;
import org.mongodb.morphia.query.FindOptions;
import org.mongodb.morphia.query.Query;
import org.mongodb.morphia.query.UpdateOperations;

import com.ljd.hackajob.phonebook.model.Contact;
import com.ljd.hackajob.phonebook.model.Page;
import com.ljd.hackajob.phonebook.model.PhoneNumber;
import com.ljd.hackajob.phonebook.model.exceptions.ContactNotFoundException;
import com.ljd.hackajob.phonebook.model.exceptions.DuplicateNumberException;
import com.ljd.hackajob.phonebook.model.exceptions.PhoneNumberNotFoundException;

/**
 *
 * @author leodavison
 *
 */
class MongoContactManager extends MongoManagerBase implements ContactManager, PhoneNumberManager {


    public MongoContactManager(final Datastore datastore) {
        super(datastore);
    }

    @Override
    public Contact createContact(Contact contactCreateRequest) {
        Contact newContact = new Contact.ContactBuilder(contactCreateRequest)
                .withId(UUID.randomUUID())
                .build();

        getDatastore().save(newContact);

        return newContact;
    }

    @Override
    public Contact getContact(UUID id) throws ContactNotFoundException {
        Contact contact = getDatastore().get(Contact.class, id);
        if (contact == null) {
            throw new ContactNotFoundException(id);
        }
        return contact;
    }

    @Override
    public Page<Contact> getContacts(int offset, int limit) {
        Query<Contact> q = getDatastore().createQuery(Contact.class);

        FindOptions findOptions = new FindOptions()
                .skip(offset)
                .limit(limit);

        List<Contact> contacts = q.asList(findOptions);
        return new Page<>(contacts, q.count());
    }

    @Override
    public Contact updateContact(UUID id, Contact contact) throws ContactNotFoundException {
        // this will check the we have a contact with the given id
        Contact current = getContact(id);

        String firstName = contact.getFirstName();
        String lastName = contact.getLastName();

        // we will apply valid updates to the current model
        current.setFirstName(firstName);
        current.setLastName(lastName);

        UpdateOperations<Contact> updateOps = getDatastore().createUpdateOperations(Contact.class);
        updateOps.set(Contact.FIELD_FIRST_NAME, firstName);

        if (lastName != null) {
            updateOps.set(Contact.FIELD_LAST_NAME, lastName);
        } else {
            updateOps.unset(Contact.FIELD_LAST_NAME);
        }

        getDatastore().update(current, updateOps);

        return current;
    }

    @Override
    public void deleteContact(UUID id) throws ContactNotFoundException {
        Contact contactToDelete = getContact(id);
        getDatastore().delete(contactToDelete);
    }

    @Override
    public PhoneNumber addPhoneNumberToContact(UUID contactId, PhoneNumber number) throws ContactNotFoundException, DuplicateNumberException {
        // check the contact exists
        Contact contact = getContact(contactId);

        if (phoneNumberExists(contactId, number.getType())) {
            throw new DuplicateNumberException(number.getType());
        }

        number.setContactId(contactId);
        UpdateOperations<Contact> updateOps = getDatastore().createUpdateOperations(Contact.class);
        updateOps.addToSet(Contact.FIELD_PHONENUMBERS, number);

        getDatastore().update(contact, updateOps);

        return number;
    }

    static class PNCountAggResult {
        int totalCount;
    }
    
    static class PNGetAllAggregationResult {
        private final List<PhoneNumber> phoneNumbers = Collections.emptyList();
    }

    @Override
    public Page<PhoneNumber> getPhoneNumbersForContact(UUID contactId, int offset, int limit) throws ContactNotFoundException {
        Query<Contact> q = getDatastore().createQuery(Contact.class);
        q.field(Contact.FIELD_ID).equal(contactId);

        if (q.count() == 0) {
            throw new ContactNotFoundException(contactId);
        }

        Iterator<PNCountAggResult> countIt = getDatastore().createAggregation(Contact.class)
                .match(q)
                .project(Projection.projection(Contact.FIELD_ID), Projection.projection(Contact.FIELD_PHONENUMBERS))
                .unwind(Contact.FIELD_PHONENUMBERS)
                .group(Group.grouping("totalCount", Accumulator.accumulator("$sum", 1)))
                .aggregate(PNCountAggResult.class);

        List<PhoneNumber> matchingNumbers = null;

        int totalCount = 0;

        if (countIt.hasNext()) {
            totalCount += countIt.next().totalCount;
        }

        if (totalCount > 0) {
            AggregationPipeline pipeline = getDatastore().createAggregation(Contact.class)
                    .match(q)
                    .project(Projection.projection(Contact.FIELD_ID), Projection.projection(Contact.FIELD_PHONENUMBERS))
                    .unwind(Contact.FIELD_PHONENUMBERS)
                    .skip(offset)
                    .limit(limit)
                    .group(Group.grouping(Contact.FIELD_PHONENUMBERS, Group.push(Contact.FIELD_PHONENUMBERS)));
            
            Iterator<PNGetAllAggregationResult> it = pipeline.aggregate(PNGetAllAggregationResult.class);
            
            matchingNumbers = new ArrayList<>();
            
            while (it.hasNext()) {
                matchingNumbers.addAll(it.next().phoneNumbers);
            }
        } else {
            matchingNumbers = Collections.emptyList();
        }
        
        return new Page<>(matchingNumbers, totalCount);
    }

    @Override
    public PhoneNumber getPhoneNumerForContact(UUID contactId, String type) throws ContactNotFoundException, PhoneNumberNotFoundException {
        Query<Contact> q = getDatastore().createQuery(Contact.class);
        q.field(Contact.FIELD_ID).equal(contactId);

        if (q.count() == 0) {
            throw new ContactNotFoundException(contactId);
        }

        q.field(Contact.REFERENCE_PHONENUMBERS_TYPE).equal(type);
        q.project(Contact.POSITIONAL_REF_PHONENUMBER, true);

        if (q.count() == 0) {
            throw new PhoneNumberNotFoundException(type);
        }

        return q.get().getPhoneNumbers().get(0);
    }

    @Override
    public PhoneNumber updatePhoneNumberForContact(UUID contactId, String type, PhoneNumber number) throws ContactNotFoundException, PhoneNumberNotFoundException {
        Query<Contact> q = getDatastore().createQuery(Contact.class);
        q.field(Contact.FIELD_ID).equal(contactId);

        if (q.count() == 0) {
            throw new ContactNotFoundException(contactId);
        }

        q.field(Contact.REFERENCE_PHONENUMBERS_TYPE).equal(type);

        PhoneNumber current = getPhoneNumerForContact(contactId, type);
        current.setNumber(number.getNumber());

        UpdateOperations<Contact> updateOps = getDatastore().createUpdateOperations(Contact.class);
        updateOps.set(Contact.POSITIONAL_REF_PHONENUMBER, current);

        getDatastore().update(q, updateOps);

        return current;
    }

    @Override
    public void deletePhoneNumberForContact(UUID contactId, String type) throws ContactNotFoundException, PhoneNumberNotFoundException {
        Query<Contact> q = getDatastore().createQuery(Contact.class);
        q.field(Contact.FIELD_ID).equal(contactId);

        if (q.count() == 0) {
            throw new ContactNotFoundException(contactId);
        }

        PhoneNumber numberToDelete = getPhoneNumerForContact(contactId, type);

        UpdateOperations<Contact> updateOps = getDatastore().createUpdateOperations(Contact.class);
        updateOps.removeAll(Contact.FIELD_PHONENUMBERS, numberToDelete);

        getDatastore().update(q, updateOps);
    }

    private boolean phoneNumberExists(UUID contactId, String type) {
        Query<Contact> q = getDatastore().createQuery(Contact.class);
        q.field(Contact.FIELD_ID).equal(contactId);
        q.field(Contact.REFERENCE_PHONENUMBERS_TYPE).equal(type);
        return q.count() != 0;
    }
}
