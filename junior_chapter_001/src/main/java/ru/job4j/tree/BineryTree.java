package ru.job4j.tree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class BineryTree<E extends Comparable<E>> implements Iterable<E> {
    private Node<E> root;
    private Node<E> temp;

    public BineryTree(E e) {
        this.root = new Node<>(e);
        this.temp = this.root;
    }

    public boolean add(E e) {
        if (e.compareTo(temp.value) <= 0) {
            if (temp.left == null) {
                temp.left = new Node<>(e);
                return true;
            } else {
                temp = temp.left;
                add(e);
            }
        } else {
            if (temp.right == null) {
                temp.right = new Node<>(e);
                return true;
            } else {
                temp = temp.right;
                add(e);
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Queue<Node<E>> data = new LinkedList<>();
            private Node<E> next;
            private boolean flag = true;

            private void findBy() {
                data.offer(root);
            }

            @Override
            public boolean hasNext() {
                if (flag) {
                    findBy();
                    flag = false;
                }
                return !data.isEmpty();
            }

            @Override
            public E next() {
                if (hasNext()) {
                    next = data.remove();
                    if (next.left != null) {
                        data.offer(next.left);
                    }
                    if (next.right != null) {
                        data.offer(next.right);
                    }

                }
                return next.getValue();
            }
        };
    }

    private class Node<E> {
        private E value;
        private Node<E> left;
        private Node<E> right;

        private Node(E value) {
            this.value = value;
        }

        public E getValue() {
            return value;
        }
    }
}
