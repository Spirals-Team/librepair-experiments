package ru.job4j.list;

import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;
import static org.hamcrest.Matchers.is;
/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ArrayContainerTest {

    @Test(expected = ArrayIndexOutOfBoundsException.class)
    public void whenCreateNewArrayInSizeOneElementAndPutTwoItem() {
        ArrayContainer<Integer> arrayContainer = new ArrayContainer<>(1);
        arrayContainer.add(0);
        assertThat(arrayContainer.getSize(), is(1));
        assertThat(arrayContainer.get(0), is(0));
        arrayContainer.add(10);
        assertThat(arrayContainer.getSize(), is(2));
        assertThat(arrayContainer.get(1), is(10));
        arrayContainer.get(2);
    }

    @Test
    public void whenTestIterator() {
        ArrayContainer<Integer> arrayContainer = new ArrayContainer<>(2);
        arrayContainer.add(0);
        arrayContainer.add(1);
        arrayContainer.add(2);
        Iterator iter = arrayContainer.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(0));
        assertThat(iter.next(), is(1));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(2));
        assertThat(iter.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenCreatedIteratorAndReplaceItem() {
        ArrayContainer<Integer> arrayContainer = new ArrayContainer<>();
        arrayContainer.add(0);
        Iterator iter = arrayContainer.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(0));
        arrayContainer.add(1);
        iter.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenIteratorNotMoreElement() {
        ArrayContainer<Integer> arrayContainer = new ArrayContainer<>();
        arrayContainer.add(0);
        Iterator iter = arrayContainer.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(0));
        iter.next();
    }

    @Test
    public void whenTestContainsTwoElementInArray() {
        ArrayContainer<Integer> arrayContainer = new ArrayContainer<>();
        arrayContainer.add(77);
        assertThat(arrayContainer.contains(77), is(true));
        assertThat(arrayContainer.contains(0), is(false));
    }
}