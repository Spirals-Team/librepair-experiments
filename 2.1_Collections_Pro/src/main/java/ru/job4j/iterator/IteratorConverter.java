package ru.job4j.iterator;

import java.util.Iterator;

/**
 *Класс IteratorConverter.
 *@author ifedorenko
 *@since 20.09.2017
 *@version 1
 */

public class IteratorConverter {
    /**
     * Method convert.
     * @param it Iterator<Iterator<Integer>>
     * @return Iterator<Integer>
     */
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        Iterator<Integer> result = null;
        if (it.hasNext()) {
            result = new Iterator<Integer>() {
                private Iterator<Integer> iterator = it.next();

                @Override
                public boolean hasNext() {
                    boolean res = false;
                    if (iterator.hasNext()) {
                        res = true;
                    }
                    return res;
                }

                @Override
                public Integer next() {
                    Integer value = iterator.next();
                    if (!iterator.hasNext()) {
                        if (it.hasNext()) {
                            iterator = it.next();
                        }
                    }
                    return value;
                }
            };
        }
        return result;
    }
}