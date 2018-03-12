package ru.job4j.list;

/**
 * Интерфейс для Stack и Queue.
 * @param <T> тип объектов коллекции.
 */
public interface SimpleStackAndQueue<T> {
    /**
     * Метод для получения объекта и удаления объекта.
     * @return вернем значение объекта.
     */
    T poll();

    /**
     * Метод для добавления объекта.
     * @param value задаем значение объекту.
     */
    void push(T value);
}
