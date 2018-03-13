package ru.job4j.loop;

import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Test building a pyramid in pseudo-graphics..
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 10.10.2017
 */
public class PaintTest {
    /**
     * Test pain pyramid with height equals two.
     */
    @Ignore
    @Test
    public void whenPiramidWithHeightTwoThenStringWithTwoRows() {
        Paint paint = new Paint();
        String result = paint.piramid(2);
        String expected = String.format(" ^ %s^^^", System.getProperty("line.separator"));
        assertThat(result, is(expected));
    }

    /**
     * Test pain pyramid with height equals three.
     */
    @Ignore
    @Test
    public void whenPiramidWithHeightThreeThenStringWithThreeRows() {
        //напишите здесь тест, проверяющий формирование пирамиды для высоты 3.
        Paint paint = new Paint();
        String result = paint.piramid(3);
        final String line = System.getProperty("line.separator");
        String expected = String.format("  ^  %s ^^^ %s^^^^^", line, line, line);
        assertThat(result, is(expected));
    }
}