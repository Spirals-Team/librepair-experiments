package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Converter {
    private Iterator<Iterator<Integer>> iteratorOfIterators;
    private Iterator<Integer> currentIterator;

    public Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        this.iteratorOfIterators = it;
        Iterator<Integer> result = new Iterator<Integer>() {
            @Override
            public boolean hasNext() {
                while (currentIterator == null || !currentIterator.hasNext()) {
                    if (!iteratorOfIterators.hasNext()) return false;
                    currentIterator = iteratorOfIterators.next();
                }
                return true;
            }

            @Override
            public Integer next() throws NoSuchElementException {
                if (!hasNext())
                    throw new NoSuchElementException();
                return currentIterator.next();
            }
        };
        return result;
    }
}
