package ru.job4j.generic;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SimpleArrayIterator<T> implements Iterator<T> {

    private final SimpleArray<T> models;
    private int index = 0;

    SimpleArrayIterator(SimpleArray<T> models) {
        this.models = models;
    }

    @Override
    public boolean hasNext() {
        return index != models.getSize() && models.get(index) != null;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException();
        }
        return models.get(index++);
    }
}
