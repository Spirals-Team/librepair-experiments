package ru.job4j.max;


import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * MaxTest class.
 *
 * * @author Artem Lipatov
 * */

public class MaxTest {

    /**
     * * Test.
     * *
     * * @author Artem Lipatov
     * */

    @Test
    public void whenFirstLessSecond() {
        Max maxim = new Max();
        int result = maxim.max(3, 4);
        assertThat(result, is(4));
    }

    /**
     * * Test.
     * *
     * * @author Artem Lipatov
     * */

    @Test
    public void whenFirstLargerThanSecond() {
        Max maxim = new Max();
        int result = maxim.max(5, 2);
        assertThat(result, is(5));
    }

    @Test
    public void whenFirstIsMax() {
        Max maxim = new Max();
        int result = maxim.max(23, 4, 5);
        assertThat(result, is(23));
    }

    @Test
    public void whenSecondIsMax() {
        Max maxim = new Max();
        int result = maxim.max(8, 65, -4);
        assertThat(result, is(65));
    }

    @Test
    public void whenThirdIsMax() {
        Max maxim = new Max();
        int result = maxim.max(54, 7, 99);
        assertThat(result, is(99));
    }
}
