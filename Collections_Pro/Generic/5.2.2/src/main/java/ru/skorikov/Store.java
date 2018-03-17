package ru.skorikov;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <T> Параметр.
 * @ author: Alex_Skorikov.
 * @ date: 09.10.17
 * @ version: java_kurs_standart
 * Интерфейс Store парметризованный классом Base и наследниками.
 */
public interface Store<T extends Base> {

    /**
     * Добавить объект.
     *
     * @param model объект.
     * @return объект.
     */
    T add(T model);

    /**
     * Обновить объект.
     *
     * @param model объект.
     * @return объект.
     */
    T update(T model);

    /**
     * Удалить объект.
     *
     * @param id Id объекта.
     * @return выполнена ли операция.
     */
    boolean delete(String id);
}
