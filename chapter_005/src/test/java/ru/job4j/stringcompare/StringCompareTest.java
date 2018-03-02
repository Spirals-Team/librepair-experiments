package ru.job4j.stringcompare;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class StringCompareTest {

    @Test
    public void whenStringsMatchThenReturnTrue() {
        StringCompare sc = new StringCompare();
        boolean result = sc.compare("mama", "amam");
        assertThat(result, is(true));
    }

    @Test
    public void whenStringsNoMatchThenReturnFalse() {
        StringCompare sc = new StringCompare();
        boolean result = sc.compare("mama", "nmam");
        assertThat(result, is(false));
    }
}