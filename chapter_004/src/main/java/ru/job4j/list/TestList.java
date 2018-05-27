package ru.job4j.list;

import java.util.ArrayList;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TestList {
    boolean hasCycle(Node node) {
        Node turtle = node;
        Node rabbit = node.next;
        do {
            if (turtle.equals(rabbit)) {
                return true;
            }
            turtle = turtle.next;
            if (rabbit != null && rabbit.next != null) {
                rabbit = rabbit.next.next;
            } else {
                return false;
            }
        } while (turtle != null);
        return false;
    }

    static class Node<T> {
        T value;
        Node<T> next;

        public Node(T value) {
            this.value = value;
            this.next = null;
        }
    }
}
