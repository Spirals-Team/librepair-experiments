package net.coljate.list;

import java.util.NoSuchElementException;

import net.coljate.collection.MutableCollectionTest;

import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public interface QueueTest<T> extends MutableCollectionTest<T> {

    @Override
    Queue<T> createTestCollection();

    interface ZeroElementTests<T> extends QueueTest<T>, MutableCollectionTest.ZeroElementTests<T> {

        @Test
        default void testPoll() {
            assertNull(this.createTestCollection().poll());
        }

        @Test
        default void testPeek() {
            assertNull(this.createTestCollection().peek());
        }

        @Test
        default void testDequeue() {
            assertThrows(NoSuchElementException.class, () -> this.createTestCollection().dequeue());
        }

    }

    interface OneElementTests<T> extends QueueTest<T>, MutableCollectionTest.OneElementTests<T> {

    }

}
