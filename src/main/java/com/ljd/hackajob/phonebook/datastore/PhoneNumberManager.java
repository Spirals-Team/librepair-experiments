package com.ljd.hackajob.phonebook.datastore;

import java.util.UUID;

import com.ljd.hackajob.phonebook.model.Page;
import com.ljd.hackajob.phonebook.model.PhoneNumber;
import com.ljd.hackajob.phonebook.model.exceptions.ContactNotFoundException;
import com.ljd.hackajob.phonebook.model.exceptions.DuplicateNumberException;
import com.ljd.hackajob.phonebook.model.exceptions.PhoneNumberNotFoundException;

public interface PhoneNumberManager {
    public PhoneNumber addPhoneNumberToContact(UUID contactId, PhoneNumber number) throws ContactNotFoundException, DuplicateNumberException;
    public Page<PhoneNumber> getPhoneNumbersForContact(UUID contactId, int offset, int limit) throws ContactNotFoundException;
    public PhoneNumber getPhoneNumerForContact(UUID contactId, String type) throws ContactNotFoundException, PhoneNumberNotFoundException;
    public PhoneNumber updatePhoneNumberForContact(UUID contactId, String type, PhoneNumber number) throws ContactNotFoundException, PhoneNumberNotFoundException;
    public void deletePhoneNumberForContact(UUID contactId, String type) throws ContactNotFoundException, PhoneNumberNotFoundException;
}
