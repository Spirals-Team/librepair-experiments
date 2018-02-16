package ru.job4j.list;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleLinkedListTest {

    @Test
    public void whenAddElementThenElementAdded() {
        SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertThat(list.get(1), is(2));
    }

    @Test(expected = NoSuchElementException.class)
    public void hasNextNextSequentialInvocation() {
        SimpleLinkedList<Integer> list = new SimpleLinkedList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        Iterator<Integer> iter = list.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(1));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(2));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(3));
        assertThat(iter.hasNext(), is(false));
        iter.next();
    }
}