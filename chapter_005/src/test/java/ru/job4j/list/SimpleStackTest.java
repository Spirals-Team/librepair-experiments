package ru.job4j.list;

import org.junit.Test;

import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleStackTest {

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenPopThenLastElementDeleted() {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        stack.push(2);
        assertThat(stack.pop(), is(2));
        stack.get(1);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenPopInEmptyStackThenThrowNoSuchElementException() {
        SimpleStack<Integer> stack = new SimpleStack<>();
        stack.push(1);
        assertThat(stack.pop(), is(1));
        stack.pop();
    }
}