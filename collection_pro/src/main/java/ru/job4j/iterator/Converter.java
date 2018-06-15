package ru.job4j.iterator;

import java.util.*;

public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> integerIterator = it.next();

            @Override
            public boolean hasNext() {
           if (!integerIterator.hasNext() && it.hasNext()) {
               integerIterator = it.next();
           }
                return integerIterator.hasNext();
            }

            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                if (!integerIterator.hasNext() && it.hasNext()) {
                    integerIterator = it.next();
                }

                return integerIterator.next();
            }
        };
    }
}
