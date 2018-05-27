package ru.job4j.tree;

import ru.job4j.list.QueueList;
import ru.job4j.list.StackList;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.TreeSet;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class BST<E extends Comparable> implements Iterable {
    private Node<E> head;
    private int size;
    private int modCount;

    public BST() {
        this.head = null;
        this.size = 0;
        this.modCount = 0;
    }

    public BST(E element) {
        Node<E> node = new Node<>(element);
        this.head = node;
        this.size = 1;
        this.modCount = 0;
    }

    public void add(E e) {
        this.modCount++;
        Node<E> node = new Node<>(e);
        if (head == null) {
            this.head = node;
            this.size = 1;
        } else {
            add(node, head);
            this.size++;
        }
    }

    private void add(Node<E> newNode, Node<E> node) {
        if (newNode.element.compareTo(node.element) <= 0) {
            if (node.left != null) {
                add(newNode, node.left);
            } else {
                node.left = newNode;
            }
        } else {
            if (node.right != null) {
                add(newNode, node.right);
            } else {
                node.right = newNode;
            }
        }
    }

    public int getSize() {
        return size;
    }

    @Override
    public Iterator iterator() {
        return new Iterator() {
            private Node<E> nextNode = head;
            private StackList<E> list = new StackList<>();
            private boolean flagNewIter = true;
            private int notChange = modCount;

            @Override
            public boolean hasNext() {
                if (flagNewIter) {
                    addNodesInStack(nextNode);
                    flagNewIter = false;
                }
                return list.getSize() > 0;
            }

            @Override
            public Object next() {
                if (notChange != modCount) {
                    throw new ConcurrentModificationException("Iterator only for read.");
                }
                if (!hasNext()) {
                    throw new NoSuchElementException("Not more elements.");
                }
                return list.pull();
            }

            private void addNodesInStack(Node<E> node) {
                if (node.left != null) {
                    addNodesInStack(node.left);
                }
                if (node.right != null) {
                    addNodesInStack(node.right);
                }
                list.push(node.element);
            }
        };
    }

    class Node<E> {
        E element;
        Node<E> left;
        Node<E> right;

        public Node(E element) {
            this.element = element;
            this.left = null;
            this.right = null;
        }
    }
}
