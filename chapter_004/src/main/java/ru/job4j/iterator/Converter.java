package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Converter {

    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {

        return new Iterator<Integer>() {

            Iterator<Integer> list = it.next();

            @Override
            public boolean hasNext() {
                if (it.hasNext() & !list.hasNext()) {
                    list = it.next();
                }
                return list.hasNext();
            }

            @Override
            public Integer next() {
                if (!this.hasNext()) {
                    throw new NoSuchElementException();
                }
                return list.next();
            }
        };
    }
}
