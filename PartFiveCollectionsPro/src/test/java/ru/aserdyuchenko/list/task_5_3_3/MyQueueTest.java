package ru.aserdyuchenko.list.task_5_3_3;


import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author anton.
 */
public class MyQueueTest {
    /**
     * Test queue.
     */
    @Test
    public void whenAddAndGetFromQueue() {
        MyQueue<String> queue = new MyQueue();
        queue.push("First");
        queue.push("Last");
        String result = queue.pop();
        assertThat(result, is("First"));
    }
}