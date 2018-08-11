package net.coljate.graph;

import net.coljate.set.impl.TwoSet;

/**
 *
 * @author Ollie
 */
public interface MutableUndirectedGraph<V, E>
        extends MutableGraph<V, E>, UndirectedGraph<V, E> {

    @Override
    @Deprecated
    default boolean add(final Relationship<V, E> relationship) {
        return relationship instanceof UndirectedRelationship
                && this.add((UndirectedRelationship<V, E>) relationship);
    }

    default boolean add(final UndirectedRelationship<V, E> relationship) {
        final TwoSet<? extends V> vertices = relationship.vertices();
        return this.addEdge(vertices.first(), vertices.last(), relationship.edge());
    }

    @Override
    @Deprecated
    default boolean remove(final Relationship<?, ?> relationship) {
        return relationship instanceof UndirectedRelationship
                && this.remove((UndirectedRelationship<?, ?>) relationship);
    }

    boolean remove(UndirectedRelationship<?, ?> relationship);

}
