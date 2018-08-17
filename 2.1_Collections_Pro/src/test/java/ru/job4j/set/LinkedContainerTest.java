package ru.job4j.set;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class LinkedContainerTest.
 */
public class LinkedContainerTest {
    /**
     * Test method add and get.
     */
    @Test
    public void whenAddElementInContainterThenGetThisElement() {
        LinkedContainer<Integer> lk = new LinkedContainer<>();

        lk.add(1);
        lk.add(2);
        lk.add(3);
        lk.add(4);
        int result = lk.get(3);
        assertThat(result, is(4));

    }
}