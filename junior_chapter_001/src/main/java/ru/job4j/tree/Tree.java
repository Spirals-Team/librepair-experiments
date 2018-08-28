package ru.job4j.tree;

import java.util.*;

public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    Node<E> root;

    public Tree(E value) {
        this.root = new Node<>(value);
    }

    @Override
    public boolean add(E parent, E child) {
        if (!findBy(child).isPresent()) {
            if (!findBy(parent).isPresent()) {
                this.root.add(new Node<>(parent));
            }
            findBy(parent).get().add(new Node<>(child));
            return true;
        }
        return false;
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

    public boolean isBinary() {
        Queue<Node<E>> data = new LinkedList<>();
        Node<E> next;
        data.offer(this.root);
        boolean resault = true;
        if (!data.isEmpty()) {
            next = data.remove();
            for (Node<E> node : next.leaves()) {
                data.offer(node);
            }
            if (next.leaves().size() > 2) {
                resault = false;
            }
        }
        return resault;
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private Queue<Node<E>> data = new LinkedList<>();
            private Node<E> next;
            private boolean flag = true;

            private void findBy() {
                data.offer(root);
            }

            @Override
            public boolean hasNext() {
                if (flag) {
                    findBy();
                    flag = false;
                }
                return !data.isEmpty();
            }

            @Override
            public E next() {
                if (hasNext()) {
                    next = data.remove();
                    for (Node<E> node : next.leaves()) {
                        data.offer(node);
                    }
                }
                return next.getValue();
            }
        };
    }
}
