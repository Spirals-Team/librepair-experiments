package ru.job4j.tree;

import java.util.Iterator;
import java.util.Stack;

public class BinaryTree<T> implements Iterable<T> {
    private Node<T> root;

    public Node<T> getRoot() {
        return root;
    }

    private Node<T> addRecursive(Node<T> current, T value) {
        if (current == null) {
            return new Node<T>(value);
        }

        if (current.compareTo(value) == 1) {
            current.left = addRecursive(current.left, value);
        } else if (current.compareTo(value) == -1) {
            current.right = addRecursive(current.right, value);
        } else {
            return current;
        }

        return current;
    }

    public void add(T value) {
        root = addRecursive(root, value);
    }

    public void display(Node node) {
        if (node != null) {
            display(node.left);
            System.out.print(" " + node.value);
            display(node.right);
        }
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            private Stack<Node<T>> stack = new Stack<Node<T>>();
            boolean flag = true;

            public void setStack() {
                while (root != null) {
                    stack.push(root);
                    root = root.left;
                }
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public T next() {
                if (flag) {
                    setStack();
                    flag = false;
                }
                Node<T> node = stack.pop();
                T result = node.value;
                if (node.right != null) {
                    node = node.right;
                    while (node != null) {
                        stack.push(node);
                        node = node.left;
                    }
                }
                return result;
            }
        };
    }

    private class Node<T> implements Comparable<T> {
        T value;
        Node<T> left;
        Node<T> right;

        Node(T value) {
            this.value = value;
            right = null;
            left = null;
        }

        @Override
        public int compareTo(T o) {
            return Integer.valueOf((Integer) o).compareTo((Integer) root.value);
        }
    }
}
