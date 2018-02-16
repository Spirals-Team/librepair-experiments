package ru.job4j.list;

public class SimpleStack<E> {
    private SimpleLinkedList<E> stack = new SimpleLinkedList<>();

    public void push(E value) {
        stack.add(value);
    }

    public E pop() {
        return stack.deleteLast();
    }

    public E get(int index) {
        return stack.get(index);
    }
}
