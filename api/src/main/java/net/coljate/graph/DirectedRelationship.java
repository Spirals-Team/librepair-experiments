package net.coljate.graph;

import java.util.Objects;
import java.util.Optional;

import javax.annotation.Nonnull;

import net.coljate.graph.impl.RegularDirectedRelationship;
import net.coljate.set.impl.TwoSet;

/**
 *
 * @author Ollie
 */
public interface DirectedRelationship<V, E> extends Relationship<V, E> {

    @Nonnull
    V from();

    @Nonnull
    V to();

    @Override
    default boolean containsVertex(final Object vertex) {
        return this.isFrom(vertex) || this.isTo(vertex);
    }

    @Override
    default boolean isBetween(final Object fromVertex, final Object toVertex) {
        return this.isFrom(fromVertex) && this.isTo(toVertex);
    }

    default boolean isFrom(final Object vertex) {
        return Objects.equals(vertex, this.from());
    }

    default boolean isTo(final Object vertex) {
        return Objects.equals(vertex, this.to());
    }

    @Override
    default TwoSet<V> vertices() {
        return TwoSet.require(this.from(), this.to());
    }

    @Override
    default Optional<V> otherVertex(final Object vertex) {
        if (Objects.equals(vertex, this.from())) {
            return Optional.of(this.to());
        } else if (Objects.equals(vertex, this.to())) {
            return Optional.of(this.from());
        } else {
            return Optional.empty();
        }
    }

    static <V, E> DirectedRelationship<V, E> of(final V fromVertex, final V toVertex, final E edge) {
        return new RegularDirectedRelationship<>(fromVertex, toVertex, edge);
    }

}
