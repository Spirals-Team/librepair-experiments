package net.coljate.cache;

import net.coljate.set.MutableSet;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public interface MutableSetMultimap<K, V>
        extends SetMultimap<K, V>, MutableMultimap<K, V> {

    @Override
    MutableMultimapEntry<K, V, ? extends MutableSet<V>> getEntry(Object key);

    @Override
    default MutableSet<V> get(final Object key) {
        return Functions.ifNonNull(this.getEntry(key), MultimapEntry::value);
    }

}
