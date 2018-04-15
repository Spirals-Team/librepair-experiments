package ru.aserdyuchenko.tree.task_1;

/**
 * @author anton.
 */
public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    /**
     * Добавляет элемент child в parent.
     * @param parent
     * @param child
     * @return
     */
    boolean add(E parent, E child);

}
