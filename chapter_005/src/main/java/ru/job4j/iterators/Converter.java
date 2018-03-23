package ru.job4j.iterators;

import java.util.Iterator;

public class Converter {

    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {

        return new Iterator<Integer>() {

            Iterator<Integer> innerIterator = it.next();

            @Override
            public boolean hasNext() {
                return innerIterator.hasNext() || it.hasNext();
            }

            @Override
            public Integer next() {
                if (!innerIterator.hasNext()) {
                    innerIterator = it.next();
                }
                return innerIterator.next();
            }
        };
    }
}
