package ru.job4j.list;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class NodeListIterator<E> implements Iterator<E> {

    private NodeList<E> list;
    private Node<E> current;
    private int expectedModCount;

    public NodeListIterator(NodeList<E> list) {
        this.list = list;
        this.current = list.getFirst();
        this.expectedModCount = list.getModCount();
    }

    @Override
    public boolean hasNext() {
        this.checkModCount();
        boolean result = true;
        if (this.current == null) {
            result = false;
        }
        return result;
    }

    @Override
    public E next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException();
        }
        E elem = this.current.getElem();
        this.current = this.current.getNext();
        return elem;
    }

    private void checkModCount() {
        if (expectedModCount != list.getModCount()) {
            throw new ConcurrentModificationException();
        }
    }
}
