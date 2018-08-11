package net.coljate.set;

import net.coljate.collection.ImmutableCollectionTest;

/**
 *
 * @author Ollie
 */
public interface ImmutableSetTest<T> extends SetTest<T>, ImmutableCollectionTest<T> {

    @Override
    ImmutableSet<T> createTestCollection();

    interface ZeroElementTests<T> extends ImmutableSetTest<T>, SetTest.ZeroElementTests<T> {

    }

    interface OneElementTests<T> extends ImmutableSetTest<T>, SetTest.OneElementTests<T> {

    }

}
