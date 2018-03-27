package ru.job4j.collections.tree;

/**
 * Элементарное дерево.
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 18.10.17;
 * @version $Id$
 * @since 0.1
 * @param <E> тип данных.
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     * @param parent parent.
     * @param child child.
     * @return true родитель найден ребенок добавлен или false неудача.
     */
    boolean add(E parent, E child);
}
