package ru.skorikov;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <E> параметр класса.
 *            5.3.2. Создать контейнер на базе связанного списка
 * @ author: Alex_Skorikov.
 * @ date: 23.10.17
 * @ version: java_kurs_standart
 */
public class SimpleLinkedContainer<E> implements Iterable<E> {

    /**
     * Счетчик.
     */
    private int index = 0;

    /**
     * Начальный элемент.
     */
    private Element elementFirst;

    /**
     * Конечный элемент.
     */
    private Element elementLast;

    /**
     * Класс Элемент со ссылкой.
     * из которого формируется коллекция.
     *
     * @param <E> параметр.
     */
    public static class Element<E> {
        /**
         * Data.
         */
        private E data;
        /**
         * Ссылка на следующий элемент.
         */
        private Element next = null;
        /**
         * Счетчик.
         */
        private int index = 0;

        /**
         * Получить индекс элемента.
         * @return индекс.
         */
        public int getIndex() {
            return index;
        }

        /**
         * Задать индекс элемента.
         * @param index индекс.
         */
        public void setIndex(int index) {
            this.index = index;
        }

        /**
         * Получить данные.
         * @return данные.
         */
        public E getData() {
            return data;
        }

        /**
         * Задать данные.
         * @param data данные.
         */
        public void setData(E data) {
            this.data = data;
        }

        /**
         * Получить следующий элемент.
         * @return элемент.
         */
        public Element getNext() {
            return next;
        }

        /**
         * Задать следующий элемент.
         * @param next элемент.
         */
        public void setNext(Element next) {
            this.next = next;
        }
    }

    /**
     * Добавить элемент в контейнер.
     *
     * @param value элемент
     */
    public void add(E value) {
        Element element = new Element();
        element.data = value;
        if (elementFirst == null) {
            elementFirst = element;
            elementLast = element;
        } else {
            elementLast.setNext(element);
            //elementLast.next = element;
            elementLast = element;
        }
        element.index = index++;
    }

    /**
     * Получить элемент из контейнера по индексу.
     *
     * @param index индекс.
     * @return элемент.
     */
    public E get(int index) {
        Element<E> search = null;
        Element getElement = elementFirst;
        while (getElement != null) {
            if (getElement.index == index) {
                search = getElement;
                break;
            } else {
                getElement = getElement.getNext();
            }
        }
        if (search != null) {
            return search.data;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Element<E> el1 = elementFirst;
            private Element<E> el2 = el1;

            @Override
            public boolean hasNext() {
                return el2 != null;
            }

            @Override
            public E next() {
                el1 = el2;
                el2 = el2.next;
                return el1.data;
            }
        };
    }

    /**
     * Получить индекс контейнера.
     * @return индекс.
     */
    public int getIndex() {
        return index;
    }

    /**
     * Установить индекс контейнера.
     * @param index индекс.
     */
    public void setIndex(int index) {
        this.index = index;
    }

    /**
     * Получить первый элемент.
     * @return первый элемент.
     */
    public Element getElementFirst() {
        return elementFirst;
    }

    /**
     * Задать первый элемент.
     * @param elementFirst первый элемент.
     */
    public void setElementFirst(Element elementFirst) {
        this.elementFirst = elementFirst;
    }

    /**
     * Получить последний элемент.
     * @return последний элемент.
     */
    public Element getElementLast() {
        return elementLast;
    }

    /**
     * Задать последний элемент.
     * @param elementLast последний элемент.
     */
    public void setElementLast(Element elementLast) {
        this.elementLast = elementLast;
    }
}
