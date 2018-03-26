package ru.job4j.string;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class StringInTest {
    @Test
    public void firstTest() {
        StringIn stringIn = new StringIn();
        String result = "Привет";
        String sub = "иве";
        boolean expected = stringIn.contains(result, sub);
        assertThat(true, is(expected));
    }

    @Test
    public void secondTest() {
        StringIn stringIn = new StringIn();
        String result = "Привет";
        String sub = "ров";
        boolean expected = stringIn.contains(result, sub);
        assertThat(false, is(expected));
    }

    @Test
    public void thirdTest() {
        StringIn stringIn = new StringIn();
        String result = "Привет";
        String sub = "вет";
        boolean expected = stringIn.contains(result, sub);
        assertThat(true, is(expected));
    }
}
