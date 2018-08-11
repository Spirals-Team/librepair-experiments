package net.coljate.graph.impl;

import net.coljate.graph.AbstractGraph;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public abstract class AbstractGuavaGraph<V, E>
        extends AbstractGraph<V, Set<E>> {

    final com.google.common.graph.Network<V, E> network;
    private Set<V> vertices;

    protected AbstractGuavaGraph(final com.google.common.graph.Network<V, E> network) {
        this.network = network;
    }

    @Override
    public Set<V> vertices() {
        return vertices == null
                ? vertices = Set.viewOf(network.nodes())
                : vertices;
    }

    public com.google.common.graph.MutableNetwork<V, E> mutableNetworkCopy() {
        return com.google.common.graph.NetworkBuilder.from(network).build();
    }

    public com.google.common.graph.ImmutableNetwork<V, E> immutableNetworkCopy() {
        return com.google.common.graph.ImmutableNetwork.copyOf(network);
    }

}
