package net.coljate.graph;

import java.util.Objects;

/**
 * @author Ollie
 */
public abstract class AbstractRelationship<V, E> implements Relationship<V, E> {

    @Override
    public boolean equals(final Object that) {
        return that instanceof Relationship
                && this.equals((Relationship) that);
    }

    protected boolean equals(final Relationship<?, ?> that) {
        return Objects.equals(this.vertices(), that.vertices())
                && Objects.equals(this.edge(), that.edge());
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 79 * hash + Objects.hashCode(this.vertices());
        hash = 79 * hash + Objects.hashCode(this.edge());
        return hash;
    }

}
