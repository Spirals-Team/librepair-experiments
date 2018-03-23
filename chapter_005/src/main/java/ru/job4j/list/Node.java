package ru.job4j.list;

public class Node<T> {

    private T value;
    Node<T> next;

    public Node(T value) {
        this.value = value;
    }

    public static boolean hasCycle(Node node) {
        Node first = node;
        Node next = node.next;

        while (next.next != null) {
            if (next.next == first) {
                return true;
            } else {
                first = first.next;
            }

            if (next == first) {
                next = next.next;
                first = node;
            }
        }
        return false;
    }
}
