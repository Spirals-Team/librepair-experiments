package ru.job4j.condition;
import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class PointTest {
    /**
     * Test is.
     */
    @Test
    public void whenPointOnLineThenTrue() {
        //create of new point
        Point point = new Point(1, 1);
        //execute method - is and get result
        boolean result = point.is(0, 1);
        //assert result by excepted value
        assertThat(result, is(true));
    }
}
