package ru.job4j.loop;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import java.util.StringJoiner;


public class PaintTest {
    @Test
    public void whenPyramid4Right() {
        Paint paint = new Paint();
        String result = paint.rightTrl(4);
        System.out.println(result);
        assertThat(result,
                is(new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
        .add("^   ")
        .add("^^  ")
        .add("^^^ ")
        .add("^^^^")
        .toString()
        )
        );

    }
    @Test
    public void whenPyramid4Left() {
        Paint paint = new Paint();
        String result = paint.lefthtTrl(4);
        System.out.println(result);
        assertThat(result,
                is(new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("   ^")
                        .add("  ^^")
                        .add(" ^^^")
                        .add("^^^^")
                        .toString()
                )
        );

    }
    @Test
    public void whenPyramid2() {
        Paint paint = new Paint();
        String result = paint.pyramid(2);
        System.out.println(result);
        assertThat(result,
                is(new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add(" ^ ")
                        .add("^^^")
                        .toString()
                )
        );

    }
    @Test
    public void whenPyramid3() {
        Paint paint = new Paint();
        String result = paint.pyramid(3);
        System.out.println(result);
        assertThat(result,
                is(new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("  ^  ")
                        .add(" ^^^ ")
                        .add("^^^^^")
                        .toString()
                )
        );

    }
}