package ru.skorikov;

import java.util.NoSuchElementException;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <E> параметр.
 * @ author: Alex_Skorikov.
 * @ date: 27.11.17
 * @ version: java_kurs_standart
 * 3. Собственная реализация Binary search tree
 */
public class BinarySearchTree<E> implements Comparable<E> {
    /**
     * Корневой элемент.
     */
    private Node root;
    /**
     * Следующий элемент.
     */
    private Node newNode;
    /**
     * Искомый узел.
     */
    private Node searchNode;
    /**
     * Максимальный элемент.
     */
    private E maxNode;
    /**
     * Минимальный элемент.
     */
    private E minNode;

    /**
     * Будем сравнивать хэш-коды текущего элемента и сравниваемого.
     *
     * @param value данные сравниваемого элемента.
     * @return 0 - равны, -1 - меньше, 1 - больше.
     */
    @Override
    public int compareTo(E value) {
        int compar = 0;
        if (value.hashCode() != newNode.value.hashCode()) {
            if (value.hashCode() < newNode.value.hashCode()) {
                compar = -1;
            } else {
                compar = 1;
            }
        }
        return compar;
    }

    /**
     * Узел.
     * Строительный блок дерева.
     */
    public class Node {
        /**
         * Данные.
         */
        private E value;
        /**
         * Левая ветка.
         */
        private Node left;
        /**
         * Правая ветка.
         */
        private Node right;

        /**
         * Конструктор принимет данные.
         * Ключ вычисляем по хэшкоду.
         *
         * @param value данные.
         */
        Node(E value) {
            this.value = value;
            this.right = null;
            this.left = null;
        }

        /**
         * Получить данные узла.
         * @return данные.
         */
        public E getValue() {
            return value;
        }
    }

    /**
     * Конструктор дерева.
     */
    public BinarySearchTree() {
        this.root = null;
    }

    /**
     * Получить корень.
     *
     * @return корень.
     */
    public Node getRoot() {
        return root;
    }

    /**
     * Добавить узел в дерево.
     *
     * @param value данные.
     */
    public void add(E value) {
        if (root == null) {
            root = new Node(value);
            newNode = root;
        } else {
            if (compareTo(value) >= 0) {
                if (newNode.right == null) {
                    newNode.right = new Node(value);
                } else {
                    newNode = newNode.right;
                    add(value);
                }
            } else {
                if (newNode.left == null) {
                    newNode.left = new Node(value);
                } else {
                    newNode = newNode.left;
                    add(value);
                }
            }
        }
        newNode = root;
    }

    /**
     * Получить узел по значению.
     *
     * @param value значение.
     * @return узел.
     */
    public Node getNode(E value) {
        if (newNode != null) {
            if (compareTo(value) == 0) {
                searchNode = newNode;
            } else {
                if (compareTo(value) > 0) {
                    newNode = newNode.right;
                    getNode(value);
                } else {
                    newNode = newNode.left;
                    getNode(value);
                }
            }
        }
        if (searchNode != null) {
            return searchNode;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Поиск макимума.
     *
     * @param node стартовый узел.
     * @return максимум.
     */
    public E maxNode(Node node) {

        if (node != null) {
            if (node.right == null) {
                maxNode = node.value;
            } else {
                maxNode(node.right);
            }
        }
        if (maxNode != null) {
            return maxNode;
        } else {
            throw new NoSuchElementException();
        }
    }

    /**
     * Поиск минимума.
     *
     * @param node стартовый узел.
     * @return минимум.
     */
    public E minNode(Node node) {
        if (node != null) {
            if (node.left == null) {
                minNode = node.value;
            } else {
                minNode(node.left);
            }
        }
        if (minNode != null) {
            return minNode;
        } else {
            throw new NoSuchElementException();
        }
    }
}