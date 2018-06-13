package ru.job4j.comparator;

import org.junit.Test;

import static org.hamcrest.Matchers.lessThan;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.MatcherAssert.assertThat;

public class StringsCompareTest {
    @Test
    public void whenStringAreEqualThenZero() {
        ListCompare listCompare = new ListCompare();
        int result = listCompare.compare(
                "Ivanov",
                "Ivanov");
        assertThat(result, is(0));
    }

    @Test
    public void whenLeftLessThanRightResultSholdBeNegative() {
        ListCompare listCompare = new ListCompare();
        int result = listCompare.compare(
                "Ivanov",
                "Ivanova");
        assertThat(result, lessThan(0));
    }

    @Test
    public void whenLeftGreaterThanRightResultShouldBePositive() {
        ListCompare listCompare = new ListCompare();
        int result = listCompare.compare(
                "Petrov",
                "Ivanova");
        assertThat(result, greaterThan(0));
    }

    @Test
    public void secondCharOfLeftGreaterThanRightShouldBePositive() {
        ListCompare compare = new ListCompare();
        int rst = compare.compare(
                "Petrov",
                "Patrov"
        );
        assertThat(rst, greaterThan(0));
    }

    @Test
    public void secondCharOfLeftLessThanRightShouldBeNegative() {
        ListCompare compare = new ListCompare();
        int rst = compare.compare(
                "Patrova",
                "Petrov"
        );
        assertThat(rst, lessThan(0));
    }
}
