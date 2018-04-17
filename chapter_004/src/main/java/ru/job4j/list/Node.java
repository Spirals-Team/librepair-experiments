package ru.job4j.list;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Node<E> {
    private E elem;
    private Node<E> next;

    public Node(E elem) {
        this.elem = elem;
    }

    public Node<E> getNext() {
        return next;
    }

    public void setNext(Node<E> next) {
        this.next = next;
    }

    public E getElem() {
        return elem;
    }
}
