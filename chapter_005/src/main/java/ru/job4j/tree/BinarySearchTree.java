package ru.job4j.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

public class BinarySearchTree<E extends Comparable<E>> implements Iterable<E> {

    private Node<E> root;

    class Node<T> {
        private Node<T> left;
        private Node<T> right;
        private T value;

        public Node(T value) {
            this.value = value;
        }
    }

    // collect all nodes in the list
    private List<Node<E>> collectAllElements() {
        List<Node<E>> list = new ArrayList<>();
        list.add(root);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).left != null) {
                list.add(list.get(i).left);
            }
            if (list.get(i).right != null) {
                list.add(list.get(i).right);
            }
        }
        return list;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            List<Node<E>> list = collectAllElements();
            int index = 0;

            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return list.get(index++).value;
            }
        };
    }

    // add new node
    private boolean addNode(Node<E> node, E value) {
        if (value != null) {
            if (value.compareTo(node.value) > 0) {
                if (node.right == null) {
                    node.right = new Node<>(value);
                    return true;
                }
                this.addNode(node.right, value);
            } else {
                if (node.left == null) {
                    node.left = new Node<>(value);
                    return true;
                }
                this.addNode(node.left, value);
            }
        }
        return false;
    }

    // add new element in tree
    public boolean add(E value) {
        if (root == null) {
            root = new Node<>(value);
            return true;
        }
        return addNode(root, value);
    }
}
