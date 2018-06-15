package ru.job4j.max;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;



public class MaxTest {
    @Test
    public void whenFirstLessSecond() {
        Max maximum = new Max();
        int result = maximum.max(8, 2);
        assertThat(result, is(8));
    }
    @Test
    public void whenFirstLessSecondLessThird() {
        Max maximum3 = new Max();
        int result = maximum3.max(28, 16, 15);
        assertThat(result, is(28));
    }
    }
