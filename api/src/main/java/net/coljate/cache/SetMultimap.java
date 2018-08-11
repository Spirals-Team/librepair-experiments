package net.coljate.cache;

import net.coljate.set.Set;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public interface SetMultimap<K, V>
        extends Multimap<K, V> {

    @Override
    MultimapEntry<K, V, ? extends Set<V>> getEntry(Object key);

    @Override
    default Set<V> get(final Object key) {
        return Functions.ifNonNull(this.getEntry(key), MultimapEntry::value);
    }

    @Override
    MutableSetMultimap<K, V> mutableCopy();

    @Override
    ImmutableSetMultimap<K, V> immutableCopy();

}
