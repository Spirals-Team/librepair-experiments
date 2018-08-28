package ru.job4j.list.cycle;

public class Node<T> {
    /**
     * Значение объекта.
     */
    private final T value;
    /**
     * Ссылка на следующий объект.
     */
    private Node<T> nextNode;

    /**
     * Конструктор объекта.
     *
     * @param value значение объекта.
     */
    public Node(T value) {
        this.value = value;
    }

    /**
     * Геттер для значение объекта.
     *
     * @return вернет значение объекта.
     */
    public T getValue() {
        return value;
    }

    /**
     * Геттер для ссылки на следующий объект.
     *
     * @return вернет ссылку на следующий объект.
     */
    public Node<T> getNextNode() {
        return nextNode;
    }

    /**
     * Сеттер задаем ссылку на следующий объект.
     *
     * @param nextNode ссылка на следующий объект.
     */
    public void setNextNode(Node<T> nextNode) {
        this.nextNode = nextNode;
    }

}
