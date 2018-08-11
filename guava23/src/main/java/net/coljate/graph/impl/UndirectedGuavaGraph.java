package net.coljate.graph.impl;

import net.coljate.graph.*;
import net.coljate.set.Set;
import net.coljate.set.impl.TwoSet;
import net.coljate.util.iterator.CovariantIterator;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public class UndirectedGuavaGraph<V, E>
        extends AbstractGuavaGraph<V, E>
        implements UndirectedGraph<V, Set<E>> {

    protected UndirectedGuavaGraph(final com.google.common.graph.Network<V, E> network) {
        super(network);
        if (network.isDirected()) {
            throw new IllegalArgumentException("Network is directed: " + network);
        }
    }

    @Override
    public CovariantIterator<Relationship<V, Set<E>>, ? extends UndirectedRelationship<V, Set<E>>> iterator() {
        return Iterators.transform(network.asGraph().edges().iterator(), UndirectedGuavaRelationship::new);
    }

    @Override
    public MutableUndirectedGraph<V, Set<E>> mutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public ImmutableUndirectedGraph<V, Set<E>> immutableCopy() {
        throw new UnsupportedOperationException(); //TODO
    }

    protected class UndirectedGuavaRelationship
            extends AbstractRelationship<V, Set<E>>
            implements UndirectedRelationship<V, Set<E>> {

        private final com.google.common.graph.EndpointPair<V> endpoints;

        protected UndirectedGuavaRelationship(final com.google.common.graph.EndpointPair<V> endpoints) {
            this.endpoints = endpoints;
        }

        @Override
        public TwoSet<V> vertices() {
            return TwoSet.require(endpoints.nodeU(), endpoints.nodeV());
        }

        @Override
        public Set<E> edge() {
            return Set.viewOf(network.edgesConnecting(endpoints.nodeU(), endpoints.nodeV()));
        }

    }

}
