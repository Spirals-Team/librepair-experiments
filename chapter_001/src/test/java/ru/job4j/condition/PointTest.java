package ru.job4j.condition;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 *Test is the point (x,y) find's on line y(x) = a * x + b.
 *
 *@author Afonin Sergey (mailto:afonin1c@mail.ru)
 *
 *@version 1
 *
 *@since 06.10.2017
 */

public class PointTest {

    /**
     * is the point (x,y) find's on line y(x) = a * x + b.
     */
    @Test
    public void whenPointOnLineThenTrue() {
        //create of new point.
        Point a = new Point(1, 1);
        // execute method - is and get result;
        boolean rsl = a.is(0, 1);
        // assert result by excepted value.
        assertThat(rsl, is(true));
    }
}
