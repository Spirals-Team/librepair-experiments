package ru.job4j.threads;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.OutputStream;
import java.io.PrintStream;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * class CountCharTest
 */

public class CountCharTest {

    /**
     * Test chars
     */
    @Test
    public void whenStringHasTenCharsThenThreadReturnInfoAboutTenChars() {
        OutputStream os = new ByteArrayOutputStream();
        PrintStream ps = new PrintStream(os);
        System.setOut(ps);
        String word = "Hello World";
        Runnable sc = new CountChar(word);

        sc.run();
        final String line = System.getProperty("line.separator");
        String excepted = String.format("Chars: 10%s", line);
        assertThat(os.toString(), is(excepted));
    }
}