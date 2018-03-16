package ru.job4j.strategypattern;

import org.junit.Test;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
/**
 * PaintTest.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class PaintTest {
    /*** Draw triangle.*/
    @Test
    public void whenTriangleThenDrawTriangle() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Paint paint = new Paint();
        paint.draw(new Triangle());

        String expectString = "   *   \n  ***  \n ***** \n*******";
        assertThat(out.toString(), is(expectString));
    }
    /*** Draw square.*/
    @Test
    public void whenSquareThenDrawSquare() {
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        System.setOut(new PrintStream(out));

        Paint paint = new Paint();
        paint.draw(new Square());

        String expectString = "*******\n*******\n*******\n*******";
        assertThat(out.toString(), is(expectString));
    }
}
