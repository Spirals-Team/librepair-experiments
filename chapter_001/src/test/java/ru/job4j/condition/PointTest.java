package ru.job4j.condition;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 * @author Yury Matskevich (y.n.matskevich@gmail.com)
 * @version $Id$
 */
public class PointTest {
    @Test
    public void whenPoint1AndPoint2ThenDistance() {
        Point a = new Point(0, 3);
        Point b = new Point(4, 0);
        double result = a.distanceTo(b);
        double expected = 5;
        assertThat(result, is(expected));
    }
}