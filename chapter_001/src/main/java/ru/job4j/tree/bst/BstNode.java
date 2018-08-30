package ru.job4j.tree.bst;

public class BstNode<E extends Comparable<E>> {

    private E value;
    private BstNode<E> parent;
    private BstNode<E> left;
    private BstNode<E> right;

    public BstNode(final E value) {
        this.value = value;
        this.parent = null;
        this.left = null;
        this.right = null;
    }

    public int compareTo(E that) {
        return value.compareTo(that);
    }

    public boolean eqValue(E that) {
        return this.value.equals(that);
    }

    public boolean isLeaf() {
        return (this.left == null) && (this.right == null);
    }

    public E getValue() {
        return value;
    }

    public void setValue(E value) {
        this.value = value;
    }

    public BstNode<E> getParent() {
        return parent;
    }

    public void setParent(BstNode<E> parent) {
        this.parent = parent;
    }

    public BstNode<E> getLeft() {
        return left;
    }

    public void setLeft(BstNode<E> left) {
        this.left = left;
    }

    public BstNode<E> getRight() {
        return right;
    }

    public void setRight(BstNode<E> right) {
        this.right = right;
    }
}
