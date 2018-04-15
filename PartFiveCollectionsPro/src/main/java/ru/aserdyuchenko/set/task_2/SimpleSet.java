package ru.aserdyuchenko.set.task_2;

import ru.aserdyuchenko.list.task_5_3_1.SimpleContainer;
import java.util.Iterator;

/**
 * @author anton.
 * Set реализованный на связном списке.
 * @param <E> - element.
 */
public class SimpleSet<E> implements SimpleContainer<E> {
    /**
     * @param counter - counter.
     */
    private int counter = 0;
    /**
     * @param current - current.
     */
    private Node current;

    @Override
    public void add(E e) {
        if (isOrigin(e)) {
            if (current == null) {
                current = new Node(e);
            } else {
                current.setNext(new Node(e));
            }
            counter++;
        }
    }

    /**
     * Check element.
     * @param value - element.
     * @return true if element origin.
     */
    private boolean isOrigin(E value) {
        for (int i = 0; i < counter; i++) {
            if (value.equals(get(i))) {
                return false;
            }
        }
        return true;
    }

    @Override
    public E get(int index) {
        for (int i = 0; i < index; i++) {
            current = current.getNext();
        }
        return (E) current.getData();
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < counter;
            }

            @Override
            public E next() {
                return get(currentIndex++);
            }
        };
    }
}

/**
 * Node.
 */
class Node {
    /**
     * @param next - next.
     */
    private Node next;
    /**
     * @param data - data.
     */
    private Object data;

    /**
     * Constructor.
     * @param dataVale - data value.
     */
    Node(Object dataVale) {
        next = null;
        data = dataVale;
    }

    /**
     * Get data.
     * @return - data.
     */
    public Object getData() {
        return  data;
    }

    /**
     * Get next.
     * @return - next.
     */
    public Node getNext()  {
        return next;
    }

    /**
     * Set next.
     * @param nextValue - next value.
     */
    public void setNext(Node nextValue) {
        next = nextValue;
    }
}
