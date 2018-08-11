package net.coljate.tree;

import net.coljate.map.MapTest;

/**
 *
 * @author Ollie
 */
public interface TreeMapTest<K, V> extends MapTest<K, V> {

    @Override
    TreeMap<K, V, ?> createTestCollection();

    interface ZeroNodeTests<K, V> extends TreeMapTest<K, V>, MapTest.ZeroEntryTests<K, V> {

    }

    interface OneNodeTests<K, V> extends TreeMapTest<K, V>, MapTest.OneEntryTests<K, V> {

    }

}
