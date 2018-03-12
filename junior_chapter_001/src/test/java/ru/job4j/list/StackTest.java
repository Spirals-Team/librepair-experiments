package ru.job4j.list;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class StackTest {
    @Test
    public void testQueuePushAndPoll() {
        Stack stack = new Stack();
        stack.push("Test1");
        stack.push("Test2");
        stack.push("Test3");
        assertThat(stack.poll(), is("Test3"));
        stack.push("Test4");
        assertThat(stack.poll(), is("Test4"));
        assertThat(stack.poll(), is("Test2"));
        assertThat(stack.poll(), is("Test1"));
    }
}