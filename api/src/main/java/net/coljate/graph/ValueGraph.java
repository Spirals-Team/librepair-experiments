package net.coljate.graph;

import javax.annotation.CheckForNull;

import net.coljate.graph.impl.MappedValueGraph;
import net.coljate.map.Associative;
import net.coljate.map.Entry;
import net.coljate.map.Map;
import net.coljate.util.functions.Functions;

/**
 * A graph of vertices {@code Entry<K,V>} with a key/value association.
 *
 * @author Ollie
 * @param <K> vertex key type
 * @param <V> vertex value type
 * @param <E> edge type
 */
public interface ValueGraph<K, V, E> extends Graph<K, E>, Associative<K, V> {

    @CheckForNull
    Entry<K, V> getVertex(Object vertexKey);

    @Override
    default V getIfPresent(final Object vertexKey) {
        return Functions.ifNonNull(this.getVertex(vertexKey), Entry::value);
    }

    static <K, V, E> ValueGraph<K, V, E> of(final Graph<K, E> graph, final Map<K, V> vertexValues) {
        return new MappedValueGraph<>(graph, vertexValues);
    }

}
