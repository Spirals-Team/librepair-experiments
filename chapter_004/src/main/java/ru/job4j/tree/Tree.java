package ru.job4j.tree;

import java.util.*;

/**
 * @author Yury Matskevich
 * @since 0.1
 */
public class Tree<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;
    private int modCount = 0;

    public Tree(E value) {
        this.root = new Node<>(value);
    }

    @Override
    public boolean add(E parent, E child) {
        boolean result = false;
        if (!findBy(child).isPresent()) {
            findBy(parent).get().add(new Node<>(child));
            modCount++;
            result = true;
        }
        return result;
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
        return new TreeIterator();
    }

    public boolean isBinary() {
        NodeSequential sequential = new NodeSequential(root);
        boolean result = true;
        while (sequential.hasNext()) {
            if (sequential.next().leaves().size() > 2) {
                result = false;
                break;
            }
        }
        return result;
    }

    private class NodeSequential {
        private Stack<ListIterator<Node<E>>> iterators = new Stack<>();
        private Stack<Node<E>> stackParent = new Stack<>();
        private ListIterator<Node<E>> it = null;

        NodeSequential(Node<E> node) {
            stackParent.push(node);
            fillIteratorStack(node);
        }

        public boolean hasNext() {
            return !stackParent.isEmpty();
        }

        public Node<E> next() {
            Node<E> result;
            it = (it == null && !iterators.isEmpty()) ? iterators.pop() : it;
            if (it != null && it.hasNext()) {
                Node<E> node = it.next();
                while (!node.leaves().isEmpty()) {
                    it = putInStack(node, it);
                    node = it.next();
                }
                result = node;
            } else {
                result = stackParent.pop();
                it = !iterators.isEmpty() ? iterators.pop() : it;
            }
            return result;
        }

        private ListIterator<Node<E>> putInStack(Node<E> node, ListIterator<Node<E>> it) {
            iterators.push(it);
            stackParent.push(node);
            iterators.push(node.leaves().listIterator());
            return iterators.pop();
        }

        private void fillIteratorStack(Node<E> node) {
            if (!node.leaves().isEmpty()) {
                iterators.push(node.leaves().listIterator());
            }
        }
    }

    private class TreeIterator implements Iterator<E> {
        private int expectedModCount = modCount;
        NodeSequential nodeSequential = new NodeSequential(root);

        @Override
        public boolean hasNext() {
            checkModCount();
            return nodeSequential.hasNext();
        }

        @Override
        public E next() {
            if (!hasNext()) {
                throw new NoSuchElementException();
            }
            return nodeSequential.next().getValue();
        }

        private void checkModCount() {
            if (expectedModCount != modCount) {
                throw new ConcurrentModificationException();
            }
        }
    }
}
