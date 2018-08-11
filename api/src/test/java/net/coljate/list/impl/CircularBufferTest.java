package net.coljate.list.impl;

import net.coljate.NewObjectCreator;
import net.coljate.SameObjectCreator;
import net.coljate.list.Queue;
import net.coljate.list.QueueTest;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.OptionalInt;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
/**
 *
 * @author Ollie
 */
public class CircularBufferTest {

    @Test
    public void testZeroCapacityBuffer() {
        assertThrows(IllegalArgumentException.class, () -> CircularBuffer.create(0));
    }

    @Nested
    class EmptyBufferTest extends NewObjectCreator implements QueueTest.ZeroElementTests<Object> {

        @Override
        public CircularBuffer<Object> createTestCollection() {
            return CircularBuffer.create(1);
        }

        @Test
        public void testCapacity() {
            assertThat(this.createTestCollection().capacity()).isEqualTo(OptionalInt.of(1));
        }

        @Test
        public void testIsFull() {
            assertFalse(this.createTestCollection().isFull());
        }

    }

    @Nested
    class SingletonBufferTest extends SameObjectCreator implements QueueTest.OneElementTests<Object> {

        @Override
        public CircularBuffer<Object> createTestCollection() {
            final CircularBuffer<Object> queue = CircularBuffer.create(2);
            queue.add(this.getCollectionElement());
            return queue;
        }

        @Test
        public void testIsFull() {
            assertFalse(this.createTestCollection().isFull());
        }

        @Test
        public void testIsFull_AfterAdding() {
            final CircularBuffer<Object> queue = this.createTestCollection();
            assertTrue(queue.add(this.createTestObject()));
            assertTrue(queue.isFull());
            assertFalse(queue.add(this.createTestObject()));
        }

        @Test
        public void testEnqueue_IsFull() {
            final CircularBuffer<Object> queue = this.createTestCollection();
            assertTrue(queue.add(this.createTestObject()));
            assertThrows(Queue.EnqueueException.class, () -> queue.enqueue(this.createTestObject()));
        }

    }

}
