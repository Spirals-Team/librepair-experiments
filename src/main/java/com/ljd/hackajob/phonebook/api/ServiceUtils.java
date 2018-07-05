package com.ljd.hackajob.phonebook.api;

import java.util.concurrent.atomic.AtomicReference;

import com.ljd.hackajob.phonebook.datastore.PhonebookService;
import com.ljd.hackajob.phonebook.datastore.PhonebookServiceFactory;

/**
 * 
 * @author leodavison
 *
 */
public class ServiceUtils {
    private ServiceUtils() {
        // not meant to be instantiated
    }

    private static AtomicReference<PhonebookService> phonebookService = new AtomicReference<>(null);
    
    
    public static PhonebookService getPhonebookService() {
        PhonebookService ref = phonebookService.get();
        
        if (ref == null) {
            ref = new PhonebookServiceFactory().build();
            phonebookService.compareAndSet(null, ref);
        }
        
        return ref;
    }
}
