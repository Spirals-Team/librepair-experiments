package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */


public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            Iterator<Integer> currentIterator = null;

            @Override
            public boolean hasNext() {
                selectCurrentIterator();
                return (currentIterator != null && currentIterator.hasNext());
            }

            private void selectCurrentIterator() {
                if (currentIterator != null && currentIterator.hasNext()) {
                    return;
                }
                currentIterator = null;
                while (it.hasNext()) {
                    Iterator<Integer> nextIterator = it.next();
                    if (nextIterator.hasNext()) {
                        currentIterator = nextIterator;
                        break;
                    }
                }
            }

            @Override
            public Integer next() {
                selectCurrentIterator();
                if (currentIterator == null) {
                    throw new NoSuchElementException();
                }
                return currentIterator.next();
            }
        };
    }


}