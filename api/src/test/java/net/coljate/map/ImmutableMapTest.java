package net.coljate.map;

import net.coljate.set.ImmutableSetTest;

/**
 *
 * @author Ollie
 */
public interface ImmutableMapTest<K, V> extends MapTest<K, V>, ImmutableSetTest<Entry<K, V>> {

    @Override
    ImmutableMap<K, V> createTestCollection();

    interface OneEntryTests<K, V> extends ImmutableMapTest<K, V>, MapTest.OneEntryTests<K, V>, ImmutableSetTest.OneElementTests<Entry<K, V>> {

    }

}
