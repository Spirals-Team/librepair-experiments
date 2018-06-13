package ru.job4j.strategy;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.StringJoiner;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @version $Id$
 * @since 0.1
 */

public class PaintTest {
    public static String newline = System.getProperty("line.separator");
    private final PrintStream stdout = System.out;
    private final ByteArrayOutputStream out = new ByteArrayOutputStream();

    @Before
    public void loadOutput() {
        System.out.println("execute before method");
        System.setOut(new PrintStream(this.out));
    }

    @After
    public void backOutput() {
        System.out.println(this.stdout);
        System.out.println("execute after method");
    }

    @Test
    public void whenDrawSquare() {
        new Paint().draw(new Square());
        assertThat(out.toString(), is(new StringBuilder()
                        .append("+ + + +").append(newline)
                        .append("+     +").append(newline)
                        .append("+     +").append(newline)
                        .append("+ + + +").append(newline)
                        .append(System.lineSeparator())
                        .toString()
                )
        );
    }

    @Test
    public void whenDrawTriangle() {
        new Paint().draw(new Triangle());
        assertThat(out.toString(), is(new StringBuilder()
                .append("   +   " + newline)
                .append("  + +  " + newline)
                .append(" + + + " + newline)
                .append("+ + + +")
                .append(System.lineSeparator())
                .toString()));
    }
}