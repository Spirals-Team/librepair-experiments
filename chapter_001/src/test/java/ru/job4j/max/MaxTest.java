package ru.job4j.max;

/**
 * @autor Alexander Kaleganov
 * @version MAxTest 1.000
 * @since 28.01.2018 21:19
 */

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class MaxTest {
    Max maximum = new Max();
    @Test
    public void whenFirstLessSeconds() {

        int result = maximum.max(1, 2);
        assertThat(result, is(2));
    }
    public void whenFirstLessSecondsLessThird() {

        int result = maximum.max3(1, 2, 3);
        assertThat(result, is(3));
    }
}
