package ru.aserdyuchenko.tree.task_1;

import org.junit.Test;

import java.util.Comparator;

import static org.junit.Assert.*;

/**
 * @author anton.
 */
public class TreeTest {
    private Tree<String> tree = new Tree<>();

    @Test
    public void whenAddParentAndHisChild() {
        tree.add("B", "b");
        tree.add("A", "a");
        tree.add("C", "c1");
        boolean result = tree.add("C", "c2");
        assertTrue(result);
    }
}