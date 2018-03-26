package ru.job4j.set;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedSet<T> implements Iterable<T> {
    private Node head;
    private int size;

    private class Node {
        protected T data;
        private Node next;

        public Node(T data) {
            this.data = data;
        }
    }

    private boolean contains(T element) {
        boolean result = false;
        Iterator<T> it = this.iterator();
        while (it.hasNext()) {
            if (it.next().equals(element)) {
                result = true;
                break;
            }
        }
        return result;
    }

    public void add(T element) {
        if (this.contains(element))
            return;

        Node temp = head;
        head = new Node(element);
        head.next = temp;
        size++;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            Node temp = head;

            @Override
            public boolean hasNext() {
                return temp != null;
            }

            @Override
            public T next() throws NoSuchElementException {
                if (!hasNext())
                    throw new NoSuchElementException("");
                T result = temp.data;
                temp = temp.next;
                return result;
            }
        };
    }
}
