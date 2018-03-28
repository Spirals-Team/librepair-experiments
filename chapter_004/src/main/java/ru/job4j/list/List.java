package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

@ThreadSafe
public class List<T> implements SimpleContainer<T> {
    private int modCount = 0;
    private int size = 0;
    @GuardedBy("this")
    private Node head;

    private class Node {
        private Node next;
        private T data;

        public Node(T data) {
            this.data = data;
        }

        public T getData() {
            return data;
        }
    }

    @Override
    public synchronized boolean add(T element) {
        Node temp = head;
        head = new Node(element);
        head.next = temp;
        modCount++;
        size++;
        return true;
    }

    @Override
    public synchronized T get(int index) {
        Node temp = head;
        int n = size;
        T result = null;

        while (index != size)
        {
            index++;
            if (index == size)
                result = temp.data;
            temp = temp.next;
        }
        return result;
    }

    @Override
    public synchronized Iterator<T> iterator() throws ConcurrentModificationException, NoSuchElementException {
        return new Iterator<T>() {
            int currentModCount = modCount;
            Node temp = head;
            int nextIndex = 0;


            @Override
            public boolean hasNext() {
                return nextIndex < size;
            }

            @Override
            public T next() {
                if (!hasNext())
                    throw new NoSuchElementException();
                if (currentModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                T result = temp.data;
                temp = temp.next;
                nextIndex++;

                return result;
            }
        };
    }
}
