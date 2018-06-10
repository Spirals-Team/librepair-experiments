package ru.job4j.collections;

import org.junit.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BinarySearchTreeTest {

    @Test(expected = NoSuchElementException.class)
    public void whenAdd5ElementThemByCall6ElementThrowException() {
        BinarySearchTree<Integer> tree = new BinarySearchTree<>();
        Iterator<Integer> arrays = tree.iterator();
        tree.add(1);
        tree.add(11);
        tree.add(7);
        tree.add(9);
        tree.add(15);
        assertThat(arrays.hasNext(), is(true));
        assertThat(arrays.next(), is(1));

        assertThat(arrays.hasNext(), is(true));
        assertThat(arrays.next(), is(7));

        assertThat(arrays.hasNext(), is(true));
        assertThat(arrays.next(), is(9));

        assertThat(arrays.hasNext(), is(true));
        assertThat(arrays.next(), is(11));

        assertThat(arrays.hasNext(), is(true));
        assertThat(arrays.next(), is(15));

        assertThat(arrays.hasNext(), is(false));
        arrays.next();
    }
}