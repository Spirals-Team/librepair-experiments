package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class SimpleStackTest {

    private SimpleStack<Integer> ints;

    @Test(expected = NullPointerException.class)
    public void givenEmptyListWhenPollItemsThenThrowException() {
        ints = new SimpleStack<>();
        ints.poll();
    }

    @Test(expected = NullPointerException.class)
    public void givenListWhenPushPollItemsOneByOneThenThrowException() {
        ints = new SimpleStack<>();
        ints.push(1);
        ints.push(2);
        assertThat(ints.poll(), is(2));
        assertThat(ints.poll(), is(1));
        ints.poll();
    }
}
