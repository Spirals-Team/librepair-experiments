package ru.job4j.tree;


import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Queue;
import java.util.Collections;
import java.util.LinkedList;
import java.util.NoSuchElementException;

/**
 * class Bst.
 * @param <E> generic
 */
public class Bst<E extends Comparable<E>> implements Iterable<E> {
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
    public Bst(E first) {
        this.root = new Node<>(first);
        size++;
    }

    /**
     * Method add.
     * @param e element for adding
     */
    public void add(E e) {
       Node<E> temp = root;
       boolean cycle = true;
       while (cycle) {
           int value = e.compareTo(temp.value);
           if (value < 0) {
               if (temp.left == null) {
                   temp.left = new Node<>(e);
                   cycle = false;
                   size++;
               } else {
                   temp = temp.left;
               }
           } else if (value > 0) {
               if (temp.right == null) {
                   temp.right = new Node<>(e);
                   cycle = false;
                   size++;
               } else {
                   temp = temp.right;
               }
           } else {
              temp.value = e;
              cycle = false;
           }
       }
    }

    /**
     * Getter.
     * @return size
     */
    public int getSize() {
        return size;
    }

    /**
     * Static nested class Node.
     * @param <E> generic
     */
    class Node<E> {

        /**
         * @param value value
         */
        private E value;

        /**
         * @param left left node
         */
        private Node<E> left;
        /**
         * @param right right node
         */
        private Node<E> right;

        /**
         * Constructor.
         * @param value value
         */
        Node(E value) {
            this.value = value;
            left = null;
            right = null;
        }
    }

    /**
     * Iterator.
     * @return iterator
     */
    @Override
    public Iterator<E> iterator() {
        List<E> list = new ArrayList<>();
        Queue<Node<E>> queue = new LinkedList<>();
        queue.add(root);
        while (!queue.isEmpty()) {
            Node<E> temp = queue.remove();
            list.add(temp.value);

            if (temp.left != null) {
                queue.add(temp.left);
            }
            if (temp.right != null) {
                queue.add(temp.right);
            }
        }
        Collections.sort(list);

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

