package ru.job4j.comparator;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

import java.util.Arrays;

public class ListCompareTest {
    @Test
    public void whenLeftAndRightEqualsThenZero() {
        ListCompare compare = new ListCompare();
        int result = compare.compare(
                Arrays.asList(1, 2, 3),
                Arrays.asList(1, 2, 3)
        );
        assertThat(result, is(0));
    }

    @Test
    public void whenLeftLessThanRightThenMinus() {
        ListCompare compare = new ListCompare();
        int result = compare.compare(
                Arrays.asList(1, 2, 3),
                Arrays.asList(1)
        );
        assertThat(result, is(-1));
    }

    @Test
    public void whenLeftGreaterThanRightThenPlus() {
        ListCompare compare = new ListCompare();
        int result = compare.compare(
                Arrays.asList(1, 2),
                Arrays.asList(1, 1)
        );
        assertThat(result, is(1));
    }
}
