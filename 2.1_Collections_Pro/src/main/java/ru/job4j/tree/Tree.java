package ru.job4j.tree;


import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 * Class Tree.
 * @param <E> generic
 */
class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    /**
     * @param root root in tree
     */
    private Node<E> root;
    /**
     * @param size size of tree
     */
    private int size = 0;

    /**
     * Constructor.
     * @param first value for rott;
     */
    Tree(E first) {
        this.root = new Node<>(first);
    }

    /**
     * Method add.
     * @param parent parent.
     * @param child  child.
     * @return result
     */
    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        Node<E> par = check(parent);
        Node<E> chil = check(child);

        if (par != null && chil == null) {
            par.children.add(new Node<>(child));
            result = true;
            size++;
        }
        return result;
    }

    /**
     * Static nested class Node.
     * @param <E> generic
     */
    class Node<E> {
        /**
         * @param children list for childrens
         */
        private List<Node<E>> children;
        /**
         * @param value value
         */
        private E value;

        /**
         * Constructor.
         * @param value value
         */
        Node(E value) {
            this.value = value;
            children = new ArrayList<>();
        }
    }

    /**
     * Private method check. Checking what is value in tree.
     * @param check value for checking.
     * @return Node
     */
    private Node<E> check(E check) {
        Node<E> result = null;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<E> temp = queue.remove();
            if (temp.value.equals(check)) {
                result = temp;
            }
            if (temp.children.size() > 0) {
                for (Node<E> node : temp.children) {
                    queue.add(node);
                }
            }
        }
        return result;
    }

    /**
     * Method isBinary.
     * @return result
     */
    public boolean isBinary() {
        boolean result = true;
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<E> temp = queue.remove();
            if (temp.children.size() <= 2) {
                for (Node<E> node : temp.children) {
                    queue.add(node);
                }
            } else {
                result = false;
                break;
            }
        }
        return result;
    }

    /**
     * Getter.
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Iterator.
     * @return
     */
    @Override
    public Iterator<E> iterator() {
        List<E> list = new ArrayList<>();
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<E> temp = queue.remove();
            list.add(temp.value);

            if (temp.children.size() > 0) {
                for (Node<E> n : temp.children) {
                    queue.add(n);
                }
            }
        }

        Iterator<E> result = new Iterator<E>() {
            private int index = 0;
            @Override
            public boolean hasNext() {
                return index < list.size();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException();
                }
                E result = list.get(index++);
                return result;
            }
        };
        return result;
    }
}

