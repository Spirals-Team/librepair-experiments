package ru.job4j.set;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleArraySetTest {
    @Test
    public void testAdd() {
        SimpleArraySet<String> ss = new SimpleArraySet<>();
        Iterator<String> iterator = ss.iterator();
        ss.add("Test1");
        ss.add("Test2");
        ss.add("Test3");
        ss.add("Test3");
        assertThat(iterator.next(), is("Test1"));
        assertThat(iterator.next(), is("Test2"));
        assertThat(iterator.next(), is("Test3"));
        assertThat(iterator.hasNext(), is(false));
    }
}