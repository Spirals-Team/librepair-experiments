package ru.job4j.condition;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * PointTest class.
 *
 * * @author Artem Lipatov
 * */

public class PointTest {

    /**
     * * Test.
     * *
     * * @author Artem Lipatov
     * */

    @Test
    public void whenPointOnLineThenTrueFirst() {
        Point a = new Point(1, 1);
        boolean result = a.is(0, 1);
        assertThat(result, is(true));
    }

    /**
     * * Test.
     * *
     * * @author Artem Lipatov
     * */

    @Test
    public void whenPointOnLineThenTrueSecond() {
        Point a = new Point(1, 1);
        boolean result = a.is(1, 0);
        assertThat(result, is(true));
    }

    /**
     * * Test.
     * *
     * * @author Artem Lipatov
     * */

    @Test
    public void whenPointIsNotOnLineThenFalse() {
        Point a = new Point(1, 1);
        boolean result = a.is(1, 1);
        assertThat(result, is(false));
    }
}
