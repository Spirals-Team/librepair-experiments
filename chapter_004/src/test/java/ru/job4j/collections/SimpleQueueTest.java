package ru.job4j.collections;

import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleQueueTest {

    SimpleQueue<Object> stack = new SimpleQueue<>();

    @Test
    public void whenPollFromQueueThenReturnFirstElement() {

        stack.push(7);
        stack.push("1");
        stack.push(new Integer(4));
        stack.push(4.5);
        stack.push(true);

        assertThat(stack.poll(), is(7));
        assertThat(stack.poll(), is("1"));
        assertThat(stack.poll(), is(4));
        assertThat(stack.poll(), is(4.5));
        assertThat(stack.poll(), is(true));
    }
}