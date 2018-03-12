package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            private Iterator<Integer> iterator = it.next();

            @Override
            public boolean hasNext() {
                if (iterator.hasNext()) {
                    return true;
                } else {
                    if (it.hasNext()) {
                        iterator = it.next();
                        return true;
                    }
                    return false;
                }
            }

            @Override
            public Integer next() {
                if (hasNext()) {
                    return iterator.next();
                } else {
                    throw new NoSuchElementException("Ooops!");
                }
            }
        };
    }
}
