package ru.job4j.collections;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class BinaryNode<T extends Comparable> implements Comparable<T> {
    private BinaryNode<T> parent;
    private BinaryNode<T> right;
    private BinaryNode<T> left;

    private T element;

    public BinaryNode(T element) {
        this.element = element;
    }

    public BinaryNode<T> getParent() {
        return parent;
    }

    public void setParent(BinaryNode<T> parent) {
        this.parent = parent;
    }

    public BinaryNode<T> getLeft() {
        return left;
    }

    public BinaryNode<T> getRight() {
        return right;
    }

    public void setLeft(BinaryNode<T> left) {
        this.left = left;
    }

    public void setRight(BinaryNode<T> right) {
        this.right = right;
    }

    public T getElement() {
        return element;
    }

    public void setElement(T element) {
        this.element = element;
    }

    @Override
    public int compareTo(T o) {
        return this.hashCode() - o.hashCode();
    }
}
