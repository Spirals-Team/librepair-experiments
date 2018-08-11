package net.coljate.cache;

import net.coljate.collection.Collection;
import net.coljate.map.Entry;
import net.coljate.util.iterator.CovariantIterator;

/**
 *
 * @author Ollie
 */
public interface Multimap<K, V> extends Cache<K, Collection<V>> {

    @Override
    Collection<V> get(K key);

    @Override
    MultimapEntry<K, V, ?> getEntry(Object key);

    @Override
    CovariantIterator<Entry<K, Collection<V>>, ?> iterator();

    @Override
    MutableMultimap<K, V> mutableCopy();

    @Override
    ImmutableMultimap<K, V> immutableCopy();

    interface MultimapEntry<K, V, C extends Collection<V>> extends Entry<K, Collection<V>> {

        @Override
        C value();

    }

}
