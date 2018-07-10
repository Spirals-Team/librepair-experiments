package ru.job4j.list;

public class Node<T> {
    T value;
    Node<T> next;

    public Node(T i) {
        this.value = i;
    }

    public boolean hasCycle(Node<T> first) {
        Node<T> slow = first;
        Node<T> fast = first;

        while (true) {
            if (slow.next == null || fast.next == null) {
                return false;
            }
            slow = slow.next;
            fast = fast.next.next;
            if (slow == fast) {
                return true;
            }
        }
    }
}
