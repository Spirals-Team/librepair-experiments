package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class ListContainer<T> implements SimpleContainer<T> {
    private Node head;
    private Node first;
    private int size;
    private int modCount;

    public ListContainer() {
        this.head = null;
        this.size = 0;
        modCount = 0;

    }

    @Override
    public void add(T t) {
        Node node = new Node(t);
        if (this.head == null) {
            this.head = node;
            this.first = this.head;
        } else {
            node.feet = this.head;
            this.head.head = node;
            head = node;
        }
        size++;
        modCount++;
    }

    @Override
    public T get(int index) {
        int indexSearch = 0;
        Node node = first;
        while (indexSearch != index) {
            node = node.head;
            indexSearch++;
        }
        return (T) node.item;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Node linkNext = first;
            private int expectedModCount = modCount;
            @Override
            public boolean hasNext() {
                return linkNext != null;
            }

            @Override
            public T next() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException("Iterator only for read.");
                } else if (linkNext == null) {
                    throw new NoSuchElementException("Not more elements.");
                }
                Object element = linkNext.item;
                linkNext = linkNext.head;
                return (T) element;
            }
        };
    }

    public int getSize() {
        return size;
    }

    public void remove(int index) {
        int indexSearch = 0;
        size--;
        modCount++;
        if (index == 0) {
            first = first.head;
            return;
        }
        Node node = first;
        while (indexSearch != index) {
            node = node.head;
            indexSearch++;
        }
        node.feet.head = node.head;
    }

    public void see() {
        Node node = first;
        while (node != null) {
            System.out.print(node.item + " ");
            node = node.head;
        }
        System.out.println();
    }

    private static class Node<E> {
        E item;
        Node<E> head;
        Node<E> feet;

        Node(E element) {
            this.item = element;
            this.head = null;
            this.feet = null;
        }
    }
}
