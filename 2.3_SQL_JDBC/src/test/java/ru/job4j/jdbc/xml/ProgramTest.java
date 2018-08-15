package ru.job4j.jdbc.xml;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * class ProgramTest.
 */
public class ProgramTest {
    /**
     * @param program program
     */
    private Program program = new Program();

    /**
     * Test №1.
     */
    @Test
    public void ifAdd1000000ThenResultIs1784293664() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        program.init(1000000);
        int expect = 0;
        for (int i = 1; i <= 1000000; i++) {
            expect = expect + i;
        }
        assertThat(os.toString(), is(String.valueOf(expect)));
    }

    /**
     * Test №2.
     */
    @Test
    public void ifAdd10ThenResultIs55() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);

        program.init(10);
        int expect = 0;
        for (int i = 1; i <= 10; i++) {
            expect = expect + i;
        }
        assertThat(os.toString(), is(String.valueOf(expect)));
    }

}