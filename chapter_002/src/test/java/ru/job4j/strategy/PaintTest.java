package ru.job4j.strategy;

import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 07.09.17;
 * @version $Id$
 * @since 0.1
 */
public class PaintTest {
    /**
     * Test Square.
     * @throws Exception ex.
     */
    @Test
    public void whenShapeSquareThenTaintDrawSquae() throws Exception {
        PrintStream consoleStream = System.out;
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(byteArray);
        System.setOut(printStream);
        Paint paint = new Paint();
        paint.draw(new Square());
        String rezult = byteArray.toString();
        System.setOut(consoleStream);
        StringBuilder sb = new StringBuilder();
        sb.append("* * *").append(System.getProperty("line.separator"));
        sb.append("* * *").append(System.getProperty("line.separator"));
        sb.append("* * *").append(System.getProperty("line.separator"));
        String ex = sb.toString();
        assertThat(rezult, is(ex));
    }

    /**
     * Test traingle.
     * @throws Exception ex.
     */
    @Test
    public void whenShapeIsTraingleThenPaintDrawTraingle() throws Exception {
        PrintStream printStream = System.out;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PrintStream stream = new PrintStream(byteArrayOutputStream);
        System.setOut(stream);
        Paint paint = new Paint();
        paint.draw(new Triangle());
        String rezult = byteArrayOutputStream.toString();
        System.setOut(printStream);
        StringBuilder sb = new StringBuilder();
        sb.append("*").append(System.getProperty("line.separator"));
        sb.append("* * *").append(System.getProperty("line.separator"));
        sb.append("* * * *").append(System.getProperty("line.separator"));
        String ex = sb.toString();
        assertThat(rezult, is(ex));

    }
}
