package ru.job4j.search;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class PriorityQueueTest {
    @Test
    public void whenHigherPriority() {
        PriorityQueue queue = new PriorityQueue();
        queue.put(new Task("five", 5));
        queue.put(new Task("one", 1));
        queue.put(new Task("two", 2));
        queue.put(new Task("three", 3));
        queue.put(new Task("four", 4));
        queue.put(new Task("six", 6));
        queue.put(new Task("seven", 7));
        queue.put(new Task("nine", 9));
        queue.put(new Task("ten", 10));
        queue.put(new Task("zero", 0));
        queue.put(new Task("eight", 8));
        Task result = queue.take();
        assertThat(result.getDesc(), is("zero"));
    }
}