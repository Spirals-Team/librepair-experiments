package ru.job4j.tree;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Petr Arsentev (parsentev@yandex.ru)
 * @version $Id$
 * @since 0.1
 */
public class Node<E> {
    private final List<Node<E>> children = new ArrayList<>();
    private final E value;

    public E getValue() {
        return value;
    }

    public Node(final E value) {
        this.value = value;
    }

    public void add(Node<E> child) {
        this.children.add(child);
    }

    public List<Node<E>> leaves() {
        return this.children;
    }

    public boolean eqValue(E that) {
        return this.value.equals(that);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }

        Node<?> node = (Node<?>) o;

        if (children != null ? !children.equals(node.children) : node.children != null) {
            return false;
        }
        return value != null ? value.equals(node.value) : node.value == null;
    }

    @Override
    public int hashCode() {
        int result = children != null ? children.hashCode() : 0;
        result = 31 * result + (value != null ? value.hashCode() : 0);
        return result;
    }
}
