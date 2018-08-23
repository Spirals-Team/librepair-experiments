package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class LinkedSetTest.
 */
public class LinkedSetTest {
    /**
     * Test method add.
     */
    @Test
    public void whenAddingFOurElementsWithOneCopiThenSizeEqualsThree() {
        LinkedSet<String> ls = new LinkedSet();

        ls.add("mama");
        ls.add("papa");
        ls.add("son");
        ls.add("mama");
        int result = ls.getSize();

        assertThat(result, is(3));
    }
}