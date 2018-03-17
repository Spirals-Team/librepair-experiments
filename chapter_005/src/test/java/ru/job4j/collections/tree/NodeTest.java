package ru.job4j.collections.tree;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 *Тест.
 * @author Hincu Andrei (andreih1981@gmail.com) by 29.10.17;
 * @version $Id$
 * @since 0.1
 */
public class NodeTest {
    /**
     * Метод проверяет разворот элементов дерева.
     */
    @Test
    public void whenTreeHasTwoNode() {
        Node tree = new Node(5);
        Node l = new Node(3);
        Node r = new Node(7);
        tree.setLeft(l);
        tree.setRight(r);
        tree.revers();
        assertThat(tree.getLeft(), is(r));
    }

    /**
     * Метод проверяет замену когда один из сыновей равен null.
     */
    @Test
    public void whenTreeHasOneNode() {
        Node tree = new Node(5);
        Node l = new Node(3);
        tree.setLeft(l);
        tree.revers();
        assertThat(tree.getRight(), is(l));
        assertThat(null, is(tree.getLeft()));
    }
}