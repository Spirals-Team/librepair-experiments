package ru.aserdyuchenko.tree.task_4;

import org.junit.Test;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class TreeTest {
    private Tree<String> tree = new Tree<>();

    @Test
    public void whenDontChangeElementsInTree() {
        tree.add("D");
        tree.add("B");
        tree.add("F");
        tree.add("A");
        tree.add("C");
        tree.add("E");
        tree.add("G");

        Iterator iterator = tree.iterator();
        ArrayList list = new ArrayList();
        while (iterator.hasNext()) list.add(iterator.next());

        assertThat(list, is(Arrays.asList("A", "B", "C", "D", "E", "F", "G")));
    }

    @Test
    public void whenDepthFirstSearch() {
        tree.add("D");
        tree.add("B");
        tree.add("F");
        tree.add("A");
        tree.add("C");
        tree.add("E");
        tree.add("G");

        tree.depthFirstSearch();

        Iterator iterator = tree.iterator();
        ArrayList list = new ArrayList();
        while (iterator.hasNext()) list.add(iterator.next());

        assertThat(list, is(Arrays.asList("G", "F", "E", "D", "C", "B", "A")));
    }

    @Test
    public void whenBreadthFirstSearch() {
        tree.add("D");
        tree.add("B");
        tree.add("F");
        tree.add("A");
        tree.add("C");
        tree.add("E");
        tree.add("G");

        tree.breadthFirstSearch();

        Iterator iterator = tree.iterator();
        ArrayList list = new ArrayList();
        while (iterator.hasNext()) list.add(iterator.next());

        assertThat(list, is(Arrays.asList("G", "F", "E", "D", "C", "B", "A")));
    }
}