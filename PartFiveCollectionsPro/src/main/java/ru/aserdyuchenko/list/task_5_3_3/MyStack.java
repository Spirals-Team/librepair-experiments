package ru.aserdyuchenko.list.task_5_3_3;

import ru.aserdyuchenko.list.task_5_3_2.MyOwnLinkedList;

/**
 * @author anton.
 * @param <E> - e.
 */
public class MyStack<E> extends MyOwnLinkedList<E> {
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
        int index = list.getCounter() - 1;
        E e = (E) list.get(index);
        list.remove(index);
        return e;
    }
}
