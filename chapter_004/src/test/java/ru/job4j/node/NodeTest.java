package ru.job4j.node;

import org.junit.Test;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class NodeTest {
    @Test
    public void whenContainsLoopReturnsTrue() {
        Node<Integer> first = new Node(1);
        Node<Integer> second = new Node(2);
        Node<Integer> third = new Node(3);
        Node<Integer> fourth = new Node(4);

        first.next = second;
        second.next = third;
        third.next = fourth;
        fourth.next = first;
        assertThat(first.hasCycle(second), is(true));
    }

    @Test
    public void whenDoesNotContainsLoopReturnsFalse() {
        Node<Integer> first = new Node(1);
        Node<Integer> second = new Node(2);
        Node<Integer> third = new Node(3);
        Node<Integer> fourth = new Node(4);

        first.next = second;
        second.next = third;
        third.next = fourth;
        assertThat(first.hasCycle(second), is(false));
    }
}