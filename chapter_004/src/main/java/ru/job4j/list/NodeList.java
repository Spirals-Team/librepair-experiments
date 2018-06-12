package ru.job4j.list;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
@ThreadSafe
public class NodeList<E> implements ISimpleContainer<E> {

    @GuardedBy("this")
    private Node<E> first;
    @GuardedBy("this")
    private Node<E> last;
    @GuardedBy("this")
    private int modCount = -1;

    public synchronized int getModCount() {
        return modCount;
    }

    @Override
    public synchronized void add(E e) {
        Node<E> newNode = new Node<>(e);
        if (this.isEmpty()) {
            this.first = newNode;
        } else {
            this.last.setNext(newNode);
        }
        this.last = newNode;
        this.modCount++;
    }

    @Override
    public synchronized E get(int index) {
        this.checkIndex(index);
        Node<E> current = this.first;
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return current.getElem();
    }

    @Override
    public Iterator<E> iterator() {
        return new NodeListIterator<>(this);
    }

    public synchronized void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (this.isEmpty()) {
            this.last = newNode;
        }
        newNode.setNext(this.first);
        this.first = newNode;
        this.modCount++;
    }

    public synchronized E deleteFirst() {
        if (this.isEmpty()) {
            throw new NoSuchElementException();
        }
        Node<E> current = this.first;
        if (current.getNext() == null) {
            this.first = current.getNext();
            this.last = current.getNext();
        } else {
            this.first = current.getNext();
        }
        this.modCount--;
        return current.getElem();
    }

    private synchronized boolean isEmpty() {
        return (first == null & last == null);
    }

    private synchronized void checkIndex(int index) {
        if (index < 0 || index > this.modCount) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public synchronized Node<E> getFirst() {
        return first;
    }
}
