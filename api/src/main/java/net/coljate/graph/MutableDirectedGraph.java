package net.coljate.graph;

import net.coljate.graph.impl.MutableHashBackedDirectedGraph;

/**
 *
 * @author Ollie
 */
public interface MutableDirectedGraph<V, E> extends DirectedGraph<V, E>, MutableGraph<V, E> {

    @Override
    @Deprecated
    default boolean add(final Relationship<V, E> relationship) {
        return relationship instanceof DirectedRelationship
                && this.add((DirectedRelationship<V, E>) relationship);
    }

    default boolean add(final DirectedRelationship<V, E> relationship) {
        return this.addEdge(relationship.from(), relationship.to(), relationship.edge());
    }

    @Override
    @Deprecated
    default boolean remove(final Relationship<?, ?> relationship) {
        return relationship instanceof DirectedRelationship
                && this.remove((DirectedRelationship<?, ?>) relationship);
    }

    boolean remove(DirectedRelationship<?, ?> relationship);

    @Override
    default MutableDirectedGraph<V, E> mutableCopy() {
        return copyOf(this);
    }

    static <V, E> MutableDirectedGraph<V, E> copyOf(final DirectedGraph<V, E> graph) {
        return MutableHashBackedDirectedGraph.copyOf(graph);
    }

    static <V, E> MutableDirectedGraph<V, E> createHashBackedGraph() {
        return MutableHashBackedDirectedGraph.create();
    }

}
