package net.coljate.tree;

/**
 *
 * @author Ollie
 */
public interface MutableBinaryTreeMapTest<K, V>
        extends BinaryTreeMapTest<K, V>, MutableTreeMapTest<K, V> {

    @Override
    MutableBinaryTreeMap<K, V, ?> createTestCollection();

    interface ZeroNodeTests<K, V>
            extends MutableBinaryTreeMapTest<K, V>, BinaryTreeMapTest.ZeroNodeTests<K, V>, MutableTreeMapTest.ZeroNodeTests<K, V> {

    }

    interface OneNodeTests<K, V>
            extends MutableBinaryTreeMapTest<K, V>, BinaryTreeMapTest.OneNodeTests<K, V>, MutableTreeMapTest.OneNodeTests<K, V> {

    }

}
