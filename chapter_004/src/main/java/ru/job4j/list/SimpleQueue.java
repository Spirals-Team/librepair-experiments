package ru.job4j.list;

import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 *
 * FIFO
 */
public class SimpleQueue<T> {
    private Node node;
    private int size;

    public SimpleQueue() {
        this.node = null;
        this.size = 0;
    }

    public void push(T value) {
        Node<T> node = new Node<>(value);
        if (this.node == null) {
            this.node = node;
        } else {
            Node<T> tempNode = this.node;
            while (tempNode.last != null) {
                tempNode = tempNode.last;
            }
            tempNode.last = node;
        }
        size++;
    }

    public T pull() {
        if (this.node == null) {
            throw new NoSuchElementException("queue is empty");
        }
        Node<T> getNode = node;
        node = node.last;
        size--;
        return getNode.item;
    }

    public int getSize() {
        return size;
    }

    private static class Node<E> {
        E item;
        Node<E> last;

        Node(E element) {
            this.item = element;
            this.last = null;
        }
    }
}
