package ru.job4j.tree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Stack;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class BinarySearchTree<E extends Comparable<E>> implements Iterable<E> {
    private NodeB<E> root;
    private int elements = 0;

    private NodeB<E> insert(NodeB<E> node, E e) {
        if (node == null) {
            return new NodeB<>(e);
        }
        if (e.compareTo(node.getValue()) <= 0) {
            node.setLeftChild(insert(node.getLeftChild(), e));
        } else if (e.compareTo(node.getValue()) > 0) {
            node.setRightChild(insert(node.getRightChild(), e));
        }
        return node;
    }

    public void insert(E e) {
        root = insert(root, e);
        elements++;
    }

    @Override
    public Iterator<E> iterator() {
        return new BinarySearchTreeIterator(root);
    }

    private class BinarySearchTreeIterator implements Iterator<E> {
        private int expectedModCount = elements;
        private Stack<NodeB<E>> stack;

        BinarySearchTreeIterator(NodeB<E> root) {
            stack = new Stack<>();
            while (root != null) {
                stack.push(root);
                root = root.getLeftChild();
            }
        }

        @Override
        public boolean hasNext() {
            checkModCount();
            return !stack.isEmpty();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            NodeB<E> node = stack.pop();
            E result = node.getValue();
            if (node.getRightChild() != null) {
                node = node.getRightChild();
                while (node != null) {
                    stack.push(node);
                    node = node.getLeftChild();
                }
            }
            return result;
        }

        private void checkModCount() {
            if (expectedModCount != elements) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
