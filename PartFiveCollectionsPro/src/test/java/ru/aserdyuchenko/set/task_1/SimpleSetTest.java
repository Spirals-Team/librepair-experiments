package ru.aserdyuchenko.set.task_1;


import org.junit.Test;
import java.util.Iterator;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author anton.
 */
public class SimpleSetTest {
    /**
     * Check my set.
     * Add two origin elements and check with iterator.
     */
    @Test
    public void whenAddTwoOriginElementsAndCheckWithIterator() {
        SimpleSet<String> set = new SimpleSet<>();
        Iterator iterator = set.iterator();
        set.add("A");
        set.add("B");
        iterator.next();

        Object result = iterator.next();
        assertThat(result, is("B"));
    }

    /**
     * Check my set.
     * Add two origin elements and check with iterator.
     */
    @Test
    public void whenAddTwoEqualsElementsAndCheckWithIteratorMethodNext() {
        SimpleSet<String> set = new SimpleSet<>();
        Iterator iterator = set.iterator();
        set.add("A");
        set.add("B");
        iterator.next();

        Object result = iterator.next();
        assertThat(result, is("B"));
    }

    /**
     * Check my set.
     * Add two equals elements and check with iterator.
     */
    @Test
    public void whenAddTwoEqualsElementsAndCheckWithIteratorMethodHasNext() {
        SimpleSet<String> set = new SimpleSet<>();
        Iterator iterator = set.iterator();
        set.add("A");
        set.add("A");
        iterator.next();

        boolean result = iterator.hasNext();
        assertThat(result, is(false));
    }

}