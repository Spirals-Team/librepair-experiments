package ru.skorikov;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <E> параметр класса.
 *            Set реализованный на связном списке.
 * @ author: Alex_Skorikov.
 * @ date: 01.11.17
 * @ version: java_kurs_standart
 */
public class SimpleSet<E> implements Iterator<E> {

    /**
     * Первый элемент коллекции.
     */
    private Element firstElement;

    /**
     * Последний элемент коллекции.
     */
    private Element lastElement;

    /**
     * Элемент для итератора.
     */
    private Element iteratorElement;

    /**
     * Класс Элемент.
     *
     * @param <E> параметр.
     */

    private class Element<E> {
        /**
         * Данные элемента коллекции.
         */
        private E data;
        /**
         * Ссылка на следующий элемент коллекции.
         */
        private Element next;

        /**
         * Конструктор.
         *
         * @param data данные элемента коллекции.
         */
        Element(E data) {
            this.data = data;
            this.next = null;
        }
    }

    /**
     * Is collection has a dublicate.
     *
     * @param data ekement data.
     * @return - true if has dublicate, else false.
     */
    public boolean hasDeblicate(E data) {
        boolean isDublicate = true;
        Element<E> element = firstElement;
        while (element != null) {
            if (!data.equals(element.data)) {
                element = element.next;
            } else {
                isDublicate = true;
                break;
            }
            isDublicate = false;
        }
        return isDublicate;
    }

    /**
     * Add element to collection.
     *
     * @param data - elemeent data.
     */
    public void add(E data) {
        //Если коллекция пустая
        if (firstElement == null) {
            firstElement = new Element(data);
            lastElement = firstElement;
            iteratorElement = firstElement;
        } else {
            if (!hasDeblicate(data)) {
                Element<E> eElement = new Element<>(data);
                lastElement.next = eElement;
                lastElement = eElement;
            }
        }
    }

    @Override
    public boolean hasNext() {
        if (firstElement != null) {
            return iteratorElement != null;
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public E next() {
        Element<E> returnElement = iteratorElement;
        if (hasNext()) {
            iteratorElement = iteratorElement.next;
            return returnElement.data;
        } else {
            throw new NoSuchElementException();
        }
    }
}
