package ru.job4j.tree;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Queue;

public class Tree<E extends Comparable<E>> implements SimpleTree<E> {

    private Node<E> root;
    private List<Node<E>> list;

    class Node<T> {
        private List<Node<T>> children;
        private T value;

        public Node(T value) {
            this.value = value;
        }
    }

    // collect all nodes in the list
    private void collectAllElements(List<Node<E>> list) {
        list.add(root);
        for (int i = 0; i < list.size(); i++) {
            if (list.get(i).children != null) {
                list.addAll(list.get(i).children);
            }
        }
    }

    @Override
    public Iterator<E> iterator() {
        list = new LinkedList<>();
        collectAllElements(list);

        return new Iterator<E>() {
            int index = 0;

            @Override
            public boolean hasNext() {
                return  index < list.size();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                return list.get(index++).value;
            }
        };
    }

    // find duplicate by value
    private Node<E> findDuplicate(E value) {
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<E> element = queue.remove();

            if (element.value.compareTo(value) == 0) {
                return element;
            }
            if (element.children != null) {
                queue.addAll(element.children);
            }
        }
        return null;
    }

    // add new node in children list
    private void addNode(Node<E> node, E value) {
        if (node.children == null) {
            node.children = new ArrayList<>();
        }
        node.children.add(new Node<>(value));
    }

    @Override
    public boolean add(E parent, E child) {
        if (root == null) {
            root = new Node<>(parent);
            addNode(root, child);
        }
        // if child not unique then return false
        if (findDuplicate(child) != null) {
            return false;
        }

        Node<E> node = findDuplicate(parent);
        // if parent not find then add child in root
        if (node == null) {
            node = root;
        }
        addNode(node, child);
        return true;
    }

    public boolean isBinary() {
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);

        while (!queue.isEmpty()) {
            Node<E> element = queue.remove();

            if (element.children == null) {
                continue;
            }
            if (element.children.size() > 2) {
                return false;
            }
            queue.addAll(element.children);
        }
        return true;
    }
}
