package ru.job4j.max;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 * @author Yury Matskevich (y.n.matskevich@gmail.com)
 * @version $Id$
 */

public class MaxTest {
    @Test
    public void whenFirstLessSecond() {
        Max maxim = new Max();
        assertThat(maxim.max(1, 2), is(2));
    }

    @Test
    public void whenFirstMoreSecondAndThird() {
        Max maxim = new Max();
        assertThat(maxim.max(3, 2, 1), is(3));
    }
}