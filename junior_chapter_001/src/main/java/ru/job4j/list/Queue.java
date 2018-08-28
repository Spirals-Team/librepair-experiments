package ru.job4j.list;

/**
 * Коллекция Queue.
 * @param <T> тип коллекции.
 */
public class Queue<T> implements SimpleStackAndQueue<T> {
    /**
     * Первый элемент.
     */
    private Node<T> firstNode;
    /**
     * Следующий элемент.
     */
    private Node<T> next;

    @Override
    public T poll() {
        // Получаем ссылку на первый нод.
        Node<T> node = this.firstNode;
        // Передаем firstNode информацию о следющем ноде.
        this.firstNode = node.getNextNode();
        // Вернем значение объета.
        return (T) node.getValue();
    }

    @Override
    public void push(T value) {
        // Создаем нод.
        Node<T> node = new Node<>(value);
        // Добавляем ссылку на первый элемент.
        if (this.firstNode == null) {
            this.firstNode = node;
        }
        // Если ссылка на новый нод уже есть то даем занать ноду о следующем ноде.
        if (this.next != null) {
            this.next.setNextNode(node);
        }
        // Добавляем ссылку в следующий элемент.
        this.next = node;
    }

    /**
     * Объект Node.
     *
     * @param <T> тип объекта.
     */
    private class Node<T> {
        /**
         * Значение объекта.
         */
        private final T value;
        /**
         * Ссылка на следующий объект.
         */
        private Node<T> nextNode;

        /**
         * Коснтруктор объекта.
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
}
