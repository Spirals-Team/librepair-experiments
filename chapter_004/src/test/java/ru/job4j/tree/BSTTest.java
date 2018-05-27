package ru.job4j.tree;

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
public class BSTTest {
    BST<Integer> tree = new BST<>();
    @Test(expected = NoSuchElementException.class)
    public void whenAdd5ItemsInTree() {
        tree.add(1);
        tree.add(5);
        tree.add(10);
        tree.add(2);
        tree.add(77);
        Iterator iter = tree.iterator();
        assertThat(iter.hasNext(), is(true));
        System.out.println(iter.next());
        System.out.println(iter.next());
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.hasNext(), is(true));
        System.out.println(iter.next());
        System.out.println(iter.next());
        System.out.println(iter.next());
        assertThat(iter.hasNext(), is(false));
        iter.next();
    }
    @Test(expected = ConcurrentModificationException.class)
    public void whenAddItemsAfterAddIter() {
        tree.add(1);
        Iterator iter = tree.iterator();
        assertThat(iter.hasNext(), is(true));
        System.out.println(iter.next());
        assertThat(iter.hasNext(), is(false));
        tree.add(2);
        iter.next();
    }
}