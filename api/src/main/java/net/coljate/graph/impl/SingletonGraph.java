package net.coljate.graph.impl;

import net.coljate.graph.ImmutableGraph;
import net.coljate.graph.MutableGraph;
import net.coljate.graph.Relationship;
import net.coljate.set.ImmutableSet;
import net.coljate.util.iterator.UnmodifiableIterator;

/**
 *
 * @author ollie
 */
public class SingletonGraph<V, E> implements ImmutableGraph<V, E> {

    private final V vertex;

    public SingletonGraph(final V vertex) {
        this.vertex = vertex;
    }

    @Override
    public ImmutableSet<V> vertices() {
        return ImmutableSet.of(vertex);
    }

    @Override
    public MutableGraph<V, E> mutableCopy() {
        throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public UnmodifiableIterator<Relationship<V, E>> iterator() {
        return UnmodifiableIterator.of();
    }

}
