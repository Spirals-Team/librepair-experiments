package ru.job4j.tree;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * Класс реализующий бинарное дерево поиска
 * @param <E> класс, объекты которого можно сравнивать друг с другом
 */
public class BiTree <E extends Comparable<E>> implements Iterable<E> {
    private Node<E> root = null;
    private int modCount = 0;

    /**
     * Конструктор, создающий корень дерева
     * @param root значение корня
     */
    public BiTree(E root) {
        this.root = new Node<>(root);
    }

    /**
     * Внутренний класс, описывающий узел бинарного дерева
     * @param <E> класс, объекты которого можно сравнивать друг с другом
     */
    private static class Node<E extends Comparable<E>> {
        final E value;
        Node<E> left = null;
        Node<E> right = null;

        public Node(E val) {
            value = val;
        }
    }

    /**
     * Метод добавляет узел в бинарное дерево
     * @param value значение узла
     */
    public void add(E value) {
        addNode(root, value);
        modCount++;
    }

    /**
     * Метод пытается добавить новый child в заданный узел
     * @param node заданный узел
     * @param value значение нового узла
     */
    private void addNode(Node<E> node, E value) {
        if (node != null && value.compareTo(node.value) > 0) {
            if (node.right == null) {
                node.right = new Node(value);
            } else {
                addNode(node.right, value);
            }
        } else {
            if (node.left == null) {
                node.left = new Node(value);
            } else {
                addNode(node.left, value);
            }
        }
    }

    /**
     * Метод ищет заданное значение в бинарном дереве
     * @param value заданное значение
     * @return найдено значение или нет
     */
    public boolean hasThisValue(E value) {
        return hasValue(root, value);
    }

    /**
     * Метод сравнивает заданое значение с текущим узлом
     * @param node текущий узел
     * @param value заданное значение
     * @return результат сравнения
     */
    private boolean hasValue(Node<E> node, E value) {
        boolean result = true;
        if (node != null && value.compareTo(node.value) != 0 ) {
            result = value.compareTo(node.value) > 0 ? hasValue(node.right, value) : hasValue(node.left, value);
        }
        return node == null? false : result;
    }

    @Override
    /**
     * Переопределяем итератор
     */
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            int position = 0;
            // список значений элементов в дереве
            LinkedList<E> data = new LinkedList<>();
            int expectedModCount = modCount;
            {
                // переделываем дерево в список
                reBuild(root);
            }

            /**
             * Метод разбирает узел и его потомков в список
             * @param uzel
             */
            private void reBuild(Node<E> uzel) {
                data.add(uzel.value);
                if (uzel.left != null) {
                    reBuild(uzel.left);
                }
                if (uzel.right != null) {
                    reBuild(uzel.right);
                }
            }

            @Override
            public boolean hasNext() {
                if (expectedModCount != modCount) {
                    throw new ConcurrentModificationException();
                }
                return position < data.size();
            }

            @Override
            public E next() {
                if (! hasNext()) {
                    throw new NoSuchElementException();
                }
                return data.get(position++);
            }
        };
    }
}
