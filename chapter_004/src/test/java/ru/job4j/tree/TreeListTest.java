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
public class TreeListTest {
    @Test
    public void when6ElFindLastThen6() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(6).isPresent(),
                is(true)
        );
        assertThat(tree.isBinary(), is(false));
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
        assertThat(tree.isBinary(), is(true));
    }

    @Test
    public void whenManyItemsInTreeAndNotBinary() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(3, 7);
        tree.add(4, 5);
        tree.add(5, 6);
        tree.add(6, 8);
        tree.add(6, 9);
        tree.add(6, 10);
        assertThat(tree.isBinary(), is(false));
    }

    @Test
    public void whenManyItemsInTreeAndBinary() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(2, 4);
        tree.add(3, 7);
        tree.add(4, 5);
        tree.add(5, 6);
        tree.add(6, 8);
        tree.add(6, 9);
        assertThat(tree.isBinary(), is(true));
    }

    @Test(expected = NoSuchElementException.class)
    public void whenTestIteraror() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        Iterator iter = tree.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(1));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(2));
        assertThat(iter.hasNext(), is(false));
        iter.next();
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenTestIterarorAndAddItem() {
        TreeList<Integer> tree = new TreeList<>(1);
        tree.add(1, 2);
        Iterator iter = tree.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(1));
        tree.add(2, 3);
        iter.next();
    }

    @Test
    public void whenTestIterarorOnlyTree() {
        TreeList<Integer> tree = new TreeList<>(1);
        Iterator iter = tree.iterator();
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.hasNext(), is(true));
        assertThat(iter.next(), is(1));
        assertThat(iter.hasNext(), is(false));
    }
}