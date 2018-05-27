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
public class ListContainerTest {
    @Test(expected = NullPointerException.class)
    public void whenCreateNewList() {
        ListContainer<Integer> listContainer = new ListContainer<>();
        listContainer.add(0);
        assertThat(listContainer.getSize(), is(1));
        assertThat(listContainer.get(0), is(0));
        listContainer.add(10);
        assertThat(listContainer.getSize(), is(2));
        assertThat(listContainer.get(1), is(10));
        listContainer.get(2);
    }

    @Test
    public void whenTestIterator() {
        ListContainer<Integer> listContainer = new ListContainer<>();
        listContainer.add(0);
        listContainer.add(1);
        listContainer.add(2);
        Iterator iter = listContainer.iterator();
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
        ListContainer<Integer> listContainer = new ListContainer<>();
        listContainer.add(0);
        Iterator iter = listContainer.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(0));
        listContainer.add(1);
        iter.next();
    }

    @Test(expected = NoSuchElementException.class)
    public void whenIteratorNotMoreElement() {
        ListContainer<Integer> listContainer = new ListContainer<>();
        listContainer.add(0);
        Iterator iter = listContainer.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(0));
        iter.next();
    }
}