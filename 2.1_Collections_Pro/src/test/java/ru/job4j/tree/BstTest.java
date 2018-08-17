package ru.job4j.tree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * Class BstTest.
 */
public class BstTest {
    /**
     * Test №1.
     */
    @Test
    public void whenAddElementThenSizeIncrease() {
        Bst<Integer> bst = new Bst<>(10);

        bst.add(4);
        bst.add(7);
        bst.add(6);
        bst.add(15);
        int result = bst.getSize();

        assertThat(result, is(5));

    }

    /**
     * Test №2.
     */
    @Test
    public void whenAddDuplicateThenSizeDoesNotIncrease() {
        Bst<Integer> bst = new Bst<>(10);

        bst.add(4);
        bst.add(7);
        bst.add(6);
        bst.add(4);
        bst.add(15);
        bst.add(10);
        bst.add(16);
        int result = bst.getSize();

        assertThat(result, is(6));
    }
}