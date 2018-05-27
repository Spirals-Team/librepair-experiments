package ru.job4j.generic;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleArrayTest {
    SimpleArray<Integer> list;

    @Before
    public void setUp() {
        list = new SimpleArray<Integer>();
        list.add(1);
        list.add(5);
        list.add(-3);
    }

    @Test
    public void whenAddNewItem() {
        list.add(7);
        assertThat(list.get(list.size() - 1), is(7));
    }

    @Test
    public void whenSetItem() {
        list.set(0, -5);
        assertThat(list.get(0), is(-5));
    }

    @Test
    public void whenDeleteItem() {
        list.delete(0);
        assertThat(list.get(0), is(5));
    }

    @Test
    public void whenGetItem() {
        int test = list.get(2);
        assertThat(test, is(-3));
    }

    @Test
    public void iterator() {
        Iterator it = list.iterator();
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(-3));
        assertThat(it.hasNext(), is(false));
    }
}