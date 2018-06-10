package ru.job4j.collections;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

public class TreeTest {

    @Test
    public void when6ElFindLastThen6() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(
                tree.findBy(5).isPresent(),
                is(true)
        );
        assertThat(
                tree.findBy(1).isPresent(),
                is(true)
        );
        assertThat(
                tree.findBy(11).isPresent(),
                is(false)
        );
    }

    @Test
    public void when6ElFindNotExitThenOptionEmpty() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        assertThat(
                tree.findBy(7).isPresent(),
                is(false)
        );
    }

    @Test
    public void whenAddOverTwoElementInChildrenThenReturnFalse() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 3);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        assertThat(tree.isBinary(), is(false));
    }

    @Test
    public void whenAddOverTwoElementInChildrenThenReturnFalse2() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(5, 6);
        tree.add(5, 8);
        tree.add(5, 9);
        assertThat(tree.isBinary(), is(false));
    }

    @Test
    public void whenAddOTwoElementInChildrenThenReturnTrue() {
        Tree<Integer> tree = new Tree<>(1);
        tree.add(1, 2);
        tree.add(1, 4);
        tree.add(4, 5);
        tree.add(4, 7);
        tree.add(5, 6);
        tree.add(5, 8);
        assertThat(tree.isBinary(), is(true));
    }
}