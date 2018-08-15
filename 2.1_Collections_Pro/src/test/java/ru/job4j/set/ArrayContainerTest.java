package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class ArrayContainerTest.
 */
public class ArrayContainerTest {
    /**
     * Test method add and get.
     */
    @Test
    public void whenAddElementInContainterThenGetThisElement() {
        SimpleContainer<Integer> ac = new ArrayContainer<>(4);
        ac.add(5);
        ac.add(4);
        ac.add(3);
        ac.add(7);
        ac.add(9);
        ac.add(4);
        Integer result = ac.get(5);
        assertThat(result, is(4));
    }
}