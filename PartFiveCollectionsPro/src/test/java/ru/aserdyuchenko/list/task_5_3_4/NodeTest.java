package ru.aserdyuchenko.list.task_5_3_4;

import org.junit.Test;
import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

/**
 * @author anton.
 */
public class NodeTest {

    /**
     * Test node cycle.
     */
    @Test
    public void whenNodeHasCycle() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> second = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> fourth = new Node<>(4);

        first.setNext(second);
        second.setNext(third);
        third.setNext(fourth);
        fourth.setNext(first);

        boolean result = first.hasCycle(first);
        assertThat(result, is(true));
    }

    /**
     * Test node cycle.
     */
    @Test
    public void whenNodeHasSmallCycle() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> second = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> fourth = new Node<>(4);

        first.setNext(second);
        second.setNext(third);
        third.setNext(fourth);
        fourth.setNext(second);

        boolean result = first.hasCycle(first);
        assertThat(result, is(true));
    }

    /**
     * Test node does not cycle.
     */
    @Test
    public void whenNodeHasNotCycle() {
        Node<Integer> first = new Node<>(1);
        Node<Integer> second = new Node<>(2);
        Node<Integer> third = new Node<>(3);
        Node<Integer> fourth = new Node<>(4);

        first.setNext(second);
        second.setNext(third);
        third.setNext(fourth);

        boolean result = first.hasCycle(first);
        assertThat(result, is(false));
    }
}