package net.coljate.collection;

import net.coljate.TestObjectCreator;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ollie
 */
public interface MutableCollectionTest<T> extends CollectionTest<T>, TestObjectCreator<T> {

    @Override
    MutableCollection<T> createTestCollection();

    interface ZeroElementTests<T> extends MutableCollectionTest<T>, CollectionTest.ZeroElementTests<T> {

        @Test
        default void testClear() {
            this.createTestCollection().clear();
        }

        @Test
        default void testRemoveFirst() {
            assertFalse(this.createTestCollection().removeFirst(this.createTestObject()));
        }

    }

    interface OneElementTests<T> extends MutableCollectionTest<T>, CollectionTest.OneElementTests<T> {

        @Test
        default void testClear() {
            final MutableCollection<T> collection = this.createTestCollection();
            final T element = this.getCollectionElement();
            assertTrue(collection.contains(element));
            collection.clear();
            assertFalse(collection.contains(element));
            assertThat(collection.count()).isEqualTo(0);
        }

        @Test
        default void testRemoveFirst() {
            final MutableCollection<T> collection = this.createTestCollection();
            final T element = this.getCollectionElement();
            assertTrue(collection.contains(element));

            assertTrue(collection.removeFirst(element));
            assertFalse(collection.contains(element));
            assertTrue(collection.isEmpty());
        }

    }

}
