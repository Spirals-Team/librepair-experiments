package net.coljate.cache;

import net.coljate.set.ImmutableSet;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public interface ImmutableSetMultimap<K, V>
        extends ImmutableMultimap<K, V>, SetMultimap<K, V> {

    @Override
    ImmutableMultimapEntry<K, V, ? extends ImmutableSet<V>> getEntry(Object key);

    @Override
    default ImmutableSet<V> get(final Object key) {
        return Functions.ifNonNull(this.getEntry(key), MultimapEntry::value);
    }

    @Deprecated
    @Override
    default ImmutableSetMultimap<K, V> immutableCopy() {
        return this;
    }

}
