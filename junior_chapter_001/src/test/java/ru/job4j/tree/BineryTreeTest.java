package ru.job4j.tree;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.*;

public class BineryTreeTest {
    @Test
    public void whenAddTest() {
        BineryTree<Integer> bineryTree = new BineryTree<>(1);
        assertThat(bineryTree.add(2), is(true));
        assertThat(bineryTree.add(2), is(false));
    }

    @Test
    public void whenIteratorTest() {
        BineryTree<Integer> bineryTree = new BineryTree<>(1);
        Iterator<Integer> it = bineryTree.iterator();
        assertThat(bineryTree.add(2), is(true));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(false));
    }
}