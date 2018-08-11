package net.coljate.graph.impl;

import net.coljate.graph.DirectedRelationship;
import net.coljate.graph.MutableDirectedGraph;
import net.coljate.set.Set;

/**
 *
 * @author Ollie
 */
public class MutableDirectedGuavaGraph<V, E>
        extends DirectedGuavaGraph<V, E>
        implements MutableDirectedGraph<V, Set<E>> {

    public static <V, E> MutableDirectedGuavaGraph<V, E> create() {
        return new MutableDirectedGuavaGraph<>(com.google.common.graph.NetworkBuilder.directed().build());
    }

    public static <V, E> MutableDirectedGuavaGraph<V, E> viewOf(final com.google.common.graph.MutableNetwork<V, E> network) {
        return new MutableDirectedGuavaGraph<>(network);
    }

    private final com.google.common.graph.MutableNetwork<V, E> network;

    protected MutableDirectedGuavaGraph(final com.google.common.graph.MutableNetwork<V, E> network) {
        super(network);
        this.network = network;
    }

    @Override
    public boolean remove(final DirectedRelationship<?, ?> relationship) {
        throw new UnsupportedOperationException(); //TODO
    }

    @Override
    public boolean addVertex(final V vertex) {
        return network.addNode(vertex);
    }

    @Override
    public boolean addEdge(final V fromVertex, final V toVertex, final Set<E> edges) {
        boolean addedAny = false;
        for (final E edge : edges) {
            addedAny |= network.addEdge(fromVertex, toVertex, edge);
        }
        return addedAny;
    }

    @Override
    public boolean removeVertex(final V vertex) {
        return network.removeNode(vertex);
    }

    @Override
    public void clear() {
        throw new UnsupportedOperationException(); //TODO
    }

}
