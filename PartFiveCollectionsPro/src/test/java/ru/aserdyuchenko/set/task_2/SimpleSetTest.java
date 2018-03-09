package ru.aserdyuchenko.set.task_2;

import org.junit.Test;
import java.util.Iterator;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author anton.
 */
public class SimpleSetTest {
    /**
     * @param set - LinkedSet.
     */
    private SimpleSet<String> set = new SimpleSet<>();
    /**
     * @param iterator - set iterator.
     */
    private Iterator iterator = set.iterator();
    /**
     * Check my set.
     * Add two origin elements and check with iterator.
     */
    @Test
    public void whenAddTwoOriginElementsAndCheckWithIterator() {
        set.add("A");
        set.add("B");
        iterator.next();

        Object result = iterator.next();
        assertThat(result, is("B"));
    }
    /**
     * Add two origin elements and get first element.
     */
    @Test
    public void whenAddTwoOriginElementsAndGetFirstElement() {
        set.add("A");
        set.add("B");

        Object result = set.get(0);
        assertThat(result, is("A"));
    }

    /**
     * Check my set.
     * Add two origin elements and check with iterator.
     */
    @Test
    public void whenAddTwoEqualsElementsAndCheckWithIteratorMethodNext() {
        set.add("A");
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
        set.add("A");
        set.add("A");
        iterator.next();

        boolean result = iterator.hasNext();
        assertThat(result, is(false));
    }

}