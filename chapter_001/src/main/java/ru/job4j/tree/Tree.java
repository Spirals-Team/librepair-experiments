package ru.job4j.tree;

import java.util.*;

/**
 * Simple structure of tree.
 *
 * @author Roman Baranov (baranov.rp@gmail.com)
 * @version 1
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;
    Stack<Node<E>> children = new Stack<>();

    public Tree(E root) {
        this.root = new Node<>(root);
    }

    @Override
    public boolean add(E parent, E child) {
        if (findBy(child).isPresent()) {
            return false;
        }
        if (!findBy(parent).isPresent()) {
            return false;
        }

        Node<E> ancestor = findBy(parent).get();
        ancestor.add(new Node<>(child));
        return true;
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> rsl = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> el = data.poll();
            if (el.eqValue(value)) {
                rsl = Optional.of(el);
                break;
            }
            for (Node<E> child : el.leaves()) {
                data.offer(child);
            }
        }
        return rsl;
    }

    @Override
    public Iterator<E> iterator() {
        getAllChildren();

        return new Iterator<E>() {
            @Override
            public boolean hasNext() {
                return !children.isEmpty();
            }

            @Override
            public E next() {
                if (children.isEmpty()) {
                    throw new NoSuchElementException();
                }
                return children.pop().getValue();
            }
        };
    }

    private void getAllChildren() {
        getAllChildren(this.root);
    }

    private void getAllChildren(Node<E> node) {
        if (node != null) {
            children.add(node);
            for (Node<E> leaf : node.leaves()) {
                getAllChildren(leaf);
            }
        }
    }
}
