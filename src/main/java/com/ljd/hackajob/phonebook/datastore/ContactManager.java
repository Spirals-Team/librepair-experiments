package com.ljd.hackajob.phonebook.datastore;

import java.util.UUID;

import com.ljd.hackajob.phonebook.model.Contact;
import com.ljd.hackajob.phonebook.model.Page;
import com.ljd.hackajob.phonebook.model.exceptions.ContactNotFoundException;

public interface ContactManager {
    public Contact createContact(Contact contact);
    public Contact getContact(UUID id) throws ContactNotFoundException;
    public Page<Contact> getContacts(int offset, int limit);
    public Contact updateContact(UUID id, Contact contact) throws ContactNotFoundException;
    public void deleteContact(UUID id) throws ContactNotFoundException;
}
