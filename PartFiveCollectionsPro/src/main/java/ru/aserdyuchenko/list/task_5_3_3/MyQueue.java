package ru.aserdyuchenko.list.task_5_3_3;

import ru.aserdyuchenko.list.task_5_3_2.MyOwnLinkedList;

/**
 * @author anton.
 * @param <E> - e.
 */
public class MyQueue<E> extends MyOwnLinkedList {
    /**
     * @param list - list.
     */
    private MyOwnLinkedList list = new MyOwnLinkedList();

    /**
     * Push.
     * @param e - new element.
     */
    public void push(E e) {
        list.add(e);
    }

    /**
     * Pop.
     * @return element.
     */
    public E pop() {
        E e = (E) list.get(0);
        list.remove(0);
        return e;
    }
}
