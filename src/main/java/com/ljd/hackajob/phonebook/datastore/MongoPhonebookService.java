package com.ljd.hackajob.phonebook.datastore;

import org.mongodb.morphia.Datastore;

class MongoPhonebookService implements PhonebookService {
    private final ContactManager contactManager;
    private final PhoneNumberManager phoneNumberManager;
    
    protected MongoPhonebookService(Datastore datastore) {
        MongoContactManager mgr = new MongoContactManager(datastore); 
        this.contactManager = mgr;
        this.phoneNumberManager = mgr;
    }

    @Override
    public ContactManager getContactManager() {
        return contactManager;
    }       
    
    @Override
    public PhoneNumberManager getPhoneNumberManager() {
        return phoneNumberManager;
    }
}
