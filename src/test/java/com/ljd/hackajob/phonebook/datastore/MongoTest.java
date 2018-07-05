package com.ljd.hackajob.phonebook.datastore;

import java.util.UUID;

import org.junit.AfterClass;
import org.junit.BeforeClass;

import com.ljd.hackajob.phonebook.util.TestUtils;

public abstract class MongoTest {
    protected static PhonebookService service;
    protected static String dbName;
    
    @BeforeClass
    public static void beforeAllBase() {
        dbName = String.format("testdb_%s", UUID.randomUUID().toString());
        System.out.println("test db: " + dbName);
        service = new PhonebookServiceFactory()
                .withMongoClient(TestUtils.getMongoClient())
                .withDBName(dbName)
                .build();
    }
    
    @AfterClass
    public static void afterAllBase() {
        TestUtils.getMongoClient().dropDatabase(dbName);
    }
}
