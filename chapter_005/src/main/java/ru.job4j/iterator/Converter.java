package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Converter {

    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {

        return new Iterator<Integer>() {

            private Iterator<Integer> iterator;

            private boolean hasNextIteratorIterator() {

                return it.hasNext();
            }

            private boolean hasNextIterator() {

                return iterator.hasNext();
            }

            @Override
            public boolean hasNext() {

                return hasNextIteratorIterator() || hasNextIterator();
            }

            @Override
            public Integer next() {

                if (!hasNext()) {

                    throw new NoSuchElementException();

                } else {

                    if (iterator == null || !hasNextIterator()) {

                        iterator = it.next();

                        return iterator.next();

                    } else {

                        return iterator.next();
                    }
                }

            }

        };

    }

}