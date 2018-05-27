package ru.job4j.list;

import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * LIFO
 */
public class SimpleStack<T> {
    private Node node;
    private int size;

    public SimpleStack() {
        this.node = null;
        this.size = 0;
    }

    public void push(T value) {
        Node<T> node = new Node<>(value);
        if (this.node == null) {
            this.node = node;
        } else {
            node.next = this.node;
            this.node = node;
        }
        size++;
    }

    public T pull() {
        if (this.node == null) {
            throw new NoSuchElementException("stack is empty");
        }
        Node<T> getNode = node;
        this.node = this.node.next;
        size--;
        return getNode.item;
    }

    public int getSize() {
        return size;
    }

    private static class Node<E> {
        E item;
        Node<E> next;

        Node(E element) {
            this.item = element;
            this.next = null;
        }
    }
}

