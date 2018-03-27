package ru.job4j.collections.list;

/**
 * Queue.
 * @author Hincu Andrei (andreih1981@gmail.com) by 10.10.17;
 * @version $Id$
 * @since 0.1
 * @param <E> type of items.
 */
public class SimpleQueue<E> extends ContainerAsLinkedList<E> {
    /**
     * Конструктор.
     */
    public SimpleQueue() {
    }

    /**
     * Метод возвращает первый элемент и удаляет его из очереди.
     * @return первый элемент.
     */
    public E poll() {
        return removeFirst();
    }

    /**
     * Метод добовляет элемент в конец очереди.
     * @param value новый элемент.
     */
    public void push(E value) {
        addLast(value);
    }
}

