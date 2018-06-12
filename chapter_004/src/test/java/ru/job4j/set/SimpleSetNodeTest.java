package ru.job4j.set;

import org.junit.Before;
import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SimpleSetNodeTest {
    SimpleSetNode<String> set;
    Iterator<String> it;

    @Before
    public void setUp() {
        set = new SimpleSetNode<>();
        set.add("first");
        set.add("first");
        set.add("first");
        set.add("second");
        set.add("second");
        set.add("third");
        it = set.iterator();
    }

    @Test
    public void whenAddElementThenIsAddedOnlyUniqueElement() {
        assertThat(it.next(), is("first"));
        assertThat(it.next(), is("second"));
        assertThat(it.next(), is("third"));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenIteratorDoesNotHaveAnyMoreElementThenNoSuchElementException() {
        it.next();
        it.next();
        it.next();
        it.next();
    }
}