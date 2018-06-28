package ru.job4j.collections;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class SimpleStack<T> extends AbstractSimple<T> {

    public SimpleStack() {
        lstNode = new Node(null, fstNode, null);
        fstNode = new Node(null, null, lstNode);
    }

    /**
     * Метод вставляет элемент с писок, элемент записывается в голову списка, создается новая головая
     * и из старой головы ссылаемся на новую голову
     * @param value - значение, которое требуется вставить в список, вставляется в старую голову списка.
     */
    public void push(T value) {
        Node<T> next = fstNode;
        next.item = value;
        fstNode = new Node(null, null, next);
        next.prev = fstNode;
        size++;
    }
}
