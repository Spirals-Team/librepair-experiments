package ru.job4j.queue;

import ru.job4j.list.List;

public class SimpleQueue<T> {
    private List<T> queue = new List<>();
    private int size = 0;
    private int position = 0;

    public void add(T element) {
        queue.add(element);
        size++;
    }

    public T poll() {
        return queue.get(position++);
    }

}
