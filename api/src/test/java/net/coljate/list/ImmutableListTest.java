package net.coljate.list;

import net.coljate.collection.ImmutableCollectionTest;

/**
 *
 * @author Ollie
 */
public interface ImmutableListTest<T> extends ListTest<T>, ImmutableCollectionTest<T> {

    @Override
    ImmutableList<T> createTestCollection();

    interface OneElementTests<T> extends ImmutableListTest<T>, ListTest.OneElementTests<T>, ImmutableCollectionTest.OneElementTests<T> {

    }

}
