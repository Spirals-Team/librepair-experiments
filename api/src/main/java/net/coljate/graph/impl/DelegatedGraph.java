package net.coljate.graph.impl;

import java.util.Iterator;

import net.coljate.collection.Collection;
import net.coljate.graph.AbstractGraph;
import net.coljate.graph.Graph;
import net.coljate.graph.ImmutableGraph;
import net.coljate.graph.MutableGraph;
import net.coljate.graph.Relationship;
import net.coljate.map.Map;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public abstract class DelegatedGraph<K, E> extends AbstractGraph<K, E> {

    private final Graph<K, E> graph;

    protected DelegatedGraph(final Graph<K, E> graph) {
        this.graph = graph;
    }

    @Override
    public int count() {
        return graph.count();
    }

    @Override
    public boolean contains(final Relationship<?, ?> relationship) {
        return graph.contains(relationship);
    }

    @Override
    public int degree(final K vertex) {
        return graph.degree(vertex);
    }

    @Override
    public E edgeBetween(final Object fromVertex, final Object toVertex) {
        return graph.edgeBetween(fromVertex, toVertex);
    }

    @Override
    public Map<K, E> neighbours(final K vertex) {
        return graph.neighbours(vertex);
    }

    @Override
    public Relationship<K, E> relationshipBetween(final Object fromVertex, final Object toVertex) {
        return graph.relationshipBetween(fromVertex, toVertex);
    }

    @Override
    public int size() {
        return graph.size();
    }

    @Override
    public Collection<E> edges() {
        return graph.edges();
    }

    @Override
    public int order() {
        return graph.order();
    }

    @Override
    public Set<K> vertices() {
        return graph.vertices();
    }

    @Override
    public MutableGraph<K, E> mutableCopy() {
        return graph.mutableCopy();
    }

    @Override
    public ImmutableGraph<K, E> immutableCopy() {
        return graph.immutableCopy();
    }

    @Override
    public Iterator<Relationship<K, E>> iterator() {
        return graph.iterator();
    }

}
