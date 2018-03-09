package ru.aserdyuchenko.set.task_3;

import java.util.Iterator;

/**
 * @author anton.
 * @param <E> - element.
 */
public class SimpleSet<E> implements Iterable<E> {
    /**
     * @param objects - objects.
     */
    private Object[] objects;
    /**
     * @param index - index for objects.
     */
    private int index = 0;

    /**
     * Constructor.
     */
    public SimpleSet() {
        this.objects = new Object[2];
    }

    /**
     * Add elemetn.
     * @param e - new element.
     */
    public void add(E e) {
        if (checkSize()) {
            if (isOrigin(e)) {
                objects[index++] = e;
            }
        } else {
            createNewSize();
            if (isOrigin(e)) {
                objects[index++] = e;
            }
        }
    }

    /**
     * Check element.
     * @param value - element.
     * @return true if element origin.
     */
    private boolean isOrigin(E value) {
        boolean result = true;
        for (Object element : objects) {
            if (value.equals(element)) {
                result = false;
            }
        }
        return result;
    }

    /**
     * Check size.
     * @return result of ckeck.
     */
    private boolean checkSize() {
        boolean check = false;
        if (index < objects.length - 1) {
            check = true;
        }
        return check;
    }

    /**
     * Create new size for container.
     */
    private void createNewSize() {
        Object[] newObject = new Object[this.objects.length * 2];
        System.arraycopy(objects, 0, newObject, 0, objects.length);
        this.objects = newObject;
    }

    @Override
    public Iterator<E> iterator() {
        Iterator<E> it = new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                return currentIndex < index && objects[currentIndex] != null;
            }

            @Override
            public E next() {
                return (E) objects[currentIndex++];
            }
        };
        return it;
    }
}
