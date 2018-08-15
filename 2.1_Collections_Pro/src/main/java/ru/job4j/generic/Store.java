package ru.job4j.generic;

/**
 * Class Store.
 *@author ifedorenko
 *@since 25.09.2017
 *@version 1
 * @param <T> generic
 */
public interface Store<T extends Base> {
    /**
     * Method add.
     * @param value value for adding.
     */
    void add(T value);

    /**
     * Method update.
     * @param value value for updating.
     */
    void update(T value);

    /**
     * Method delete.
     * @param value value for deleting/
     */
    void delete(T value);
}
