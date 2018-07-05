package com.ljd.hackajob.phonebook.datastore;

import java.util.Arrays;

import org.mongodb.morphia.Morphia;
import org.mongodb.morphia.mapping.DefaultCreator;

import com.ljd.hackajob.phonebook.model.Contact;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientOptions;
import com.mongodb.ReadPreference;
import com.mongodb.ServerAddress;
import com.mongodb.WriteConcern;

public class PhonebookServiceFactory {
    private static final String DB_NAME = "phonebook";

    private MongoClient mongoClient;
    private String dbName;

    public PhonebookServiceFactory() {
        // nothing
    }

    private MongoClientOptions createMongoClientOptions() {
        return MongoClientOptions.builder()
                .sslInvalidHostNameAllowed(true)
                .readPreference(ReadPreference.primaryPreferred())
                .writeConcern(WriteConcern.MAJORITY.withJournal(true))
                .connectionsPerHost(100)
                .connectTimeout(30000)
                .build();
    }

    protected MongoClient buildMongoClient() {
        return new MongoClient(Arrays.asList(new ServerAddress("mongo")), createMongoClientOptions());
    }

    public PhonebookServiceFactory withMongoClient(MongoClient mongoClient) {
        this.mongoClient = mongoClient;
        return this;
    }

    public PhonebookServiceFactory withDBName(String dbName) {
        this.dbName = dbName;
        return this;
    }

    public PhonebookService build() {

        if (mongoClient == null) {
            mongoClient = buildMongoClient();
        }

        if (dbName == null) {
            dbName = DB_NAME;
        }

        Morphia morphia = new Morphia();
        morphia.map(Contact.class);
        // this seems to be required when running in liberty to prevent mongo exceptions like 'getClass Class not found defined in dbObj'
        morphia.getMapper().getOptions().setObjectFactory(new DefaultCreator() {
            @Override
            protected ClassLoader getClassLoaderForClass() {
                return Contact.class.getClassLoader();
            }
        });

        return new MongoPhonebookService(morphia.createDatastore(mongoClient, dbName));
    }
}
