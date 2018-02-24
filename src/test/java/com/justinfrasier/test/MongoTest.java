package com.justinfrasier.test;

import com.justinfrasier.database.Mongo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class MongoTest {

    Mongo mongo;

    @Before
    public void setup(){
        mongo = new Mongo("mongodb://app:citation10@ds129143.mlab.com:29143/leaderboard");
    }
    @Test
    public void registerTest(){
        assertTrue(mongo.register("cerealkiller","password","email"));
    }

    @Test
    public void checkUserNameIsClear(){
        mongo.register("cerealkiller","password","email");
        assertFalse(mongo.checkIfUserNameIsBeingUsed("happy"));
    }

    @Test
    public void checkUserNameIsNotClear(){
        mongo.register("cerealkiller","password","email");
        assertTrue(mongo.checkIfUserNameIsBeingUsed("cerealkiller"));
    }

    @Test
    public void getPasswordCheck(){
        mongo.register("cerealkiller","password","email");
        assertEquals("password",mongo.getPassword("cerealkiller"));
    }

    @After
    public void after(){
        mongo.cleanForTest("cerealkiller");
    }
}
