package ru.job4j.tree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class TreeTest.
 */
public class TreeTest {
    /**
     * Test №1.
     */
    @Test
    public void whenParentDoesNotExistThenResultAddFalse() {
        Tree<Integer> tree = new Tree<>(0);

        tree.add(0, 1);
        tree.add(0, 2);

        boolean result = tree.add(3, 2);

        assertThat(result, is(false));
    }

    /**
     * Test 2.
     */
    @Test
    public void whenAddDuplicateThenSizeDoesNotIncrease() {
        Tree<Integer> tree = new Tree<>(0);

        tree.add(0, 1);
        tree.add(0, 2);
        tree.add(1, 11);
        tree.add(1, 12);
        tree.add(0, 11);
        tree.add(1, 2);

        int result = tree.getSize();

        assertThat(result, is(4));
    }

    /**
     * Test 3.
     */
    @Test
    public void whenParentHasNotMostTwoChildrenThenResultTrue() {
        Tree<Integer> tree = new Tree<>(0);

        tree.add(0, 1);
        tree.add(0, 2);
        tree.add(1, 11);
        tree.add(1, 12);
        tree.add(2, 21);
        tree.add(2, 22);

        boolean result = tree.isBinary();

        assertThat(result, is(true));
    }

    /**
     * Test 4.
     */
    @Test
    public void whenParentHasMostTwoChildrenThenResultFalse() {
        Tree<Integer> tree = new Tree<>(0);

        tree.add(0, 1);
        tree.add(0, 2);
        tree.add(1, 11);
        tree.add(1, 12);
        tree.add(2, 21);
        tree.add(2, 22);
        tree.add(22, 220);
        tree.add(22, 221);
        tree.add(22, 222);

        boolean result = tree.isBinary();

        assertThat(result, is(false));
    }

}