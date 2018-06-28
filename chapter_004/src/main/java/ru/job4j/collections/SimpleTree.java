package ru.job4j.collections;

import java.util.Optional;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public interface SimpleTree<T extends Comparable<T>> extends Iterable<T> {
    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     * @param parent parent.
     * @param child child.
     * @return
     */
    boolean add(T parent, T child);

    Optional<Leaf<T>> findBy(T value);
}
