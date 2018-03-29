package ru.job4j.tree;

import org.junit.Test;

import java.util.Iterator;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class BinaryTreeTest {
    @Test
    public void iteratorTest() {
        BinaryTree<Integer> tree = new BinaryTree<>();
        tree.add(6);
        tree.add(4);
        tree.add(8);
        tree.add(3);
        tree.add(5);
        tree.add(7);
        tree.add(9);
        tree.display(tree.getRoot());
        Iterator<Integer> it = tree.iterator();
        assertThat(it.next(), is(9));
        assertThat(it.next(), is(7));
        assertThat(it.next(), is(8));
        assertThat(it.next(), is(6));
        assertThat(it.next(), is(4));
        assertThat(it.next(), is(3));
        assertThat(it.next(), is(5));
    }
}