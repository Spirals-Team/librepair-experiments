package ru.job4j.list;

public class SimpleQueue<E> {
    private SimpleLinkedList<E> queue = new SimpleLinkedList<>();

    public void push(E value) {
        queue.add(value);
    }

    public E poll() {
        return queue.deleteFirst();
    }

    public E get(int index) {
        return queue.get(index);
    }
}
