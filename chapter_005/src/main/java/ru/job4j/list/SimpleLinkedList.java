package ru.job4j.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleLinkedList<E> implements Iterable<E> {

    private Node<E> first;
    private Node<E> last;
    private int size = 0;

    public void add(E value) {
        Node<E> tmp = last;

        Node<E> newNode = new Node<>(value, tmp, null);
        last = newNode;

        if (first == null) {
            first = newNode;
        } else {
            tmp.next = newNode;
        }
        size++;
    }

    public E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.item;
    }



    public E deleteLast() {
        if (last == null) {
            throw new NoSuchElementException();
        }
        Node<E> element = last;
        E item = last.item;
        last.item = null;
        if (last.prev == null) {
            last = null;
            first = null;
        } else {
            last = element.prev;
            last.next = null;
            element.prev = null;
        }
        size--;
        return item;
    }

    public E deleteFirst() {
        if (first == null) {
            throw new NoSuchElementException();
        }
        Node<E> element = first;
        E item = first.item;
        first.item = null;
        if (first.next == null) {
            first = null;
            last = null;
        } else {
            first = element.next;
            first.prev = null;
            element.next = null;
        }
        size--;
        return item;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int position = 0;

            @Override
            public boolean hasNext() {
                return position < size;
            }

            @Override
            public E next() {
                if (hasNext()) {
                    return get(position++);
                } else {
                    throw new NoSuchElementException();
                }
            }
        };
    }

    private static class Node<E> {
        private E item;
        private Node<E> prev;
        private Node<E> next;

        public Node(E item, Node<E> prev, Node<E> next) {
            this.item = item;
            this.prev = prev;
            this.next = next;
        }
    }
}
