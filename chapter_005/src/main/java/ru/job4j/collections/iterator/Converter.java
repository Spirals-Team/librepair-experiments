package ru.job4j.collections.iterator;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *Converter .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 04.10.17;
 * @version $Id$
 * @since 0.1
 */
public class Converter {
    /**
     * Метод конвертирует итератор итераторов в простой итератор.
     * @param it итератор итераторов.
     * @return итератор .
     */
    Iterator<Integer> convert(Iterator<Iterator<Integer>> it) {
        return new Iterator<Integer>() {
            /**
             * Итератор итераторов.
             */
            private final Iterator<Iterator<Integer>> iterator = it;
            /**
             * текущий итератор.
             */
            private Iterator<Integer> current;

            /**
             * Метод проверяет есть ли еще элементы.
             * @return true or false.
             */
            @Override
            public boolean hasNext() {
                if (current == null && iterator.hasNext()) {
                    current = iterator.next();
                }
                if (current == null) {
                    return false;
                }
                if (current.hasNext()) {
                    return true;
                }
                if (iterator.hasNext()) {
                    current = iterator.next();
                }
                return current.hasNext();
            }

            /**
             * Возвращает текущий элемент и сдвигает каретку на следующий.
             * @return текущее значение.
             */
            @Override
            public Integer next() {
                if (current == null && iterator.hasNext()) {
                    current = iterator.next();
                }
                if (current == null) {
                    throw new NoSuchElementException();
                }
                if (!current.hasNext() && iterator.hasNext()) {
                    current = iterator.next();
                }
                return current.next();
            }
        };
    }
}
