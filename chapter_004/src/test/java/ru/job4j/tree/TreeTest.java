package ru.job4j.tree;

import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class TreeTest {
    private Tree<Integer> tree;
    private Iterator<Integer> it;

    @Before
    public void setUp() {
        tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        tree.add(5, 7);
        tree.add(3, 8);
        tree.add(3, 9);
        it = tree.iterator();
    }

    @Test
    public void when6ElFindLastThen6() {
        assertThat(tree.findBy(6).isPresent(), is(true));
    }

    @Test
    public void when77ElFindNotExitThenOptionEmpty() {
        assertThat(tree.findBy(77).isPresent(), is(false));
    }

    @Test(expected = NoSuchElementException.class)
    public void shouldReturnNextElementIfItExist() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(8));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(9));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(6));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(7));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(false));
        it.next();
    }

    @Test
    public void sequentialHasNextInvocationDoesntAffectRetrievalOrder() {
        assertThat(it.hasNext(), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.next(), is(8));
        assertThat(it.next(), is(9));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(6));
        assertThat(it.next(), is(7));
        assertThat(it.next(), is(5));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(1));
    }

    @Test
    public void shouldReturnRootIfTreeConsistOnlyOneElement() {
        Tree<Integer> tree = new Tree<>(1);
        it = tree.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(false));
    }

    @Test(expected = ConcurrentModificationException.class)
    public void whenModifiedTreeAndContinueUseIteratorThenConcurrentModificationException() {
        tree.add(1, 100);
        it.next();
    }

    @Test
    public void whenTreeIsNotBinatyThenFalse() {
        assertThat(tree.isBinary(), is(false));
    }

    @Test
    public void whenTreeIsBinatyThenTrue() {
        Tree<String> tree = new Tree<>("one");
        tree.add("one", "two");
        tree.add("one", "three");
        tree.add("two", "four");
        tree.add("two", "five");
        tree.add("three", "six");
        assertThat(tree.isBinary(), is(true));
    }
}
