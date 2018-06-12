package ru.job4j.list;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class SimpleStack<T> {
    private NodeList<T> list = new NodeList<>();

    public T poll() {
        return list.deleteFirst();
    }

    public void push(T value) {
        list.addFirst(value);
    }
}
