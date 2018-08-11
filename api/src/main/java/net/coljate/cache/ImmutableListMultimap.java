package net.coljate.cache;

import net.coljate.list.ImmutableList;
import net.coljate.util.functions.Functions;

/**
 *
 * @author Ollie
 */
public interface ImmutableListMultimap<K, V>
        extends ListMultimap<K, V>, ImmutableMultimap<K, V> {

    @Override
    ImmutableMultimapEntry<K, V, ? extends ImmutableList<V>> getEntry(Object key);

    @Override
    default ImmutableList<V> get(final Object key) {
        return Functions.ifNonNull(this.getEntry(key), MultimapEntry::value);
    }

    @Override
    default ImmutableListMultimap<K, V> immutableCopy() {
        return this;
    }

}
