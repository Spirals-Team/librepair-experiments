package ru.job4j.iterator;


import java.util.Iterator;

public class IteratorArray implements Iterator {

    private final int[] values;
    private int index = 0;

    public IteratorArray(final int[] values) {
        this.values = values;
    }

    @Override
    public boolean hasNext() {
        return values.length > index;
    }

    @Override
    public Object next() {
        return values[index++];
    }
}
