package net.coljate.graph;

import net.coljate.set.impl.TwoSet;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.Optional;

/**
 * Encapsulates an edge between two vertices.
 * <p>
 * The vertices are reversible if this is a member of an undirected graph.
 *
 * @param <V> vertex type
 * @param <E> edge type
 */
public interface Relationship<V, E> {

    E edge();

    @Nonnull
    TwoSet<V> vertices();

    default boolean containsVertex(final Object vertex) {
        return this.vertices().contains(vertex);
    }

    boolean isBetween(Object fromVertex, Object toVertex);

    default Optional<V> otherVertex(final Object vertex) {
        final TwoSet<V> vertices = this.vertices();
        if (Objects.equals(vertex, vertices.first())) {
            return Optional.of(vertices.last());
        } else if (Objects.equals(vertex, vertices.last())) {
            return Optional.of(vertices.first());
        } else {
            return Optional.empty();
        }
    }

}
