package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class QueueTest {

    @Test
    public void poll() {
        Queue queue = new Queue();
        queue.push("Test1");
        queue.push("Test2");
        queue.push("Test3");
        assertThat(queue.poll(), is("Test1"));
        queue.push("Test4");
        assertThat(queue.poll(), is("Test2"));
        assertThat(queue.poll(), is("Test3"));
        assertThat(queue.poll(), is("Test4"));
    }

}