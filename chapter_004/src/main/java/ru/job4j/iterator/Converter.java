package ru.job4j.iterator;

import java.util.Iterator;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class Converter {
    public Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> iter = it.next();
            @Override
            public boolean hasNext() {
                if (!iter.hasNext() && it.hasNext()) {
                    iter = it.next();
                }
                return iter.hasNext();
            }

            @Override
            public Integer next() {
                if (!iter.hasNext()) {
                    iter = it.next();
                }
                int i = iter.next();
                return i;
            }
        };
    }
}
