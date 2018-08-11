package net.coljate.graph;

import java.util.Objects;

/**
 *
 * @author Ollie
 */
public abstract class AbstractDirectedRelationship<V, E>
        extends AbstractRelationship<V, E>
        implements DirectedRelationship<V, E> {

    @Override
    protected boolean equals(final Relationship<?, ?> that) {
        return that instanceof DirectedRelationship
                && this.equals((DirectedRelationship) that);
    }

    protected boolean equals(final DirectedRelationship<?, ?> that) {
        return Objects.equals(this.from(), that.from())
                && Objects.equals(this.to(), that.to())
                && Objects.equals(this.edge(), that.edge());
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 73 * hash + Objects.hashCode(this.from());
        hash = 73 * hash + Objects.hashCode(this.to());
        hash = 73 * hash + Objects.hashCode(this.edge());
        return hash;
    }

    @Override
    public String toString() {
        return this.getClass().getSimpleName()
                + ":{from=[" + this.from()
                + "],to=[" + this.to()
                + "],edge=[" + this.edge()
                + "]}";
    }

}
