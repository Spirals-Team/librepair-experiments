package ru.job4j.arr;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class CalkTest {
    @Test
    public void cal2ProverkPRIMERA() {
        double expected = 4;
        double test = Calk.kkkalCullator("2+2");
        assertThat(test, is(expected));
    }
}
