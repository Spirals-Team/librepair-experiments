package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
* Test Пирамиды.
* @author Igor Fedorenko (mailto:if.zommy@gmail.com)
* @version $Id$
* @since 0.1
*/
public class PaintTest {
	/**
	* Пирамида 3 яруса.
	*/
	@Test
    public void whenPiramidWithHeightThreeThenStringWithThreeRows() {
        Paint paint = new Paint();
        String result = paint.piramid(3);
        final String line = System.getProperty("line.separator");
        String expected = String.format("  ^%s ^^^%s^^^^^%s", line, line, line);
        assertThat(result, is(expected));
	}
 	/**
	* Пирамида 4 яруса.
	*/
	@Test
    public void whenPiramidWithHeightFourThenStringWithFourRows() {
        Paint paint = new Paint();
        String result = paint.piramid(4);
        final String line = System.getProperty("line.separator");
        String expected = String.format("   ^%s  ^^^%s ^^^^^%s^^^^^^^%s", line, line, line, line);
        assertThat(result, is(expected));
	}
}