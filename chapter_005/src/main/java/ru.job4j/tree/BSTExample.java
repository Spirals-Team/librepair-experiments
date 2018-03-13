package ru.job4j.tree;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * BST
 * @author gtkesh
 */
public class BSTExample<T extends Comparable<T>> {

    private Node<T> root;
    private int size = 0;

    /**
     * Adds a data entry to the BST
     * <p>
     * null is positive infinity
     *
     * @param data The data entry to add
     */
    public void add(T data) {
        root = add(data, root);
    }

    /**
     * Recursive add() method helper
     */
    private Node<T> add(T data, Node<T> node) {
        if (node != null) {
            if (data == null) {
                node.right = add(data, node.right);
            } else {
                if (node.getData() == null) {
                    node = new Node<T>(data);
                    node.right = add(null, node.right);
                }
                if (data.compareTo(node.data) < 0) {
                    node.left = add(data, node.left);
                }
                if (data.compareTo(node.data) > 0) {
                    node.right = add(data, node.right);
                }
            }
        } else {
            node = new Node<T>(data);
            size++;
        }

        return node;
    }

    public static class Node<K extends Comparable<K>> {

        private K data;
        private Node<K> left, right;

        public Node(K data) {
            setData(data);
        }

        public K getData() {
            return data;
        }

        public void setData(K data) {
            this.data = data;
        }

        public Node<K> getLeft() {
            return left;
        }

        public void setLeft(Node<K> left) {
            this.left = left;
        }

        public Node<K> getRight() {
            return right;
        }

        public void setRight(Node<K> right) {
            this.right = right;
        }

        public String toString() {
            return "" + data;
        }
    }

}
