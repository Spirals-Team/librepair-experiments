package ru.job4j.tree;

import java.util.*;

public class Tree<E extends  Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;

    public Tree(E root) {
        this.root = new Node(root);
    }

    @Override
    public Optional<Node<E>> findBy(E value) {
        Optional<Node<E>> result = Optional.empty();
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(this.root);
        while (!data.isEmpty()) {
            Node<E> element = data.poll();
            if (element.eqValue(value)) {
                result = Optional.of(element);
                break;
            }
            for (Node<E> child : element.leaves()) {
                data.offer(child);
            }
        }
        return result;
    }

    @Override
    public boolean add(E parent, E child) {
        Optional<Node<E>> elem = findBy(parent);
        elem.get().add(new Node(child));
        return true;
    }

    public boolean contains(E element) {
        Iterator<E> it = this.iterator();
        while (it.hasNext()) {
            if (it.next().equals(element))
                return true;
        }
        return false;
    }

    public boolean isBinary() {
        boolean result = true;
        Queue<Node<E>> data = new LinkedList<>();
        data.offer(root);
        while (!data.isEmpty()) {
            Node<E> node = data.poll();
            List<Node<E>> children = node.leaves();
            if (children.size() > 2) {
                result = false;
                break;
            }
            children.forEach(data::offer);
        }
        return result;
    }

    @Override
    public Iterator<E> iterator() throws NoSuchElementException {
        return new Iterator<E>() {
            private Node<E> head = root;
            private Node<E> next = root;
            private Iterator<Node<E>> it = head.leaves().iterator();
            private boolean firstIt = true;

            @Override
            public boolean hasNext() {
                return it.hasNext() || next.leaves().size() != 0;
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No such element!");
                }
                if (firstIt) {
                    firstIt = false;
                    return root.getValue();
                }
                E result = null;
                if (!it.hasNext()) {
                    it = next.leaves().iterator();
                    next = it.next();
                    while (next.leaves().size() == 0) {
                        next = it.next();
                    }
                    head = next;
                    next = next.leaves().iterator().next();
                    it = head.leaves().iterator();
                }
                result = it.next().getValue();
                return result;
            }
        };
    }
}
