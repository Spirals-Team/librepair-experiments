package ru.job4j.tree.bst;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

public class Bst<E extends Comparable<E>> implements Iterable<E> {

    private BstNode<E> root;
    private Stack<BstNode<E>> stack;
    private int count = 0;

    public Bst(E root) {
        this.root = new BstNode<>(root);
        count++;
    }

    /**
     * Add a new node.
     * A new node is always inserted at leaf.
     *
     * @param value value with which the specified node will be associated
     * @return {@code true} if value presented
     * or {@code false} in a case if value absent
     */
    public boolean add(E value) {
        return add(this.root, value, this.root.getParent());
    }

    private boolean add(BstNode<E> actual, E value, BstNode<E> parent) {
        if (actual == null) {
            return attachNode(value, parent);
        }

        if (actual.compareTo(value) > 0) {
            return add(actual.getLeft(), value, actual);
        } else {
            return add(actual.getRight(), value, actual);
        }
    }

    private boolean attachNode(E value, BstNode<E> parent) {
        BstNode<E> tmpNode = new BstNode<>(value);
        tmpNode.setParent(parent);

        if (parent.compareTo(value) > 0) {
            parent.setLeft(tmpNode);
        } else {
            parent.setRight(tmpNode);
        }
        count++;
        return true;
    }

    /**
     * Returns the node to which the specified value.
     *
     * A return value of {@code null} indicate that the tree
     * contains no nodes for the value
     *
     * @param value to search in this tree
     * @return the node for the given value or {@code null} if the tree
     * does not contain an node for the value
     */
    public BstNode<E> search(E value) {
        BstNode<E> actual = this.root;

        while (actual != null) {
            if (actual.eqValue(value)) {
                return actual;
            }
            if (actual.compareTo(value) > 0) {
                actual = actual.getLeft();
            } else {
                actual = actual.getRight();
            }
        }
        return null;
    }

    @Override
    public Iterator<E> iterator() {
        stack = new Stack<>();
        pushAll(root);

        return new Iterator<E>() {

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public E next() {
                if (stack.isEmpty()) {
                    throw new NoSuchElementException();
                }
                BstNode<E> tmp = stack.pop();
                pushAll(tmp.getRight());
                return tmp.getValue();
            }
        };
    }

    private void pushAll(BstNode<E> node) {
        while (node != null) {
            stack.push(node);
            node = node.getLeft();
        }
    }

    /**
     * Returns the number of nodes in this tree.
     *
     * @return the number of nodes in this tree
     */
    public int size() {
        return count;
    }
}
