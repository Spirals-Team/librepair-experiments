package ru.skorikov;

import net.jcip.annotations.GuardedBy;
import net.jcip.annotations.ThreadSafe;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <E> параметр класса.
 *            5.3.2. Создать контейнер на базе связанного списка
 * @ author: Alex_Skorikov.
 * @ date:10.01.18
 * @ version: java_kurs_standart
 */
@ThreadSafe
public class SimpleLinkedContainer<E> implements Iterable<E> {

    /**
     * Счетчик.
     */
    @GuardedBy("this")
    private int index = 0;

    /**
     * First element.
     */
    @GuardedBy("this")
    private Element elementFirst;

    /**
     * Last element.
     */
    @GuardedBy("this")
    private Element elementLast;


    /**
     * Класс Элемент со ссылкой.
     * из которого формируется коллекция.
     *
     * @param <E> параметр.
     */
    static class Element<E> {
        /**
         * Данные.
         */
        private E data;
        /**
         * Следующий элемент.
         */
        private Element next = null;
        /**
         * счетчик.
         */
        private int index = 0;

    }


    /**
     * Добавить элемент в контейнер.
     *
     * @param value элемент
     */
    synchronized void add(E value) {
        Element element = new Element();
        element.data = value;
        element.index = index;
        if (elementFirst == null) {
            elementFirst = element;
            elementLast = element;
        } else {
            elementLast.next = element;
            elementLast = element;
        }
        index++;
    }

    /**
     * Получить элемент из контейнера по индексу.
     *
     * @param index индекс.
     * @return элемент.
     */
    synchronized E get(int index) {

        Element search = null;
        Element getElement = elementFirst;
        while (getElement != null) {
            if (getElement.index == index) {
                search = getElement;
                break;
            } else {
                getElement = getElement.next;
            }
        }
        if (search != null) {
            return (E) search.data;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public synchronized Iterator<E> iterator() {
        return new Iterator<E>() {
            private Element el1 = elementFirst;
            private Element el2 = el1;

            @Override
            public boolean hasNext() {
                return el2 != null;
            }

            @Override
            public E next() {
                el1 = el2;
                el2 = el2.next;
                return (E) el1.data;
            }
        };
    }
}
