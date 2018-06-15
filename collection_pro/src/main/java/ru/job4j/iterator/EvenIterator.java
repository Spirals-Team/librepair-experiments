package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class EvenIterator implements Iterator {
    private int[] array;
    private int count = 0;

    public EvenIterator(int[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        while (count < array.length) {
            if (this.array[this.count] % 2 == 0) {
                result = true;
                break;
            }
            count++;
        }
        return result;
    }

    @Override
    public Object next() {
        if (!hasNext()) {
                throw new NoSuchElementException("no such even element");
        }

        return this.array[this.count++];
    }
}