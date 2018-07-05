package com.ljd.hackajob.phonebook.datastore;

import org.mongodb.morphia.Datastore;

abstract class MongoManagerBase {

    private final Datastore datastore;
    
    public MongoManagerBase(Datastore datastore) {
        this.datastore = datastore;
        this.datastore.ensureIndexes();
    }
    
    protected Datastore getDatastore() {
        return datastore;
    }
}
