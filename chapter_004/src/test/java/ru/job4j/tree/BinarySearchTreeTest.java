package ru.job4j.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.core.Is.is;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class BinarySearchTreeTest {
    private Iterator it;
    private BinarySearchTree<Integer> tree;

    @Before
    public void setUp() {
        tree = new BinarySearchTree<>();
        tree.insert(7);
        tree.insert(3);
        tree.insert(9);
        tree.insert(2);
        tree.insert(4);
        tree.insert(8);
        tree.insert(10);
        tree.insert(2);
        it = tree.iterator();
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldReturnNextElementIfItExist() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(7));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(8));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(9));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(10));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(7));
        assertThat(it.next(), is(8));
        assertThat(it.next(), is(9));
        assertThat(it.next(), is(10));
    }

    @Test
    public void shouldReturnFalseCauseThereIsNoAnyElements() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        it = tree.iterator();
        assertThat("should return false, cause there is no any elements", it.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenModifiedTreeAndContinueUseIteratorThenConcurrentModificationException() {
        tree.insert(100);
        it.next();
    }
}
