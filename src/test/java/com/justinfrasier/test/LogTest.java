package com.justinfrasier.test;

import com.justinfrasier.logger.Log;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import java.io.File;

public class LogTest {

    Log log;

    @Before
    public void setup(){
        log = new Log();
        log.writeln("Hello World");
        log.close();
    }

    @Test
    public void checkForLogFile(){

        log.writeln("This is a test");
        log.close();

        assertTrue(true);
    }


}
