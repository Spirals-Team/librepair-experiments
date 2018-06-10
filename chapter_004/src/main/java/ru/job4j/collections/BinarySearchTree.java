package ru.job4j.collections;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.NoSuchElementException;

/**
 * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
 * @version $Id$
 * @since 0.1
 */
public class BinarySearchTree<T extends Comparable> implements Iterable<T> {
   private BinaryNode<T> root;
   private List<T> list;
   private int size = 0;

    public BinarySearchTree() {
        list = new LinkedList<>();
        root = new BinaryNode<>(null);
    }

    public boolean add(T element) {
        if (size == 0) {
            root.setElement(element);
            size++;
            return true;
        }
        BinaryNode<T> newNode = new BinaryNode<>(element);
        BinaryNode<T> lastNode = findLastNode(root, newNode);
        if (lastNode == null) {
            return false;
        }
        size++;
        newNode.setParent(lastNode);
        if (lastNode.getElement().compareTo(newNode.getElement()) < 0) {
            lastNode.setRight(newNode);
            return true;
        } else {
            lastNode.setLeft(newNode);
            return true;
        }
    }

    private BinaryNode<T> findLastNode(BinaryNode<T> oldNode,
                                       BinaryNode<T> newNode
    ) {
        BinaryNode<T> lastNode = oldNode;
        int compare = oldNode.getElement().compareTo(newNode.getElement());

        if (compare < 0 && oldNode.getRight() != null) {
            lastNode = findLastNode(oldNode.getRight(), newNode);
            return lastNode;
        }

        if (compare > 0 && oldNode.getLeft() != null) {
            lastNode = findLastNode(oldNode.getLeft(), newNode);
            return lastNode;
        }

        if (compare == 0) {
            return null;
        }
        return lastNode;
    }

    @Override
    public Iterator<T> iterator() {
        return new Iterator<T>() {
            int count = 0;
            Iterator<BinaryNode<T>> iterator = new BinarySearchTreeIterator(root);

            @Override
            public boolean hasNext() {
                return iterator.hasNext();
            }

            @Override
            public T next() {
                count++;
                return iterator.next().getElement();
            }
        };
    }

    private class  BinarySearchTreeIterator<T> implements Iterator<BinaryNode> {

        private BinaryNode next;

        private BinarySearchTreeIterator(BinaryNode root) {
            next = root;
            while (next.getLeft() != null) {
                next = next.getLeft();
            }
        }

        @Override
        public boolean hasNext() {
            return next != null && next.getElement() != null;
        }

        @Override
        public BinaryNode next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            BinaryNode r = next;
            if (next.getRight() != null) {
                next = next.getRight();
                while (next.getLeft() != null) {
                    next = next.getLeft();
                }
                return r;
            }
            while (true) {
                if (next.getParent() == null) {
                    next = null;
                    return r;
                }
                if (next.getParent().getLeft() == next) {
                    next = next.getParent();
                    return r;
                }
                next = next.getParent();
            }
        }
    }
}
