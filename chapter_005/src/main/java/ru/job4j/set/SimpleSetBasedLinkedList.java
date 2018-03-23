package ru.job4j.set;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleSetBasedLinkedList<T> implements Iterable<T> {

    private Node<T> first;
    private Node<T> last;
    private int size = 0;

    //if duplicate return true else false
    private boolean isDuplicate(T value) {
        Node<T> node = first;
        if (node != null) {
            for (int i = 0; i < size; i++) {
                Node<T> tmp = node;
                if (node.item.equals(value)) {
                    return true;
                }
                node = tmp.next;
            }
        }
        return false;
    }

    //Add unique element
    public boolean add(T value) {

        //Checking duplicate
        if (isDuplicate(value)) {
            return false;
        }

        // Add element
        Node<T> tmp = last;
        Node<T> newNode = new Node<>(value, null);
        last = newNode;

        if (first == null) {
            first = newNode;
        } else {
            tmp.next = newNode;
        }
        size++;
        return true;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {

            private Node<T> node = first;
            private int count = 0;

            @Override
            public boolean hasNext() {
                return count < size;
            }

            @Override
            public T next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                Node<T> tmp = node;
                node = tmp.next;
                count++;
                return tmp.item;
            }
        };
    }

    private static class Node<T> {

        private T item;
        private Node<T> next;

        public Node(T item, Node<T> next) {
            this.item = item;
            this.next = next;
        }
    }
}
