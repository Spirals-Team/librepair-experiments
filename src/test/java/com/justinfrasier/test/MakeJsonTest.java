package com.justinfrasier.test;

import com.justinfrasier.storage.MakeJsonFile;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.File;

import static org.junit.Assert.*;

public class MakeJsonTest {

    MakeJsonFile file;
    @Before
    public void before(){file = new MakeJsonFile();}
    @Test
    public void MakesFile(){assertTrue(file.makeJsonFile("test","test","test",1,"StoreState.json"));}

    @Test
    public void FailMakingTheFile(){assertFalse(file.makeJsonFile("test","test","test",1,"test/test.json"));}

    @After
    public void cleanUp(){
        File file = new File("StoreState.json");
        if(file.exists()) file.delete();
    }


}
