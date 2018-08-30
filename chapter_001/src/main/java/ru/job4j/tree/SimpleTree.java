package ru.job4j.tree;

import ru.job4j.list.LinkedList;

import java.util.Optional;

/**
 * Simple structure of tree.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     *
     * @param parent parent.
     * @param child  child.
     * @return
     */
    boolean add(E parent, E child);

    Optional<Node<E>> findBy(E value);
}
