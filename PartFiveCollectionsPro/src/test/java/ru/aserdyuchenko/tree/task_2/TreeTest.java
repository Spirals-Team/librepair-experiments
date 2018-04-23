package ru.aserdyuchenko.tree.task_2;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class TreeTest {
    private Tree<String> tree = new Tree<>();

    @Test
    public void whenCheckIsTreeBinaryAndAssertTrue() {
        tree.add("A", "a");
        tree.add("A", "b");
        boolean result = tree.isBinary();
        assertTrue(result);
    }

    @Test
    public void whenCheckIsTreeBinaryAndAssertFalse() {
        tree.add("A", "B");
        tree.add("A", "C");
        tree.add("B", "D");
        tree.add("B", "E");
        tree.add("B", "F");
        tree.add("B", "G");
        boolean result = tree.isBinary();
        assertFalse(result);
    }
}