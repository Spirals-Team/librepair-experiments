package net.coljate.set;

import net.coljate.collection.MutableCollectionTest;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ollie
 */
public interface MutableSetTest<T> extends SetTest<T>, MutableCollectionTest<T> {

    @Override
    MutableSet<T> createTestCollection();

    interface ZeroElementTests<T> extends MutableSetTest<T>, SetTest.ZeroElementTests<T>, MutableCollectionTest.ZeroElementTests<T> {

        @Test
        default void testAdd() {
            final T element = this.createTestObject();
            final MutableSet<T> set = this.createTestCollection();
            assertTrue(set.add(element));
            assertFalse(set.add(element));
        }

    }

    interface OneElementTests<T> extends MutableSetTest<T>, SetTest.OneElementTests<T>, MutableCollectionTest.OneElementTests<T> {

    }

}
