package ru.job4j.loop;

import org.junit.Test;
import java.util.StringJoiner;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 * @author Yury Matskevich (y.n.matskevich@gmail.com)
 * @version $Id$
 */
public class PaintTest {
    @Test
    public void whenPyramid2Right() {
        Paint paint = new Paint();
        String rst = paint.pyramid(2);
        System.out.println(rst);
        assertThat(rst,
                is(new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                                .add(" ^ ")
                                .add("^^^")
                                .toString()
                )
        );
    }

    @Test
    public void whenPyramid3Right() {
        Paint paint = new Paint();
        String rst = paint.pyramid(3);
        System.out.println(rst);
        assertThat(rst,
                is(new StringJoiner(System.lineSeparator(), "", System.lineSeparator())
                        .add("  ^  ")
                        .add(" ^^^ ")
                        .add("^^^^^")
                        .toString()
                )
        );
    }
}