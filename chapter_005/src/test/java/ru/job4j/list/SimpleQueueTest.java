package ru.job4j.list;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleQueueTest {

    @Test
    public void whenPollThenFirstElementDeleted() {
        SimpleQueue<Integer> queue = new SimpleQueue<>();
        queue.push(1);
        queue.push(2);
        queue.poll();
        assertThat(queue.get(0), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenPollInEmptyQueueThenThrowNoSuchElementException() {
        SimpleQueue<Integer> queue = new SimpleQueue<>();
        queue.push(1);
        assertThat(queue.poll(), is(1));
        queue.poll();
    }
}