package ru.job4j.inputandoutput;

import org.apache.log4j.Logger;
import org.junit.Test;
import java.io.*;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class CheckByteInputStreamTest {
    private static final Logger LOG = Logger.getLogger(CheckByteInputStreamTest.class);
    CheckByteInputStream checkByteInputStream = new CheckByteInputStream();

    @Test
    public void whereTestMetodIsNumberPutNumbersAndNotNumber() {
        ByteArrayInputStream notNumber = new ByteArrayInputStream(new String("Test").getBytes());
        ByteArrayInputStream number1 = new ByteArrayInputStream(new String("24").getBytes());
        ByteArrayInputStream number2 = new ByteArrayInputStream(new String("25").getBytes());
        assertTrue(checkByteInputStream.isNumber(number1));
        assertFalse(checkByteInputStream.isNumber(number2));
        assertFalse(checkByteInputStream.isNumber(notNumber));
    }
}