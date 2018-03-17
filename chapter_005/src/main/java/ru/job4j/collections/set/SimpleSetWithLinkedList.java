package ru.job4j.collections.set;

import ru.job4j.collections.list.ContainerAsLinkedList;

/**
 * Класс Set на основе LinkedList.
 * @author Hincu Andrei (andreih1981@gmail.com)on 11.10.2017.
 * @version $Id$.
 * @since 0.1.
 * @param <E> Тип данных.
 */
public class SimpleSetWithLinkedList<E> extends ContainerAsLinkedList<E> {
    /**
     * Метод добовляет только уникальные элементы.
     * @param element элемент.
     * @return true or false.
     */
    @Override
    public boolean add(E element) {
        boolean found = false;
        if (!contains(element)) {
            super.add(element);
            found = true;
        }
        return found;
    }
}
