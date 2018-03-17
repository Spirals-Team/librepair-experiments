package ru.skorikov;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 *
 * @param <E> параметр.
 *            1. Создать элементарную структуру дерева
 * @ author: Alex_Skorikov.
 * @ date: 10.11.17
 * @ version: java_kurs_standart
 */

class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    /**
     * Корневой узел.
     */
    private Node<E> root;
    /**
     * Узел поиска.
     */
    private Node<E> returnNode = null;
    /**
     * Лист элементов для итератора.
     */
    private List<E> iteratorList = new ArrayList<>();
    /**
     * Счетчик для итератора.
     */
    private int index = 0;
    /**
     * Есть ли в коллекции дубликаты.
     */
    private boolean isDublicate = false;

    /**
     * Класс узел - строительный блок коллекции.
     *
     * @param <E> параметр.
     */
    class Node<E> {
        /**
         * Список дочерних узлов.
         */
        private List<Node<E>> childen;
        /**
         * Данные.
         */
        private E value;

        /**
         * Конструктор.
         *
         * @param value данные.
         */
        Node(E value) {
            this.value = value;
            this.childen = new ArrayList<Node<E>>();
        }
    }

    /**
     * Конструктор.
     */
    Tree() {
        this.root = new Node<>(null);
    }

    /**
     * Добавить новый узел.
     *
     * @param parent parent.
     * @param child  child.
     * @return true - добавлен.
     */
    @Override
    public boolean add(E parent, E child) {

        boolean isAdded = false;

        if (root.value == null) {
            root.value = parent;
            root.childen.add(new Node<>(child));
            isAdded = true;
        } else {
            if (root.value.equals(parent)) {
                if (!searchDublicate(root, child)) {
                    root.childen.add(new Node<>(child));
                }
            } else {
                Node<E> node = searchNode(root, parent);
                if (!searchDublicate(root, child)) {
                    node.childen.add(new Node<>(child));
                    isAdded = true;
                }
            }
        }
        isDublicate = false;
        return isAdded;
    }

    /**
     * Поиск родительского узла.
     *
     * @param root   корневой узел.
     * @param parent искомый узел.
     * @return найденный узел.
     */
    public Node<E> searchNode(Node<E> root, E parent) {
        List<Node<E>> list = root.childen;

        for (Node<E> node : list) {
            if (compare(node, parent) == 0) {
                returnNode = node;
                break;
            } else {
                searchNode(node, parent);
            }
        }
        return returnNode;
    }

    /**
     * Поиск дубликата.
     *
     * @param node узел.
     * @param data данные.
     * @return true - дубликат.
     */

    public boolean searchDublicate(Node<E> node, E data) {
        List<Node<E>> list = node.childen;
        for (Node<E> nodes : list) {
            if (compare(nodes, data) == 0 || compare(node, data) == 0) {
                isDublicate = true;
                break;
            } else {
                searchDublicate(nodes, data);
            }
        }
        return isDublicate;
    }

    /**
     * Создание листа для итератора.
     *
     * @param root корневой узел.
     */
    public void createList(Node<E> root) {
        for (Node<E> data : root.childen) {
            if (data.childen == null) {
                iteratorList.add(data.value);
            } else {
                iteratorList.add(data.value);
                createList(data);
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        createList(root);

        return new Iterator<E>() {

            @Override
            public boolean hasNext() {
                return index < iteratorList.size();
            }

            @Override
            public E next() {
                return iteratorList.get(index++);

            }
        };
    }

    /**
     * Сравнение.
     *
     * @param node   узел существующий.
     * @param parent данные искомого узла.
     * @return 0 - узел найден, -1  не найден.
     */
    public int compare(Node<E> node, E parent) {
        int isCompare = -1;
        if (node.value.equals(parent)) {
            isCompare = 0;
        }
        return isCompare;
    }
}
