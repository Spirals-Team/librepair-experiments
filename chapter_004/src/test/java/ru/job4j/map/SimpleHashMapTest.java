package ru.job4j.map;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class SimpleHashMapTest {
    @Test(expected = NoSuchElementException.class)
    public void whereTestOneItemInHashMap() {
        SimpleHashMap<String, String> map = new SimpleHashMap<>();
        assertThat(map.insert("One", "Value"), is(true));
        assertThat(map.getSize(), is(1));
        assertThat(map.get("One"), is("Value"));
        Iterator iter = map.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is("Value"));
        assertThat(iter.hasNext(), is(false));
        iter.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whereTestIteratorProtectedAdd() {
        SimpleHashMap<String, String> map = new SimpleHashMap<>();
        assertThat(map.insert("1", "Test"), is(true));
        assertThat(map.getSize(), is(1));
        assertThat(map.get("1"), is("Test"));
        Iterator iter = map.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is("Test"));
        assertThat(iter.hasNext(), is(false));
        assertThat(map.insert("1", "Test"), is(false));
        assertThat(map.insert("2", "Test 2"), is(true));
        iter.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whereTestIteratorProtectedDel() {
        SimpleHashMap<String, String> map = new SimpleHashMap<>();
        assertThat(map.insert("1", "Test"), is(true));
        assertThat(map.getSize(), is(1));
        assertThat(map.get("1"), is("Test"));
        assertThat(map.insert("2", "Test 2"), is(true));
        assertThat(map.get("2"), is("Test 2"));
        Iterator iter = map.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(map.delete("1"), is(true));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.hasNext(), is(true));
        iter.next();
    }
}