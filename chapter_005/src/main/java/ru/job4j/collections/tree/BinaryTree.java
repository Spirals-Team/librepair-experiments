package ru.job4j.collections.tree;

/**
 * Бинарное дерево .
 *
 * @author Hincu Andrei (andreih1981@gmail.com) by 20.10.17;
 * @version $Id$
 * @since 0.1
 * @param <E> тип данных.
 */
public class BinaryTree<E extends Comparable<E>> extends Tree<E> {
    /**
     * Корень дерева.
     */
    private Node<E> node;
    /**
     * Размер дерева.
     */
    private int size;
    /**
     * Узел дерева.
     * @param <E> значение.
     */
    private class Node<E> {
        /**
         * Значение.
         */
        private E value;
        /**
         * левый сын.
         */
        private Node<E> left;
        /**
         * правый сын.
         */
        private Node<E> right;

        /**
         * Конструктор.
         * @param value значение узла.
         */
        private Node(E value) {
            this.value = value;
        }
    }

    /**
     * Добавляем новый элемент или корень дерева.
     * @param e значение.
     */
    public void add(E e) {
        if (node == null) {
            node = new Node<>(e);
            size++;
        } else {
            addNewElement(e, node);
        }
    }

    /**
     * Метод для поиска места вставки.
     * @param e значение.
     * @param n текуший узел дерева.
     */
    private void addNewElement(E e, Node<E> n) {
        if (e.compareTo(n.value) < 0) {
            if (n.left == null) {
                n.left = new Node<>(e);
                size++;
            } else {
                addNewElement(e, n.left);
            }
        } else if (e.compareTo(n.value) > 0) {
            if (n.right == null) {
                n.right = new Node<>(e);
                size++;
            } else {
                addNewElement(e, n.right);
            }
        }

    }

    /**
     * геттер.
     * @return размер дерева.
     */
    public int getSize() {
        return size;
    }
}
