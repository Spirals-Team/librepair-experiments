package ru.job4j.max;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Test.
 * @author Aleksandr Shigin
 * @version $Id$
 * @since 0.1
 */
public class MaxTest {
    /**
     * Test max. maximum of two numbers
     */
    @Test
    public void whenFirstLessSecond() {
        Max maximum = new Max();
        int result = maximum.max(1, 2);
        assertThat(result, is(2));
    }
    /**
     * Test max. maximum of three numbers
     */
    @Test
    public void whenFirstMoreSecondAndThird() {
        Max maximum = new Max();
        int result = maximum.max(3, 2, 1);
        assertThat(result, is(3));
    }
}
