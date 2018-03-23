package ru.job4j.map;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class SimpleHashMapTest {

    // add and get element
    @Test
    public void whenInsertElementThenElementAdded() {
        SimpleHashMap<Object, Object> map = new SimpleHashMap<>(10);
        map.insert(25, "dog");
        assertThat(map.get(25), is("dog"));
    }
    // delete element
    @Test
    public void whenDeleteElementThenElementDeleted() {
        SimpleHashMap<Object, Object> map = new SimpleHashMap<>(10);
        map.insert(25, "dog");
        map.delete(25);
        assertThat(map.get(25), is(nullValue()));
    }

    @Test(expected = NoSuchElementException.class)
    public void hasNextNextSequentialInvocation() {
        SimpleHashMap<Object, Object> map = new SimpleHashMap<>(5);
        map.insert(25, "dog");
        map.insert(13, "cat");
        map.insert(34, "fox");
        map.insert(null, "null");
        map.insert(0, "cow");

        Iterator<SimpleHashMap.Node<Object, Object>> it = map.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.next().getValue(), is("cow"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next().getValue(), is("null"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next().getValue(), is("cat"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next().getValue(), is("fox"));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next().getValue(), is("dog"));
        assertThat(it.hasNext(), is(false));
        it.next();
    }
}