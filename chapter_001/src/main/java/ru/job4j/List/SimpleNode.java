package ru.job4j.List;

/**
 * Класс описывает узел для проверки цикличности
 * @param <T> возможный класс
 */
class SimpleNode<T> {
    T value;
    int check = 0;
    SimpleNode<T> next;

    SimpleNode (T value) {
        this.value = value;
    }
}
