package ru.skorikov;


/**
 * Created with IntelliJ IDEA.
 *
 * @ author: Alex_Skorikov.
 * @ date: 22.10.17
 * @ version: java_kurs_standart
 * Абстрактный класс.
 * Реализует интерфейс Store.
 * Имеет реализованные методы для
 * избежания дублирования кода в классах- наследниках.
 * @param <T> параметр.
 */

public abstract class AbstractStore<T extends Base> implements Store<T> {
    /**
     * Массив элементов.
     */
    private SimpleArray array;

    /**
     * Конструктор.
     *
     * @param array массив элементов.
     */

    public AbstractStore(SimpleArray array) {
        this.array = array;
    }

    /**
     * Добавить элемент в массив.
     *
     * @param model объект.
     * @return объект.
     */

    public T add(T model) {
        array.add(model);
        return model;
    }

    /**
     * Получить массив элементов.
     * @return массив.
     */
    public SimpleArray getArray() {
        return array;
    }

    /**
     * Обновить элемент в массиве.
     *
     * @param model объект.
     * @return объект.
     */
    public T update(T model) {
        String modelId = model.getId();
        for (int i = 0; i < array.getObjects().length; i++) {
            User searchUser = (User) array.getObjects()[i];
            if (searchUser.getId().equals(modelId)) {
                array.update(i, model);
                break;
            }
        }
        return model;
    }

    /**
     * Удалить элемент из массива.
     *
     * @param id Id объекта.
     * @return успешно?
     */
    public boolean delete(String id) {
        boolean isDelete = false;
        for (int i = 0; i < array.getObjects().length; i++) {
            User searchUser = (User) array.getObjects()[i];
            String searchId = searchUser.getId();
            if (id.equals(searchId)) {
                array.delete(searchUser);
                isDelete = true;
                break;
            }
        }
        return isDelete;
    }
}
