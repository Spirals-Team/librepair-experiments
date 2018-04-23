package ru.aserdyuchenko.set.task_3;

import java.util.Arrays;
import java.util.Iterator;

/**
 * @author anton.
 * @param <E> - element.
 */
public class MyHashTable<E> implements Iterable<E> {
    /**
     * @param objects - objects.
     */
    private Object[] objects;
    /**
     * Constructor.
     */
    public MyHashTable() {
        this.objects = new Object[2];
    }

    /**
     * Add elemetn.
     * @param e - new element.
     */
    public void add(E e) {
        int arrayIndex = e.hashCode() / 1000;
        if (needNewSize(arrayIndex)) createNewSize(arrayIndex);
        if (isOrigin(e)) objects[arrayIndex] = e;
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(objects);
    }

    /**
     * Check element.
     * @param value - element.
     * @return true if element origin.
     */
    private boolean isOrigin(E value) {
        int valueIndex = value.hashCode() / 1000;
        return (objects[valueIndex] != null && objects[valueIndex].equals(value)) ? false : true;
    }

    /**
     * Check size.
     * @return result of ckeck.
     */
    private boolean needNewSize(int arrayIndex) {
        return arrayIndex > objects.length - 1;
    }

    /**
     * Create new size for container.
     */
    private void createNewSize(int newSize) {
        Object[] newObject = new Object[newSize + 2];
        System.arraycopy(objects, 0, newObject, 0, objects.length);
        this.objects = newObject;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int currentIndex = 0;

            @Override
            public boolean hasNext() {
                while (objects[currentIndex] == null && currentIndex < objects.length - 1) {
                    currentIndex++;
                }
                return objects[currentIndex++] != null ? true : false;
            }

            @Override
            public E next() {
                while (objects[currentIndex] == null && currentIndex < objects.length - 1) {
                    currentIndex++;
                }
                return (E) objects[currentIndex++];
            }
        };
    }
}
