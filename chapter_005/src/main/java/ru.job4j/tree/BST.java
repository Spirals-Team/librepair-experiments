package ru.job4j.tree;

import java.util.Comparator;

//Создайте BST - binary search tree (двоичное дерево поиска).
//        1) добавьте метод add(E e);
//        Помните
//        1. Корень имеет только два дочерних элемента: left и right. Каждый из этих узлов в свою очередь,
// может быть корнем для своих левых и правых поддеревьев.
//        2. Элемент слева от корня, меньше либо равен корню, а правый больше корня.
//        3. Добавление элемента постарайтесь реализовать рекурсивно.
public class BST<E extends Comparable<E>> {

    private int size = 0;

    private Node<E> root;

    public Node<E> getRoot() {
        return root;
    }

    private Comparator comparator;

    public BST(Comparator comparator) {
        this.comparator = comparator;
    }

    public BST() {
        this.comparator = null;
    }

    public static class Node<E> {
        E value;
        Node<E> left, right;

        public Node(E value) {
            this.value = value;
        }
    }

    public void add(E e) {
        root = add(e, root);
    }

    private Node<E> add(E e, Node<E> node) {

        if (node != null) {
            if (e == null) {
                node.right = add(e, node.right);
            } else {
                if (node.value == null) {
                    node = new Node<>(e);
                    node.right = add(null, node.right);
                }
                Comparator cmp = comparator;
                if (cmp == null) {
                    if (e.compareTo(node.value) < 0) {
                        node.left = add(e, node.left);
                    }
                    if (e.compareTo(node.value) > 0) {
                        node.right = add(e, node.right);
                    }
                } else {
                    if (cmp.compare(e, node.value) < 0) {
                        node.left = add(e, node.left);
                    }
                    if (cmp.compare(e, node.value) > 0) {
                        node.right = add(e, node.right);
                    }
                }
            }
        } else {
            node = new Node<E>(e);
            size++;
        }

        return node;
    }
}