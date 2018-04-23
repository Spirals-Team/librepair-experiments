package ru.aserdyuchenko.tree.task_3;

import org.junit.Test;

import java.util.Comparator;
import java.util.Iterator;

import static org.junit.Assert.*;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class TreeTest {
    private Tree<String> tree = new Tree<>();

    @Test
    public void whenAddParents() {
        tree.add("A");
        tree.add("C");
        boolean result = tree.add("B");
        assertTrue(result);
    }

    @Test
    public void whenAddParentsAndChildren() {
        tree.add("A", "a");
        tree.add("C", "c");
        boolean result = tree.add("B", "b");
        assertTrue(result);
    }
}