package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class SimpleStackTest.
 */
public class SimpleStackTest {
    /**
     * Test methods push and poll.
     */
    @Test
    public void whenElementPushLastThenThisElementReturnFirst() {
        SimpleStack<Integer> sq = new SimpleStack<>();

        sq.push(1);
        sq.push(2);
        sq.push(3);
        sq.push(4);
        sq.push(5);
        Integer result = sq.poll();

        assertThat(result, is(5));

    }
}