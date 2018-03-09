package ru.aserdyuchenko.list.task_5_3_1;

import java.util.Iterator;

/**
 * @author anton.
 */

/**
 * My own list.
 * @param <E> - E.
 */
public class MyOwnList<E> implements SimpleContainer<E> {
    /**
     * @param container - main container.
     */
    private Object[] container;
    /**
     * @param index - index of container.
     */
    private int index = 0;

    /**
     * Constructor.
     */
    public MyOwnList() {
        this.container = new Object[2];
    }

    @Override
    public void add(E value) {
        if (checkSize()) {
            container[index++] = value;
        } else {
            createNewSize();
            container[index++] = value;
        }
    }

    @Override
    public E get(int index) {
        return (E) container[index];
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < index && container[currentIndex] != null;
            }

            @Override
            public E next() {
                return (E) container[currentIndex++];
            }
        };
        return it;
    }

    /**
     * Check size.
     * @return result of ckeck.
     */
    private boolean checkSize() {
        boolean check = false;
        if (index < container.length - 1) {
            check = true;
        }
        return check;
    }

    /**
     * Create new size for container.
     */
    private void createNewSize() {
        Object[] newObject = new Object[this.container.length * 2];
        System.arraycopy(newObject, 0, newObject, 0, container.length);
        this.container = newObject;
    }
}
