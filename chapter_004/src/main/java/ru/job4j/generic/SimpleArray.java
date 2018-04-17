package ru.job4j.generic;

import java.util.Iterator;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SimpleArray<T> implements Iterable<T> {

    private Object[] models;
    private int size = 0;

    SimpleArray(int maxSize) {
        this.models = new Object[maxSize];
    }

    @Override
    public Iterator<T> iterator() {
        return new SimpleArrayIterator<>(this);
    }

    public int getSize() {
        return size;
    }

    public void add(T model) {
        this.models[this.size++] = model;
    }

    public T set(int index, T model) {
        this.checkSize(index);
        models[index] = model;
        return model;
    }

    public T delete(int index) {
        this.checkSize(index);
        Object current = models[index];
        if (index == this.size - 1) {
            models[index] = null;
        }
        Object[] newArray = new Object[size];
        if (index == 0) {
            System.arraycopy(this.models, 1, newArray, 0, this.size - 1);
        }
        if (index != this.size - 1 & index != 0) {
            System.arraycopy(models, 0, newArray, 0, index);
            System.arraycopy(models, index + 1, newArray, index, this.size - (index + 1));
        }
        models = newArray;
        return (T) current;
    }

    public T get(int index) {
        this.checkSize(index);
        return (T) this.models[index];
    }

    private void checkSize(int index) {
        if (index >= this.size) {
            throw new IndexOutOfBoundsException();
        }
    }
}
