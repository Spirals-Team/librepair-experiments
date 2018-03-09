package ru.aserdyuchenko.tree.task_2;

import ru.aserdyuchenko.tree.task_1.SimpleTree;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Stack;

/**
 * @author anton.
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;

    public boolean isBinary() {
        boolean result = true;
        if (root != null) {
            Node<E> node = root;
            if (!node.getChilden().isEmpty()) {
                for (Node<E> aChilden : node.getChilden()) {
                    if (aChilden.getChilden().size() > 2) return false;
                }
            }
        } else result = false;
        return result;
    }

    @Override
    public boolean add(E parent, E child) {
        try {
            Node<E> node = new Node<>(parent);
            if (root == null) {
                root = node;
                root.getChilden().add(new Node<>(child));
                return true;
            } else return insertRec(root, node, new Node<>(child));
        } catch (Exception exception) {
            return false;
        }
    }

    private boolean insertRec(Node<E> latestRoot, Node<E> parent, Node<E> child) {
        if (latestRoot.getValue().compareTo(parent.getValue()) == 0) {
            latestRoot.getChilden().add(child);
            return true;
        } else {
            for (Node<E> node : root.getChilden()) {
                if (parent.getValue().compareTo(node.getValue()) == 0) {
                    node.getChilden().add(child);
                    return true;
                }
            }
            return false;
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Stack<E> stack = new Stack<>();
            private int index = 0;

            @Override
            public boolean hasNext() {
                return (!stack.isEmpty() || root != null);
            }

            private void popInStack(Node<E> node) {
                stack.add(node.getValue());
                if (!node.getChilden().isEmpty()) {
                    for (Node<E> aChilden : node.getChilden()) {
                        popInStack(aChilden);
                    }
                }
                root = null;
            }

            @Override
            public E next() {
                if (index == 0) popInStack(root);
                index++;
                return stack.pop();
            }
        };
    }

    class Node<E> {
        private E value;
        private List<Node<E>> childen;

        /**
         * Constructor.
         * @param e - new value.
         */
        Node(E e) {
            this.value = e;
            childen = new LinkedList<>();
        }

        /**
         * Get value.
         * @return - parent.
         */
        public E getValue() {
            return this.value;
        }

        public List<Node<E>> getChilden() {
            return this.childen;
        }
    }
}