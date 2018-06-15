package ru.job4j.list;

import static org.junit.Assert.assertThat;
import org.junit.Test;
import org.junit.Before;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;

public class FirstLastListTest {
    private FirstLastList<Integer> list;

    @Before
    public void setUp() {
        list = new FirstLastList<>();
        list.add(1);
        list.add(2);
        list.add(3);

    }

    @Test
    public void whenGetPositionThatContainerIndex() {
        assertThat(list.getSize(), is(3));
        assertThat(list.get(0), is(1));
        assertThat(list.get(1), is(2));
        assertThat(list.get(2), is(3));
    }

    @Test public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        assertThat(list.iterator().hasNext(), is(true));
        assertThat(list.iterator().hasNext(), is(true));
        assertThat(list.iterator().next(), is(1));
        assertThat(list.iterator().next(), is(2));
        assertThat(list.iterator().next(), is(3));
        list.add(4);
        assertThat(list.iterator().next(), is(4));
        assertThat(list.iterator().hasNext(), is(false));
    }

    @Test
    public void whenDeleteFirst() {
        list.deleteFirst();
        assertThat(list.get(0), is(2));

    }

    @Test
    public void whenDeleteLast() {
        list.deleteLast();
        assertThat(list.get(1), is(2));
    }

    @Test
    public void whenDInsertFist() {
        list.insetFirst(1);
        list.insetFirst(2);
        list.insetFirst(3);
        list.insetFirst(4);
        assertThat(list.get(0), is(4));
        assertThat(list.get(1), is(3));
        assertThat(list.get(2), is(2));
        assertThat(list.get(3), is(1));
    }
}