package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class FirstLastList<E> implements Iterable<E> {
    private Node<E> first;
    private Node<E> last;
    private int size = 0;
    protected transient int modCount = 0;
    int  expectedModCount = 0;

    public boolean isEmpty() {
        return first == null;
    }

    public void add(E value) {
        Node<E> newLink = new Node<>(value);
        if (isEmpty()) {
            last = newLink;
            newLink.next = this.first;
            this.first = newLink;
        } else {
            last.next = newLink;
            newLink.previous = last;
            last = newLink;
        }
        this.size++;
        modCount++;

    }
    public void insetFirst(E value) {
        Node<E> newLink = new Node<>(value);
        newLink.next = first;
        first = newLink;
        size++;
    }



    public E get(int index) {
        Node<E> result = this.first;
        for (int i = 0; i < index; i++) {
           result = result.next;
        }
        return result.date;
    }



    public E deleteFirst() {
        E temp = first.date;
        if (size > 1) {
            this.first.next.previous = null;
            this.first = first.next;
        } else {
            this.first = null;
            this.last = null;
        }
        this.size--;
        return  temp;
    }

    public E deleteLast() {
        E temp = last.date;
        if (size > 1) {
            this.last.previous.next = null;
            this.last = this.last.previous;
        } else {
            this.first = null;
            this.last = null;
        }
        this.size--;
        return temp;
    }


    public int getSize() {
        return size;
    }

    private static class Node<E> {
        E date;
        Node<E> next;
        Node<E> previous;

        Node(E date) {
            this.date = date;
       }
    }



    final void checkModcount() {
         if (modCount != size) {
            throw new ConcurrentModificationException("this collection has undergone a change");
        }
     }


    @Override
    public Iterator<E> iterator() {
        expectedModCount = modCount;
        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return first != null;
            }

            @Override
            public E next() {
                checkModcount();
                if (!hasNext()) {
                    throw new NoSuchElementException("no such");
                }
               E result = first.date;
                first = first.next;

                return  result;
            }
        };
    }
}
