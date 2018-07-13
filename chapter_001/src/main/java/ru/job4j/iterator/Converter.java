package ru.job4j.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class Converter {
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            // внутрений итератор
            private Iterator<Integer> innerIter = null;

            @Override
            public boolean hasNext() {
                checkInnerIter();
                return (innerIter != null && innerIter.hasNext());
            }

            @Override
            public Integer next() {
                checkInnerIter();
                if (innerIter == null) {
                    throw new NoSuchElementException();
                }
                return innerIter.next();
            }

            /**
             * Метод проверяет внутренний итератор и в случаи необходимости переносит внутренний указатель на след итератор
             */
            private void checkInnerIter() {
                if (innerIter != null && innerIter.hasNext()) {
                    return;
                }
                innerIter = null;
                if (it.hasNext()) {
                    innerIter = it.next();
                }
            }
        };
    }
}
