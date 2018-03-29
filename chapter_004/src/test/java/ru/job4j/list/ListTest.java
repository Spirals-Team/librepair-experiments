package ru.job4j.list;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ListTest {
    @Test
    public void add() {
        List<Integer> list = new List<Integer>();
        list.add(1);
        assertThat(list.get(0), is(1));
    }

    @Test
    public void get() {
        List<Integer> list = new List<Integer>();
        list.add(1);
        list.add(2);
        list.add(3);
        assertThat(list.get(0), is(1));
        assertThat(list.get(1), is(2));
        assertThat(list.get(2), is(3));
    }

    @Test(expected = NoSuchElementException.class)
    public void iterator() {
        List<Integer> list = new List<Integer>();
        list.add(3);
        list.add(2);
        list.add(1);
        Iterator<Integer> it = list.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

}