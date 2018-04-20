package com.justinfrasier.test;

import com.justinfrasier.https.HTTPS;
import org.junit.Before;
import org.junit.Test;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class HttpsTest {

    //Checks the that it is getting a json from fantasyfootballnerd.

    HTTPS https;
    @Before
    public void start(){
        https = new HTTPS();
    }

    @Test
    public void CheckSchedule() throws Exception{
        String output = https.sendGet("http://justinfrasier.com/test/test.json");
        ClassLoader classLoader = getClass().getClassLoader();
        File file = new File(classLoader.getResource("test.json").getFile());
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        byte[] buffer = new byte[(int) file.length()];
        bin.read(buffer);
        String response = new String(buffer);
        assertEquals(response,output);
    }

    @Test
    public void CheckScheduleFail(){
        String output = https.sendGet("https://justinfrasier.com/12345");
        assertNull(output);
    }

}
