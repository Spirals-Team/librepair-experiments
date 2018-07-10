package ru.job4j.tree.bst;

import org.junit.Before;
import org.junit.Test;
import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;


public class BstTest {

    private Bst<Integer> tree;

    @Before
    public void setUp() {
        tree = new Bst<>(20);
    }

    @Test
    public void addValuesUnderTree() {
        assertThat(tree.size(), is(1));
        assertThat(tree.add(15), is(true));
        assertThat(tree.size(), is(2));
        assertThat(tree.search(15).getValue(), is(15));
        assertThat(tree.add(13), is(true));
        assertThat(tree.add(25), is(true));
        assertThat(tree.add(14), is(true));
        assertThat(tree.add(10), is(true));
        assertThat(tree.add(8), is(true));
        assertThat(tree.add(30), is(true));
        assertThat(tree.size(), is(8));
        assertThat(tree.search(8).getValue(), is(8));
        assertThat(tree.search(30).getValue(), is(30));
    }

    @Test
    public void nextReturnsValue() {
        tree.add(15);
        tree.add(13);
        tree.add(25);
        tree.add(14);
        tree.add(10);
        tree.add(8);
        tree.add(30);
        Iterator<Integer> it = tree.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(8));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(10));
        assertThat(it.next(), is(13));
        assertThat(it.next(), is(14));
        assertThat(it.next(), is(15));
        assertThat(it.next(), is(20));
        assertThat(it.next(), is(25));
        assertThat(it.next(), is(30));
    }

    @Test(expected = NoSuchElementException.class)
    public void nextHasNextAfterIteratorFinished() {
        tree.add(15);
        tree.add(13);
        tree.add(25);
        tree.add(14);
        tree.add(10);
        tree.add(8);
        tree.add(30);
        Iterator<Integer> it = tree.iterator();
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(8));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(10));
        assertThat(it.next(), is(13));
        assertThat(it.next(), is(14));
        assertThat(it.next(), is(15));
        assertThat(it.next(), is(20));
        assertThat(it.next(), is(25));
        assertThat(it.next(), is(30));
        assertThat(it.hasNext(), is(false));
        it.next();
    }
}
