package ru.job4j.iterator;


import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class SimpleArrayTest {
    SimpleArray<Object> array = new SimpleArray<>();
    Iterator<Object> arrays = array.iterator();

    @Before
    public void setUp() {
        array.add(7);
        array.add("1");
        array.add(new Integer(4));
        array.add(4.5);
        array.add(true);
        array.set(0, "new 0");
        array.delete(2);
    }

    @Test(expected = NoSuchElementException.class)
    public void whenThen() {
        assertThat(arrays.hasNext(), is(true));
        assertThat(arrays.next(), is("new 0"));
        assertThat(arrays.hasNext(), is(true));
        assertThat(arrays.next(), is("1"));
        assertThat(arrays.hasNext(), is(true));
        assertThat(arrays.next(), is(4.5));
        assertThat(arrays.hasNext(), is(true));
        assertThat(arrays.next(), is(true));
        assertThat(arrays.hasNext(), is(false));
        arrays.next();
    }
}
