package ru.job4j.list;

/**
 * Коллекция Stack.
 *
 * @param <T> тип коллекции.
 */
public class Stack<T> implements SimpleStackAndQueue<T> {
    /**
     * Ссылка на текущий объект.
     */
    private Node<T> current;

    @Override
    public T poll() {
        // Присваем переменной ссылку на временный объект.
        Node<T> node = this.current;
        // Узнаем у текущего объекта ссылку на предыдущий объект. И присваиваем её объекту current.
        this.current = node.getBack();
        // Возвращаем значение текущего объекта.
        return node.getValue();
    }

    @Override
    public void push(T value) {
        // Создаем новый объект.
        Node<T> node = new Node<>(value);
        // Передаем новому объекту ссылку текущего объекта.
        node.setBack(current);
        // Присваиваем переменной текущего объекта ссылку на новый объект.
        this.current = node;
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
        private Node<T> back;

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
         * Геттер для ссылки на предшествующий объект.
         *
         * @return вернет ссылку на предшествующий объект.
         */
        public Node<T> getBack() {
            return back;
        }

        /**
         * Сеттер задаем ссылку на предшествующий объект.
         *
         * @param back ссылка на предшествующий объект.
         */
        public void setBack(Node<T> back) {
            this.back = back;
        }
    }
}
