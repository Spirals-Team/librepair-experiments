package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

@ThreadSafe
public class SimpleLinkedList<E> implements Iterable<E> {
    @GuardedBy("this")
    private Node<E> first;
    @GuardedBy("this")
    private Node<E> last;
    @GuardedBy("this")
    private volatile int size = 0;
    @GuardedBy("this")
    private volatile int modCount = 0;

    public synchronized void add(E value) {
        modCount++;
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

    public synchronized E get(int index) {
        if (index >= size) {
            throw new IndexOutOfBoundsException();
        }
        Node<E> node = first;
        for (int i = 0; i < index; i++) {
            node = node.next;
        }
        return node.item;
    }

    public synchronized E deleteLast() {
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

    public synchronized E deleteFirst() {
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
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            private int position = 0;
            int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                return position < size;
            }
            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return get(position++);
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
