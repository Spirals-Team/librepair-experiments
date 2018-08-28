package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
@ThreadSafe
public class DynamicLinkedList<E> implements SimpleContainer<E> {
    @GuardedBy("this")
    private int modCount = 0;
    @GuardedBy("this")
    private Node<E> first;
    @GuardedBy("this")
    private Node<E> last;

    @Override
    public synchronized void add(E value) {
        Node<E> newElement = new Node<>(value);
        if (this.first != null) {
            this.first.setNext(newElement);
        }

        this.first = newElement;

        if (this.last == null) {
            this.last = newElement;
        }
        this.modCount++;
    }

    @Override
    public synchronized E get(int index) {
        E value = null;

        Iterator<E> iterator = this.iterator();
        for (int i = 0; i <= index; i++) {
            value = iterator.next();
        }
        return value;
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            private Node<E> element = last;
            private final int expectedModCount = modCount;

            @Override
            public boolean hasNext() {
                    if (expectedModCount != modCount) {
                        throw new ConcurrentModificationException("Ops");
                    }
                    return this.element != null;
            }

            @Override
            public E next() {
                    E value = null;
                    if (hasNext()) {
                        value = this.element.getValue();
                        this.element = this.element.getNext();
                    }
                    return value;
            }
        };
    }

    private static class Node<E> {
        private final E value;
        private Node<E> next;

        private Node(E value) {
            this.value = value;
        }

        public E getValue() {
            return value;
        }

        public Node<E> getNext() {
            return next;
        }

        public void setNext(Node<E> next) {
            this.next = next;
        }
    }
}
