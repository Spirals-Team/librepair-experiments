package ru.job4j.collections;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleQueue<T> extends AbstractSimple<T> {

    public SimpleQueue() {
        lstNode = new Node(null, fstNode, null);
        fstNode = new Node(null, null, lstNode);
    }

    /**
     * Метод вставляет в конец списка новый элемент, в хвост.айтем вставлем новый элемент, создаем новый хвост и из старого хвоста
     * ссылаемся на новый хвост
     * @param value - значение которое требуется вставить в список, вставляется в старый хвост.
     */
    public void push(T value) {
        Node<T> prev = lstNode;
        prev.item = value;
        lstNode = new Node(null, prev, null);
        prev.next = lstNode;
        size++;
    }
}
