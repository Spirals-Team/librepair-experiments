package net.coljate.tree;

import net.coljate.map.MutableMapTest;

/**
 *
 * @author Ollie
 */
public interface MutableTreeMapTest<K, V> extends TreeMapTest<K, V>, MutableMapTest<K, V> {

    @Override
    MutableTreeMap<K, V, ?> createTestCollection();

    interface ZeroNodeTests<K, V>
            extends MutableTreeMapTest<K, V>, TreeMapTest.ZeroNodeTests<K, V>, MutableMapTest.ZeroEntryTests<K, V> {

    }

    interface OneNodeTests<K, V>
            extends MutableTreeMapTest<K, V>, TreeMapTest.OneNodeTests<K, V>, MutableMapTest.OneEntryTests<K, V> {

    }

}
