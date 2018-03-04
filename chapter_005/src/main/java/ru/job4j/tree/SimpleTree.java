package ru.job4j.tree;

public interface SimpleTree<E extends Comparable<E>> extends Iterable<E> {
    // Add child element in parent element
    boolean add(E parent, E child);
}
