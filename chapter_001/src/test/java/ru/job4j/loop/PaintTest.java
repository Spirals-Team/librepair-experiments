package ru.job4j.loop;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PaintTest {
    @Test
    public void piramidThree() {
        Paint paint = new Paint();
        String result = paint.piramid(3);
        String expected = String.format("^\\n^^^\\n^^^^^\\n");
        assertThat(result, is(expected));
    }

    @Test
    public void piramidFour() {
        Paint paint = new Paint();
        String result = paint.piramid(4);
        String expected = String.format("^\\n^^^\\n^^^^^\\n^^^^^^^\\n");
        assertThat(result, is(expected));
    }

    @Test
    public void piramidOne() {
        Paint paint = new Paint();
        String result = paint.piramid(1);
        String expected = String.format("^\\n");
        assertThat(result, is(expected));
    }
}
