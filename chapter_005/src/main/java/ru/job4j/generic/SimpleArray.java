package ru.job4j.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleArray<T> implements Iterable<T> {

    private Object[] objects;
    private int index = 0;

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int position = 0;

            @Override
            public boolean hasNext() {
                return index > position;
            }

            @Override
            @SuppressWarnings("unchecked")
            public T next() {
                try {
                    return (T) objects[position++];
                } catch (ArrayIndexOutOfBoundsException e) {
                    throw new NoSuchElementException();
                }
            }
        };
    }


    public SimpleArray(int size) {
        this.objects = new Object[size];
    }

    public void add(T value) {
        if (this.index < this.objects.length) {
            this.objects[this.index++] = value;
        }
    }

    @SuppressWarnings("unchecked")
    public T get(int position) {
        if (position <= this.index) {
            return (T) this.objects[position];
        }
        return null;
    }

    public void update(T value, int position) {
        if (position <= this.index) {
            this.objects[position] = value;
        }
    }

    public void delete(int position) {
        if (position < this.index) {
            System.arraycopy(this.objects, position + 1,
                             this.objects, position, this.objects.length - position - 1);
            this.objects[objects.length - 1] = null;
            this.index--;
        }
    }
}
