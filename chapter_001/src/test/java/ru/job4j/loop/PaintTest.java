package ru.job4j.loop;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.core.Is.is;
/**
 * Paint.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class PaintTest {
    /**
     * Pyramid with height of 2 rows.
     */
    @Test
    public void whenPyramidWithHeightTwoThenStringWithTwoRows() {
        Paint paint = new Paint();
        String result = paint.pyramid(2);
        String expected = String.format(" ^ %s^^^", System.getProperty("line.separator"));
        assertThat(result, is(expected));
    }
    /**
     * Pyramid with height of 3 rows.
     */
    @Test
    public void whenPyramidWithHeightThreeThenStringWithThreeRows() {
        Paint paint = new Paint();
        String result = paint.pyramid(3);
        final String line = System.getProperty("line.separator");
        String expected = String.format("  ^  %s ^^^ %s^^^^^", line, line);
        assertThat(result, is(expected));
    }
}
