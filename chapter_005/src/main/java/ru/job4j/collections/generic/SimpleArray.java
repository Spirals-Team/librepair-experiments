package ru.job4j.collections.generic;

/**
 *SimpleArray .
 * Доделать контейнер SimpleArray<T> добавить методы addOrRemove, update, delete, get(int index).
 * @author Hincu Andrei (andreih1981@gmail.com) by 05.10.17;
 * @version $Id$
 * @since 0.1
 * @param <T> параметризированный тип.
 */
public class SimpleArray<T> {
    /**
     * Массив.
     */
    private Object[]objects;
    /**
     * Позиция в массиве.
     */
    private int index = 0;

    /**
     * Конструктор класса.
     * @param size длинна массива.
     */
    public SimpleArray(int size) {
        this.objects = new Object[size];
    }

    /**
     * Метод добавляет новые элеметы в массив.
     * @param value новый элемент.
     */
    public void add(T value) {
        objects[index++] = value;
    }

    /**
     * Метод обновляет значение по индексу.
     * @param index позиция в массиве.
     * @param value новое значение.
     */
    public void update(int index, T value) {
        objects[index] = value;
    }

    /**
     * Метод обновляет элемент по значению.
     * @param valueOld прежнее значение.
     * @param valueNew новое значение.
     */
    public void update(T valueOld, T valueNew) {
        for (int i = 0; i != index; i++) {
            if (valueOld.equals(objects[i])) {
                objects[i] = valueNew;
                break;
            }
        }
    }

    /**
     * Обновление элемента.
     * @param value элемент.
     */
    public void update(T value) {
        for (int i = 0; i < objects.length; i++) {
            if (value.equals(objects[i])) {
                objects[i] = value;
                break;
            }
        }
    }
    /**
     * Метод возвращает значение по индексу.
     * @param pozition позиция в массиве.
     * @return значение элемента.
     */
    @SuppressWarnings("unchecked")
    public T getValue(int pozition) {
        return (T) objects[pozition];
    }

    /**
     * Метод удаляет заданное значение из массива.
     * @param value  выбранное значение для удаления.
     * @return true or false.
     */
    public boolean delete(T value) {
        boolean found = false;
        for (int i = 0; i < objects.length; i++) {
            if (value.equals(objects[i])) {
                objects[i] = objects[index - 1];
                objects[index - 1] = null;
                found = true;
                break;
            }
        }
        return found;
    }

    /**
     * Метод удаляет по индексу в массиве.
     * @param index позиция в массиве.
     * @return true or false.
     */
    public boolean delete(int index) {
        boolean f = false;
        if (index < this.index) {
            objects[index] = objects[this.index - 1];
            objects[this.index - 1] = null;
            f = true;
        }
        return f;
    }
}
