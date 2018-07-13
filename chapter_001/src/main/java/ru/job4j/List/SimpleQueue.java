package ru.job4j.List;

import java.util.NoSuchElementException;

/**
 * Класс представляет собой очередь типа T с базовыми функциями
 * @param <T> необходимый тип данных
 */
public class SimpleQueue<T> {

    // хранилище на основе связанного списка
    private LinkList<T> queue = new LinkList<>();

    /**
     * Метод возвращает размер хранилища
     * @return размер
     */
    public int size() {
        return queue.size();
    }

    /**
     * Метод возвращает первое значение из хранилища и удаляет его
     * @return значение
     */
    public T poll() {
        if (queue.size() > 0) {
            T result = queue.getFirstNode().data;
            queue.setFirstNode(queue.getFirstNode().right);
            queue.setSize(queue.size()-1);
            return result;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Метод добавляет значение в хранилище
     * @param value значение
     */
    public void push(T value) {
        queue.add(value);
    }

}
