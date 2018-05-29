package ru.job4j.array;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ArrayStringCheckTest {
    @Test
    public  void whenStringOriginIncludeStringOrb() {
        ArrayStringCheck word = new ArrayStringCheck();
        boolean result = word.contains("Hello", "ello");
        assertThat(result, is(true));
    }

    @Test
    public void whenStringOriginNonIncludeStringOrb() {
        ArrayStringCheck word = new ArrayStringCheck();
        boolean result = word.contains("Hello", "Hi");
        assertThat(result, is(false));
    }
}
