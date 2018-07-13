package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PaintTest {
    final String line = System.getProperty("line.separator");

    @Test
    public void whenPiramidWithHeightTwoThenStringWithTwoRows() {
        Paint paint = new Paint();
        String result = paint.piramid(2);
        String expected = " X "+line+"XXX"+line;
        assertThat(result, is(expected));
    }

    @Test
    public void whenPiramidWithHeightThreeThenStringWithThreeRows() {
        Paint paint = new Paint();
        String result = paint.piramid(3);
        String expected = "  X  "+line+" XXX "+line+"XXXXX"+line;
        assertThat(result, is(expected));
    }
}
