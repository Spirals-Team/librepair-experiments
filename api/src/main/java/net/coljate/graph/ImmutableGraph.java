package net.coljate.graph;

import net.coljate.collection.ImmutableCollection;
import net.coljate.graph.impl.SingletonGraph;
import net.coljate.set.ImmutableSet;

/**
 *
 * @author Ollie
 */
public interface ImmutableGraph<V, E>
        extends Graph<V, E>, ImmutableSet<Relationship<V, E>> {

    @Override
    default ImmutableSet<V> vertices() {
        return Graph.super.vertices().immutableCopy();
    }

    @Override
    default ImmutableCollection<E> edges() {
        return Graph.super.edges().immutableCopy();
    }

    @Override
    @Deprecated
    default ImmutableGraph<V, E> immutableCopy() {
        return this;
    }

    static <V, E> ImmutableGraph<V, E> of(final V vertex) {
        return new SingletonGraph<>(vertex);
    }

}
