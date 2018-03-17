package ru.job4j.collections.generic;

/**
 *Class Store .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.10.17;
 * @version $Id$
 * @since 0.1
 * @param <T> тип принимаемых значений.
 */
public interface Store<T extends Base> {
    /**
     * Добавление элемента.
     * @param model новый элемент.
     * @return его же возврат.
     */
    T add(T model);

    /**
     * Обновление элемента.
     * @param model обновляемый элемент.
     * @return этот же элемент.
     */
    T update(T model);

    /**
     * Удаление элемента.
     * @param id элемента.
     * @return true or false.
     */
    boolean delete(String id);
}
