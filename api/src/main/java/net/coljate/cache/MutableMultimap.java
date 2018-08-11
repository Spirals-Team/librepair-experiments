package net.coljate.cache;

import net.coljate.collection.Collection;
import net.coljate.collection.MutableCollection;
import net.coljate.map.Entry;
import net.coljate.map.MutableEntry;
import net.coljate.util.iterator.CovariantIterator;
import net.coljate.util.iterator.Iterators;

/**
 *
 * @author Ollie
 */
public interface MutableMultimap<K, V>
        extends Multimap<K, V>, MutableCache<K, Collection<V>> {

    @Override
    MutableCollection<V> get(K key);

    @Override
    MutableMultimapEntry<K, V, ?> getEntry(Object key);

    @Override
    ImmutableMultimap<K, V> immutableCopy();

    @Override
    default CovariantIterator<Entry<K, Collection<V>>, ? extends MutableEntry<K, Collection<V>>> iterator() {
        return Iterators.transform(this.keys().iterator(), this::getEntry);
    }

    static <K, V> MutableMultimap<K, V> create() {
        return MutableListMultimap.createLinkedListMultimap();
    }

    interface MutableMultimapEntry<K, V, C extends MutableCollection<V>>
            extends MutableEntry<K, Collection<V>>, MultimapEntry<K, V, C> {

    }

}
