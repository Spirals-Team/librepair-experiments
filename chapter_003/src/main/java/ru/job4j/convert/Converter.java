package ru.job4j.convert;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Converter class
 *
 * @author Aleksandr Vysotskiy
 * @verion 1.5
 * @since 18.05.18
 */

public class Converter {
    /**
     * This is a method for runs through iterators which contain elements of type Integer.
     *
     * @param it - Iterator containing iterators.
     * @return Iterator containing Integer.
     */
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> first;
            Iterator<Integer> second;

            /**
             * This is a method hasNext.
             *
             * @return true if there is next object, other false.
             */
            @Override
            public boolean hasNext() {
                boolean result = false;
                if (first != null && first.hasNext()) {
                    result = true;
                } else {
                    while (it.hasNext()) {
                        second = it.next();
                        if (second.hasNext()) {
                            first = second;
                            result = true;
                            break;
                        }
                    }
                }
                return result;
            }

            /**
             * This is a method next;
             *
             * @return next object in iteration.
             */
            @Override
            public Integer next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return first.next();
            }
        };
    }
}