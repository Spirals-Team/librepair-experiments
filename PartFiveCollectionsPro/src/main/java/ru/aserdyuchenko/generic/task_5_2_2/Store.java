package ru.aserdyuchenko.generic.task_5_2_2;

/**
 * @author anton.
 */

/**
 * Interface Store.
 * @param <T> - T.
 */
public interface Store<T extends Base> {
    /**
     * Add T in store.
     * @param value - user or role.
     */
    void add(T value);

    /**
     * Update T in store.
     * @param position - position for update.
     * @param value - new value.
     */
    void update(int position, T value);

    /**
     * Delete.
     * @param position - position for delete.
     */
    void delete(int position);
}
