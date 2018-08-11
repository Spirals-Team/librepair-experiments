package net.coljate.collection;

import org.junit.jupiter.api.Test;

import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Ollie
 */
public interface CollectionTest<T> {

    Collection<T> createTestCollection();

    @Test
    default void testMutableCopy() {
        assertNotNull(this.createTestCollection().mutableCopy());
    }

    @Test
    default void testImmutableCopy() {
        assertNotNull(this.createTestCollection().immutableCopy());
    }

    @Test
    default void testSpliterator() {
        assertNotNull(this.createTestCollection().spliterator());
    }

    interface ZeroElementTests<T> extends CollectionTest<T> {

        @Test
        default void testCount() {
            assertThat(this.createTestCollection().count()).isEqualTo(0);
        }

        @Test
        default void testIsEmpty() {
            assertTrue(this.createTestCollection().isEmpty());
        }

        @Test
        default void testIterator() {
            final Iterator<T> iterator = this.createTestCollection().iterator();
            assertFalse(iterator.hasNext());
            assertThrows(NoSuchElementException.class, () -> iterator.next());
        }

    }

    interface OneElementTests<T> extends CollectionTest<T> {

        T getCollectionElement();

        @Test
        default void testCount() {
            assertThat(this.createTestCollection().count()).isEqualTo(1);
        }

        default void testCount_Element() {
            assertThat(this.createTestCollection().count(e -> Objects.equals(e, this.getCollectionElement()))).isEqualTo(1);
        }

        @Test
        default void testContains() {
            assertTrue(this.createTestCollection().contains(this.getCollectionElement()));
        }

        @Test
        default void testIterator() {
            final Iterator<T> iterator = this.createTestCollection().iterator();
            assertTrue(iterator.hasNext());
            assertEquals(this.getCollectionElement(), iterator.next());
            assertFalse(iterator.hasNext());
            assertThrows(NoSuchElementException.class, () -> iterator.next());
        }

        @Test
        default void testIterator_HasNextRepeatable() {
            final Iterator<T> iterator = this.createTestCollection().iterator();
            assertTrue(iterator.hasNext());
            assertTrue(iterator.hasNext());
            assertTrue(iterator.hasNext());
            iterator.next();
            assertFalse(iterator.hasNext());
            assertFalse(iterator.hasNext());
            assertFalse(iterator.hasNext());
        }

        @Test
        default void testIterator_NextWithoutHaveNext() {
            final Iterator<T> iterator = this.createTestCollection().iterator();
            assertThat(iterator.next()).isEqualTo(this.getCollectionElement());
            assertThrows(NoSuchElementException.class, () -> iterator.next());
        }

    }

}
