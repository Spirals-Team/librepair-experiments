package ru.job4j.collections.tree;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

/**
 *Дерево.
 * @author Hincu Andrei (andreih1981@gmail.com) by 18.10.17;
 * @version $Id$
 * @since 0.1
 * @param <E> тип данных.
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    /**
     * Корень дерева.
     */
    private Node<E> node;
    /**
     * Размер дерева.
     */
    private int size;

    /**
     * Вложенный класс.
     * @param <E> тип данных.
     */
    private class Node<E> {
        /**
         * Значение узла.
         */
        private E value;
        /**
         * Список детей от данного узла.
         */
        private List<Node<E>> children;

        /**
         * Конструктор.
         * @param value значение.
         */
        private Node(E value) {
            this.value = value;
        }

        /**
         * Добавление первого потомка.
         * @param children первый ребёнок.
         */
        private void addFirstChild(E children) {
            this.children = new ArrayList<>();
            this.children.add(new Node<E>(children));
        }
    }

    /**
     * Метод добовляет новую пару или существующему родителю нового ребенка.
     * @param parent parent. родитель.
     * @param child child.
     * @return удачно или тпкой ребенок уже есть у однолго из родителей.
     */
    @Override
    public boolean add(E parent, E child) {
        boolean success = false;
        if (parent.compareTo(child) != 0) {
            if (this.node == null) {
                this.node = new Node<>(parent);
                size++;
                this.node.addFirstChild(child);
                success = true;
                size++;
            } else {
                if (!contains(node, parent, child) && nodeParent != null) {
                    if (nodeParent.children == null) {
                        nodeParent.addFirstChild(child);
                    } else {
                        nodeParent.children.add(new Node<>(child));
                    }
                    success = true;
                }
                if (success) {
                    size++;
                }
            }
        }
        nodeParent = null;
        return success;
    }
    /**
     * Поле для сохранения значения родителя которому собираются добавить ребенка.
     * после добавления переменаая обнуляется.
     */
    private Node<E> nodeParent;

    /**
     * Метод проверяет дерево на наличие дубликата попутно находим родителя.
     * @param n корень дерева.
     * @param parent счастливый родитель.
     * @param child добовляемый ребёнок.
     * @return если ребёнок уникален то возврат false дубликатов нет.
     */
    private boolean contains(Node<E> n, E parent, E child) {
        boolean found = false;
        if (n.value.compareTo(child) == 0) {
            found = true;
        }
        if (n.value.compareTo(parent) == 0) {
            nodeParent = n;
        }
        if (!found && n.children != null) {
            for (Node<E> node : n.children) {
                found = contains(node, parent, child);
            }
        }
        return found;
    }

    /**
     * Итератор для перебора дерева.
     * @return значение узла дерева.
     */
    @Override
    public Iterator<E> iterator() {
        /**
         * локальная переменная для хранения корня дерева.
         */
        Node<E> n = node;
        return new Iterator<E>() {
            /**
             * флаг для проверки начала итерации.
             */
            private boolean flag = false;
            /**
             * Очередь для добавления и взятия элементов.
             */
           private Queue<Node<E>> queue = new ArrayDeque<>();
            /**
             * позиция в дереве.
             */
            private int position = 0;
            /**
             * Узел дерева.
             */
            private Node<E> val;

            /**
             * Метод проверяет есть ли еще элементы.
             * @return да или нет.
             */
            @Override
            public boolean hasNext() {
                return position < size;
            }

            /**
             * возвращаем текущий элемент и передвигаемся к следующему.
             * @return элемент.
             */
            @Override
            public E next() {
                if (hasNext()) {
                    if (!flag) {
                        flag = true;
                        if (n.children != null) {
                            queue.addAll(n.children);
                        }
                        val = n;
                    } else {
                        val = queue.poll();
                        if (val.children != null) {
                            queue.addAll(val.children);
                        }
                    }
                } else {
                    throw new NoSuchElementException();
                }
                position++;
                return val.value;
            }
        };
    }

    /**
     * Метод проверяет бинарночть дерева.
     * @return true ели бинарное or false если у узла больше двух потомков.
     */
    public boolean isBinary() {
        boolean binar = true;
        Node<E> knot;
        Queue<Node<E>> queue = new ArrayDeque<>();
        if (node != null) {
            queue.add(node);
        }
        while (!queue.isEmpty()) {
            knot = queue.poll();
            if (knot.children != null) {
                if (knot.children.size() <= 2) {
                    queue.addAll(knot.children);
                } else {
                    binar = false;
                    break;
                }
            }
        }
        return binar;
    }
}
