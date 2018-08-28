package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class SimpleLinkedSetTest {

    private SimpleLinkedSet<String> ssl;

    @Before
    public void setUp() {
        ssl = new SimpleLinkedSet<>();
    }

    @Test
    public void testAddLinkedList() throws NullPointerException {
        ssl.add("Test1");
        ssl.add("Test2");
        ssl.add("Test2");
        ssl.add("Test3");
        Iterator<String> iterator = ssl.iterator();
        assertThat(iterator.next(), is("Test1"));
        assertThat(iterator.next(), is("Test2"));
        assertThat(iterator.next(), is("Test3"));
        assertThat(iterator.hasNext(), is(false));
    }
}