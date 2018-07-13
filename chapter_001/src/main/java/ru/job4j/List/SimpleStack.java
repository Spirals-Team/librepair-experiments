package ru.job4j.List;

import java.util.NoSuchElementException;

/**
 * Класс представляет собой стек типа T с базовыми функциями
 * @param <T> необходимый тип данных
 */
public class SimpleStack<T> {

    // хранилище на основе связанного списка
    private LinkList<T> stack = new LinkList<>();

    /**
     * Метод возвращает размер хранилища
     * @return размер
     */
    public int size() {
        return stack.size();
    }

    /**
     * Метод возвращает верхнее значение из хранилища и удаляет его
     * @return значение
     */
    public T poll() {
        if (stack.size() > 0) {
            T result = stack.getLastNode().data;
            stack.setLastNode(stack.getLastNode().left);
            stack.setSize(stack.size()-1);
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
        stack.add(value);
    }
}
