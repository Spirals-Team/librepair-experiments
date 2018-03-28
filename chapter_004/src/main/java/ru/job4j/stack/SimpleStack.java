package ru.job4j.stack;

import ru.job4j.list.List;

public class SimpleStack<T> {
    private List<T> stack = new List<>();
    private int size = 0;

    public T poll() {
        return stack.get(--size);
    }

    public void push(T value) {
        stack.add(value);
        size++;
    }
}
