package ru.aserdyuchenko.tree.task_4;

import java.util.Iterator;
import java.util.Stack;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class Tree<E extends Comparable<E>> {
    private Node<E> root;

    public boolean breadthFirstSearch() {
        try {
            Node<E> node = root;
            while (node.left != null) {
                Node<E> temp = node.left;
                node.left = node.right;
                node.right = temp;
                node = node.left;
            }

            node = root.right;
            while (node.right != null) {
                Node<E> temp = node.left;
                node.left = node.right;
                node.right = temp;
                node = node.right;
            }

            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean depthFirstSearch() {
        try {
            depthFirstSearch(root);
            depthFirstSearch(root.left);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    private void depthFirstSearch(Node<E> node) {
        if (node.left != null) depthFirstSearch(node.left);
        Node<E> temp = node.left;
        node.left = node.right;
        node.right = temp;
    }

    public boolean add(E e) {
        try {
            Node<E> node = new Node<E>(e);
            if (root == null) {
                root = node;
                return true;
            } else insertRec(root, node);
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private void insertRec(Node<E> latestRoot, Node<E> node) {
        if (latestRoot.value.compareTo(node.value) > 0) {
            if (latestRoot.left == null) {
                latestRoot.left = node;
            } else {
                insertRec(latestRoot.left, node);
            }
        } else {
            if (latestRoot.right == null) {
                latestRoot.right = node;
            } else {
                insertRec(latestRoot.right, node);
            }
        }
    }

    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Stack<Node<E>> stack = new Stack<>();
            private Node<E> current = root;

            @Override
            public boolean hasNext() {
                return (!stack.isEmpty() || current != null);
            }

            @Override
            public E next() {
                while (current != null) {
                    stack.push(current);
                    current = current.left;
                }

                current = stack.pop();
                Node<E> node = current;
                current = current.right;

                return node.value;
            }
        };
    }

    class Node<E> {
        E value;
        Node<E> left, right;

        /**
         * Constructor.
         * @param e - new value.
         */
        Node(E e) {
            this.value = e;
            left = right = null;
        }
    }
}
