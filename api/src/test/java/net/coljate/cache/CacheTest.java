package net.coljate.cache;

import net.coljate.map.MapTest;

/**
 *
 * @author Ollie
 */
public interface CacheTest<K, V> extends MapTest<K, V> {

    @Override
    Cache<K, V> createTestCollection();

    interface ZeroEntryTests<K, V> extends CacheTest<K, V>, MapTest.ZeroEntryTests<K, V> {

    }

    interface OneEntryTests<K, V> extends CacheTest<K, V>, MapTest.OneEntryTests<K, V> {

    }

}
