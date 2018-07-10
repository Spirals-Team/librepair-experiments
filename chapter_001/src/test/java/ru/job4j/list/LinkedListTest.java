package ru.job4j.list;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

/**
 * Test.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class LinkedListTest {

    private LinkedList<Integer> ints;

    @Test
    public void givenEmptyListWhenAddItemsThenItemsAreReachable() {
        ints = new LinkedList<>();
        ints.add(5);
        assertThat(ints.size(), is(1));
        assertThat(ints.get(0), is(5));
        ints.add(10);
        assertThat(ints.size(), is(2));
        assertThat(ints.get(0), is(5));
        assertThat(ints.get(1), is(10));
    }

    @Test
    public void givenListCreatedByVarArgsConstructorWhenAddItemsThenItemsAreReachable() {
        ints = new LinkedList<>(5, 10);
        assertThat(ints.get(0), is(5));
        assertThat(ints.get(1), is(10));
        assertThat(ints.size(), is(2));
        ints.add(15);
        assertThat(ints.get(2), is(15));
        assertThat(ints.size(), is(3));

    }

    @Test(expected = NoSuchElementException.class)
    public void givenIteratorWhenGoesOverBorderThenThrowException() {
        ints = new LinkedList<>(5, 6, 3);
        Iterator it = ints.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(6));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void givenIteratorWhenModifyListThenThrowException() {
        ints = new LinkedList<>(5, 6, 3);
        Iterator it = ints.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        ints.add(10);
        it.next();
    }
}