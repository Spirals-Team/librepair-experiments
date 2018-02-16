package ru.job4j.tree;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class BinarySearchTreeTest {

    @Test(expected = NoSuchElementException.class)
    public void hasNextNextSequentialInvocation() {
        BinarySearchTree<Integer> bst = new BinarySearchTree<>();
        bst.add(3);
        bst.add(1);
        bst.add(4);
        bst.add(2);
        bst.add(5);
        Iterator<Integer> it = bst.iterator();

        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(3));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(1));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(4));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(2));
        assertThat(it.hasNext(), is(true));
        assertThat(it.next(), is(5));
        it.next();
    }
}