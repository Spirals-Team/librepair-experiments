package net.coljate.cache;

import net.coljate.map.MutableMapTest;

/**
 *
 * @author Ollie
 */
public interface MutableCacheTest<K, V> extends CacheTest<K, V>, MutableMapTest<K, V> {

    @Override
    MutableCache<K, V> createTestCollection();

    interface ZeroEntryTests<K, V>
            extends MutableCacheTest<K, V>, CacheTest.ZeroEntryTests<K, V>, MutableMapTest.ZeroEntryTests<K, V> {

    }

    interface OneEntryTests<K, V>
            extends MutableCacheTest<K, V>, CacheTest.OneEntryTests<K, V>, MutableMapTest.OneEntryTests<K, V> {

    }

}
