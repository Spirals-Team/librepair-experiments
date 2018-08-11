package net.coljate.tree;

import net.coljate.map.ImmutableMapTest;

/**
 *
 * @author Ollie
 */
public interface ImmutableTreeMapTest<K, V> extends TreeMapTest<K, V>, ImmutableMapTest<K, V> {

    @Override
    ImmutableTreeMap<K, V, ?> createTestCollection();

    interface OneNodeTests<K, V>
            extends ImmutableTreeMapTest<K, V>, TreeMapTest.OneNodeTests<K, V>, ImmutableMapTest.OneEntryTests<K, V> {

    }

}
