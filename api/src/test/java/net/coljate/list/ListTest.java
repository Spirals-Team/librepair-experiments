package net.coljate.list;

import net.coljate.collection.CollectionTest;

/**
 *
 * @author Ollie
 */
public interface ListTest<T> extends CollectionTest<T> {

    @Override
    List<T> createTestCollection();

    interface ZeroElementTests<T> extends ListTest<T>, CollectionTest.ZeroElementTests<T> {

    }

    interface OneElementTests<T> extends ListTest<T>, CollectionTest.OneElementTests<T> {

    }

}
