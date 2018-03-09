package ru.aserdyuchenko.tree.task_3;

import ru.aserdyuchenko.tree.task_1.SimpleTree;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author Anton Serdyuchenko. anton415@gmail.com
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;

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

    @Override
    public boolean add(E parent, E child) {
        try {
            Node<E> node = new Node<E>(parent);
            if (root == null) {
                root = node;
                root.childen.add(new Node<E>(child));
                return true;
            } else insertRec(root, node, new Node<E>(child));
            return true;
        } catch (Exception exception) {
            return false;
        }
    }

    private void insertRec(Node<E> latestRoot, Node<E> parent, Node<E> child) {
        if (latestRoot.value.compareTo(parent.value) > 0) {
            if (latestRoot.left == null) {
                latestRoot.left = parent;
                latestRoot.left.childen.add(child);
            } else {
                insertRec(latestRoot.left, parent);
            }
        } else {
            if (latestRoot.right == null) {
                latestRoot.right = parent;
                latestRoot.right.childen.add(child);
            } else {
                insertRec(latestRoot.right, parent);
            }
        }
    }

    @Override
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
        List<Node<E>> childen;

        /**
         * Constructor.
         * @param e - new value.
         */
        Node(E e) {
            this.value = e;
            left = right = null;
            childen = new LinkedList<>();
        }

        /**
         * Get value.
         * @return - parent.
         */
        public E getValue() {
            return this.value;
        }
    }
}
