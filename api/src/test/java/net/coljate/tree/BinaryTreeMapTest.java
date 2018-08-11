package net.coljate.tree;

/**
 *
 * @author Ollie
 */
public interface BinaryTreeMapTest<K, V> extends TreeMapTest<K, V> {

    @Override
    BinaryTreeMap<K, V, ?> createTestCollection();

    interface ZeroNodeTests<K, V> extends BinaryTreeMapTest<K, V>, TreeMapTest.ZeroNodeTests<K, V> {

    }

    interface OneNodeTests<K, V> extends BinaryTreeMapTest<K, V>, TreeMapTest.OneNodeTests<K, V> {

    }

}
