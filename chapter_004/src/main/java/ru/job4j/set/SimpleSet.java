package ru.job4j.set;

import ru.job4j.list.Array;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class SimpleSet<T> implements Iterator<T> {
    private Array<T> array = new Array<>();
    private int size = 0;
    private int position = 0;

    public void add(T element) {
        if (!array.contains(element)) {
            array.add(element);
            size++;
        }
    }

    public T[] toArray() {
        return (T[]) array.toArray();
    }

    @Override
    public boolean hasNext() {
        return position < size;
    }

    @Override
    public T next() throws NoSuchElementException {
        if (!hasNext())
            throw new NoSuchElementException("No such element!");
        return array.get(position++);
    }
}
