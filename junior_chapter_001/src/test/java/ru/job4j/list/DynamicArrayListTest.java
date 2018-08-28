package ru.job4j.list;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class DynamicArrayListTest {
    private SimpleContainer<Integer> sc;

    @Before
    public void setUp() {
        sc = new DynamicArrayList<>();
    }

    @Test
    public void addAndGetObjects() throws Exception {
        for (int i = 0; i <= 20; i++) {
            sc.add(i);
        }
        assertThat(sc.get(0), is(0));
        assertThat(sc.get(10), is(10));
        assertThat(sc.get(20), is(20));
    }

    @Test
    public void addTreeObjectsAndGetThroughIterator() throws Exception {
        for (int i = 0; i < 3; i++) {
            sc.add(i);
        }
        Iterator<Integer> it = sc.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(0));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(false));
    }

}