package com.justinfrasier.test;

import com.justinfrasier.database.Id;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

public class IdTest {

    Id id;

    @Before
    public void before(){
        id = new Id();
    }

    @Test
    public void idSaveTest(){
        assertTrue(id.save("1234567890","Cerealkiller","qwertyuiop"));
    }

    @Test
    public void getIdFromJsonTest(){
        assertEquals(id.getIdFromJson(),"1234567890");
    }

    @Test
    public void getUsernameFromJsonTest(){
        assertEquals(id.getUsernameFromJson(),"Cerealkiller");
    }

    @Test
    public void getPasswordFromJsonTest(){
        assertEquals(id.getPasswordFromJson(),"qwertyuiop");
    }


}
