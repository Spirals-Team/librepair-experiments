package ru.job4j.list;

import ru.job4j.set.LinkedContainer;

/**
 * Class SimpleStack.
 * @param <T> generic
 */
public class SimpleStack<T> implements SimpleStructure<T> {
    /**
     * @param lc container for saving element.
     */
    private LinkedContainer<T> lc = new LinkedContainer<>();

    /**
     * Method poll.
     * @return element.
     */
    @Override
    public T poll() {
        T result = lc.get(lc.getLast());
        lc.deleteLast();
        return result;
    }

    /**
     * Method push.
     * @param value element for pushing.
     */
    @Override
    public void push(T value) {
        lc.add(value);
    }
}
