package ru.job4j.tree;

import ru.job4j.list.StackList;

import java.util.*;

/**
 * @author Michael Hodkov
 * @version $Id$
 * @since 0.1
 */
public class TreeList<E extends Comparable<E>> implements SimpleTree<E> {
    private Node<E> root;
    private int modCount;

    public TreeList(E element) {
        Node<E> node = new Node<>(element);
        this.root = node;
    }

    @Override
    public boolean add(E parent, E child) {
        modCount++;
        Optional<Node<E>> oNode = findBy(parent);
        if (!oNode.isPresent()) {
            return false;
        }
        Node<E> node = oNode.get();
        oNode = findBy(child);
        if (!oNode.isPresent()) {
            Node<E> childNode = new Node<>(child);
            node.add(childNode);
            return true;
        } else {
            return false;
        }
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
    public Iterator iterator() {
        return new Iterator() {
            private Node<E> node = root;
            private StackList<E> list = new StackList<>();
            private boolean flagNewIter = true;
            private int notChange = modCount;
            @Override
            public boolean hasNext() {
                if (flagNewIter) {
                    addNodesInStack(node);
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
                if (node.leaves() != null) {
                    for (Node<E> items: node.leaves()) {
                        addNodesInStack(items);
                    }
                }
                list.push(node.getValue());
            }
        };
    }

    public boolean isBinary() {
        if (root.leaves().size() > 2) {
            return false;
        } else {
            Queue<Node<E>> data = new LinkedList<>();
            data.offer(this.root);
            while (!data.isEmpty()) {
                Node<E> el = data.poll();
                if (el.leaves().size() > 2) {
                    return false;
                }
                for (Node<E> child : el.leaves()) {
                    data.offer(child);
                }
            }
        }
        return true;
    }
}
