package ru.job4j.collections;

import java.util.ArrayList;
import java.util.List;

    /**
     * @author Alexei Zuryanov (zuryanovalexei@gmail.com)
     * @version $Id$
     * @since 0.1
     */
public  class Leaf<T extends Comparable<T>> {
    private final List<Leaf<T>> children = new ArrayList<>();
    private final T value;

    public Leaf(final T value) {
        this.value = value;
    }

    public void add(Leaf<T> child) {
        this.children.add(child);
    }

    public List<Leaf<T>> leaves() {
        return this.children;
    }

    public boolean eqValue(T that) {
        return this.value.compareTo(that) == 0;
    }
    public T getValue() {
        return this.value;
    }
}
