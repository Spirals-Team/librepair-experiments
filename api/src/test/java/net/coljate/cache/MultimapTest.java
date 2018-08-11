package net.coljate.cache;

import net.coljate.collection.Collection;

/**
 *
 * @author Ollie
 */
public interface MultimapTest<K, V>
        extends CacheTest<K, Collection<V>> {

    @Override
    Multimap<K, V> createTestCollection();

    interface ZeroEntryTests<K, V> extends MultimapTest<K, V>, CacheTest.ZeroEntryTests<K, Collection<V>> {

    }

    interface OneEntryTests<K, V> extends MultimapTest<K, V>, CacheTest.OneEntryTests<K, Collection<V>> {

    }

}
