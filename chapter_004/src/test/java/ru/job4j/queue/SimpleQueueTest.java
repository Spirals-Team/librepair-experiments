package ru.job4j.queue;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class SimpleQueueTest {
    @Test
    public void addAndPullTest() {
        SimpleQueue<Integer> queue = new SimpleQueue<>();
        queue.add(1);
        queue.add(2);
        queue.add(3);

        assertThat(queue.poll(), is(1));
        assertThat(queue.poll(), is(2));
        assertThat(queue.poll(), is(3));
    }
}