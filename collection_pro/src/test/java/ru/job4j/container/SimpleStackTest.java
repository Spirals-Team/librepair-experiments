package ru.job4j.container;

import org.junit.Test;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

public class SimpleStackTest {

    @Test
    public void whenStackList() {
        SimpleStack<Integer> queue = new SimpleStack<>();
        queue.push(1);
        queue.push(2);
        queue.push(3);
        queue.push(4);
        assertThat(queue.poll(), is(4));
        assertThat(queue.poll(), is(3));
        assertThat(queue.poll(), is(2));
        assertThat(queue.poll(), is(1));
    }

}