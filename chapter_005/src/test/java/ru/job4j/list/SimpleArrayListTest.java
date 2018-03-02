package ru.job4j.list;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayListTest {

    //add & get element
    @Test
    public void whenAddElementThenElementAdded() {
        SimpleArrayList<Integer> list = new SimpleArrayList<>(1);
        list.add(1);
        assertThat(list.get(0), is(1));
    }

    @Test(expected = NoSuchElementException.class)
    public void hasNextNextSequentialInvocation() {
        SimpleArrayList<Integer> list = new SimpleArrayList<>();
        list.add(1);
        Iterator<Integer> iter = list.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(1));
        assertThat(iter.hasNext(), is(false));
        iter.next();
    }
}