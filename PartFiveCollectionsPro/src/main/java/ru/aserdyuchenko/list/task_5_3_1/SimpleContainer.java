package ru.aserdyuchenko.list.task_5_3_1;

/**
 * @author anton.
 */

/**
 * Simple container.
 * @param <E> - E.
 */
public interface SimpleContainer<E> extends Iterable<E> {
    /**
     * Add.
     * @param e - new value.
     */
    void add(E e);

    /**
     * Get.
     * @param index - index.
     * @return value.
     */
    E get(int index);
}
