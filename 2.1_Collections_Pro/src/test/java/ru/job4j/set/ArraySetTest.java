package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class ArraySetTest.
 */
public class ArraySetTest {
    /**
     * Test method add.
     */
    @Test
    public void whenAddingEightElementsWithThreeCopiesThenSizeEqualsFive() {
        ArraySet<String> as = new ArraySet<>(3);

        as.add("1");
        as.add("2");
        as.add(null);
        as.add("3");
        as.add("4");
        as.add("3");
        as.add("3");
        as.add("5");
        int result = as.getIndex();

        assertThat(result, is(5));

    }
}