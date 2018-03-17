package ru.job4j.collections.list;

/**
 * @author Hincu Andrei (andreih1981@gmail.com)on 09.10.2017.
 * @version $Id$.
 * @since 0.1.
 * @param <T> тип хранимых данных.
 */
public class SimpleStac<T> extends ContainerAsLinkedList<T> {
    /**
     * Конструктор класса.
     */
    public SimpleStac() {
    }

    /**
     * Метод возвращает и удаляет последний элемент.
     * @return последний элемент.
     */
    public T poll() {
        return  removeLast();
    }

    /**
     * Метод добовляетновый элемент на вершину стэка.
     * @param value новый элемент.
     */
    public void push(T value) {
        addLast(value);
    }
}
