package net.coljate.graph.impl;

import java.util.Objects;

import net.coljate.graph.Graph;
import net.coljate.graph.ValueGraph;
import net.coljate.map.Entry;
import net.coljate.map.Map;

/**
 *
 * @author Ollie
 */
public class MappedValueGraph<K, V, E>
        extends DelegatedGraph<K, E>
        implements ValueGraph<K, V, E> {

    private final Map<K, V> vertexValues;

    public MappedValueGraph(final Graph<K, E> graph, final Map<K, V> vertexValues) {
        super(graph);
        this.vertexValues = Objects.requireNonNull(vertexValues);
    }

    @Override
    public V get(final K key) {
        return vertexValues.get(key);
    }

    @Override
    public V getIfPresent(final Object vertexKey) {
        return vertexValues.getIfPresent(vertexKey);
    }

    @Override
    public Entry<K, V> getVertex(final Object vertexKey) {
        return vertexValues.getEntry(vertexKey);
    }

}
