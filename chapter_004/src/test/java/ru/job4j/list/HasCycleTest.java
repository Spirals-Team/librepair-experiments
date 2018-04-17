package ru.job4j.list;

import org.junit.Test;

import java.util.*;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class HasCycleTest {

    class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
        }
    }

    boolean hasCycle(Node first) {
        Node current = first.next;
        while (current.next != null) {
            Node cycle = first;
            while (current != cycle) {
                if (current.next == cycle) {
                    return true;
                }
                cycle = cycle.next;
            }
            current = current.next;
        }
        return false;
    }

    @Test
    public void whenThereIsCycleThenMethodReturnsTrue() {
        Node first = new Node(1);
        Node two = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = first;
        assertThat(this.hasCycle(first), is(true));
    }

    @Test
    public void whenThereIsCycleInTheMiddleOfListThenMethodReturnsTrue() {
        Node first = new Node(1);
        Node two = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);
        first.next = two;
        two.next = third;
        third.next = four;
        four.next = third;
        assertThat(this.hasCycle(first), is(true));
    }

    @Test
    public void whenThereIsNotCycleThenMethodReturnsFalse() {
        Node first = new Node(1);
        Node two = new Node(2);
        Node third = new Node(3);
        Node four = new Node(4);
        first.next = two;
        two.next = third;
        third.next = four;
        assertThat(this.hasCycle(first), is(false));
    }
}
