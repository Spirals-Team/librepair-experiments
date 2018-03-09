package ru.aserdyuchenko.iterator.task5_1_4;

import java.util.Iterator;

/**
 * @author anton.
 */
public class IteratorForIterator implements Iterator<Integer> {
    /**
     * @param iteratorIterator - iterator with iterators.
     */
    private Iterator<Iterator<Integer>> iteratorIterator;
    /**
     * @param iterator - main iterator.
     */
    private Iterator<Integer> iterator;

    /**
     * Convert iterator with iterators to iterator with integers.
     * @param it - iterator with iterators.
     * @return this - this iteraor.
     */
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        this.iteratorIterator = it;
        this.iterator = iteratorIterator.next();
        return this;
    }

    @Override
    public boolean hasNext() {
        boolean result = false;
        if (iterator.hasNext() || iteratorIterator.hasNext()) {
            result = true;
        }
        return result;
    }

    @Override
    public Integer next() {
        if (!iterator.hasNext() && iteratorIterator.hasNext()) {
            iterator = iteratorIterator.next();
        }
        return iterator.next();
    }
}
