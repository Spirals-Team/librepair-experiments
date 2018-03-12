package ru.job4j.map;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;
import static org.junit.Assert.*;

public class SimpleHashMapTest {
    private SimpleHashMap<String, Integer> shm;

    @Before
    public void setUp() {
        shm = new SimpleHashMap<>();
    }

    @Test
    public void whenAddStringToHashMap() {
        assertThat(shm.insert("one", 1), is(true));
        assertThat(shm.insert("two", 2), is(true));
        assertThat(shm.insert("tree", 3), is(true));
        assertThat(shm.insert("four", 4), is(true));
        assertThat(shm.insert("four", 4), is(false));
    }

    @Test
    public void whenGetStringToHashMap() {
        shm.insert("one", 1);
        shm.insert("two", 2);
        shm.insert("tree", 3);
        shm.insert("four", 4);
        assertThat(shm.get("one"), is(1));
        assertThat(shm.get("two"), is(2));
        assertThat(shm.get("tree"), is(3));
        assertThat(shm.get("four"), is(4));
        assertThat(shm.get("five"), is(nullValue()));
    }

    @Test
    public void whenDeleteStringToHashMap() {
        shm.insert("one", 1);
        shm.insert("two", 2);
        shm.insert("tree", 3);
        shm.insert("four", 4);
        assertThat(shm.delete("one"), is(true));
        assertThat(shm.get("one"), is(nullValue()));
    }

    @Test
    public void testAndIterator() {
        shm.insert("one", 1);
        shm.insert("two", 2);
        shm.insert("tree", 3);
        shm.insert("four", 4);
        Iterator<String> it = shm.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(shm.get(it.next()), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(shm.get(it.next()), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(shm.get(it.next()), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(shm.get(it.next()), is(4));
        assertThat(it.hasNext(), is(false));
    }
}