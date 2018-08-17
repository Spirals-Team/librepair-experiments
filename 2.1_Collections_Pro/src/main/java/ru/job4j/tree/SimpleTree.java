package ru.job4j.tree;

/**
 * Interface SimpleTree.
 * @param <E> generic
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавить элемент child в parent.
     * Parent может иметь список child.
     * @param parent parent.
     * @param child  child.
     * @return result
     */
    boolean add(E parent, E child);
}
