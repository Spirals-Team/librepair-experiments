package net.coljate.cache;

import net.coljate.collection.Collection;

/**
 *
 * @author Ollie
 */
public interface MutableMultimapTest<K, V>
        extends MultimapTest<K, V>, MutableCacheTest<K, Collection<V>> {

    @Override
    MutableMultimap<K, V> createTestCollection();

    interface ZeroEntryTests<K, V>
            extends MutableMultimapTest<K, V>, MultimapTest.ZeroEntryTests<K, V>, MutableCacheTest.ZeroEntryTests<K, Collection<V>> {

    }

    interface OneEntryTests<K, V>
            extends MutableMultimapTest<K, V>, MultimapTest.OneEntryTests<K, V>, MutableCacheTest.OneEntryTests<K, Collection<V>> {

    }

}
