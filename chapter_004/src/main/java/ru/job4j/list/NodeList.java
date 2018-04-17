package ru.job4j.list;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class NodeList<E> implements ISimpleContainer<E> {

    private Node<E> first;
    private Node<E> last;
    private int modCount = -1;

    public int getModCount() {
        return modCount;
    }

    @Override
    public void add(E e) {
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
    public E get(int index) {
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

    public void addFirst(E e) {
        Node<E> newNode = new Node<>(e);
        if (this.isEmpty()) {
            this.last = newNode;
        }
        newNode.setNext(this.first);
        this.first = newNode;
        this.modCount++;
    }

    public E deleteFirst() {
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

    private boolean isEmpty() {
        return (first == null & last == null);
    }

    private void checkIndex(int index) {
        if (index < 0 || index > this.modCount) {
            throw new ArrayIndexOutOfBoundsException();
        }
    }

    public Node<E> getFirst() {
        return first;
    }
}
