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
        if (contains(child)) {
            try {
                throw new ContainsDuplicateException("Contains");
            } catch (ContainsDuplicateException e) {
                e.printStackTrace();
            }
        }
        Optional<Node<E>> elem = findBy(parent);
        try {
            elem.get().add(new Node(child));
        } catch (NoSuchElementException e) {
            System.out.println("No such element!");
        }
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
            private Queue<Node<E>> data = new LinkedList<>();
            private Queue<Node<E>> queue = new LinkedList<>();

            {
                Optional<Node<E>> result = Optional.empty();
                data.offer(this.head);
                while (!data.isEmpty()) {
                    Node<E> element = data.poll();
                    queue.offer(element);

                    for (Node<E> child : element.leaves()) {
                        data.offer(child);
                    }
                }
            }

            @Override
            public boolean hasNext() {
                return !queue.isEmpty();
            }

            @Override
            public E next() {
                if (!hasNext()) {
                    throw new NoSuchElementException("No such element!");
                }
                return queue.poll().getValue();
            }
        };
    }
}
