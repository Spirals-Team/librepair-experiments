package ru.job4j.list;

public class Cycle {

    static class Node<T> {
        T value;
        Node<T> next;

        Node(T value) {
            this.value = value;
        }

    }

    public static boolean hasCycle(Node first) {
        Node turtle = first;
        Node hare = first;

        while (hare != null && hare.next != null) {
            turtle = turtle.next;
            hare = hare.next.next;
            if (turtle.equals(hare)) {
                return true;
            }
        }
        return false;
    }
}



