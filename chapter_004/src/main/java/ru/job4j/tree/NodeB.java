package ru.job4j.tree;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class NodeB<E extends Comparable<E>> {
    private E value;
    private NodeB<E> leftChild;
    private NodeB<E> rightChild;

    public NodeB(E value) {
        this.value = value;
    }

    public void setLeftChild(NodeB<E> leftChild) {
        this.leftChild = leftChild;
    }

    public void setRightChild(NodeB<E> rightChild) {
        this.rightChild = rightChild;
    }

    public E getValue() {
        return value;
    }

    public NodeB<E> getLeftChild() {

        return leftChild;
    }

    public NodeB<E> getRightChild() {
        return rightChild;
    }
}
