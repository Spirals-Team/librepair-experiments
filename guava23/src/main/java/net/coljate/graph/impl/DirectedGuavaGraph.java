package net.coljate.graph.impl;

import net.coljate.graph.AbstractRelationship;
import net.coljate.graph.DirectedGraph;
import net.coljate.graph.DirectedRelationship;
import net.coljate.graph.Relationship;
import net.coljate.set.Set;
import net.coljate.util.iterator.CovariantIterator;
import net.coljate.util.iterator.Iterators;

/**
 * @author Ollie
 */
public class DirectedGuavaGraph<V, E>
        extends AbstractGuavaGraph<V, E>
        implements DirectedGraph<V, Set<E>> {

    protected DirectedGuavaGraph(final com.google.common.graph.Network<V, E> network) {
        super(network);
        if (!network.isDirected()) {
            throw new IllegalArgumentException("Not a directed graph: " + network);
        }
    }

    @Override
    public CovariantIterator<Relationship<V, Set<E>>, ? extends DirectedRelationship<V, Set<E>>> iterator() {
        return Iterators.transform(network.asGraph().edges().iterator(), DirectedGuavaRelationship::new);
    }

    @Override
    public MutableDirectedGuavaGraph<V, E> mutableCopy() {
        return new MutableDirectedGuavaGraph<>(this.mutableNetworkCopy());
    }

    @Override
    public ImmutableDirectedGuavaGraph<V, E> immutableCopy() {
        return new ImmutableDirectedGuavaGraph<>(this.immutableNetworkCopy());
    }

    protected class DirectedGuavaRelationship
            extends AbstractRelationship<V, Set<E>>
            implements DirectedRelationship<V, Set<E>> {

        private final com.google.common.graph.EndpointPair<V> endpoints;

        protected DirectedGuavaRelationship(final com.google.common.graph.EndpointPair<V> endpoints) {
            this.endpoints = endpoints;
        }

        @Override
        public V from() {
            return endpoints.nodeU();
        }

        @Override
        public V to() {
            return endpoints.nodeV();
        }

        @Override
        public Set<E> edge() {
            return Set.viewOf(network.edgesConnecting(this.from(), this.to()));
        }

    }

}
