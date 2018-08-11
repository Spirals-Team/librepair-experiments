package net.coljate.counter;

import net.coljate.collection.MutableCollectionTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 *
 * @author Ollie
 */
public interface MutableCounterTest<T> extends CounterTest<T>, MutableCollectionTest<T> {

    @Override
    MutableCounter<T> createTestCollection();

    interface ZeroElementTests<T>
            extends MutableCounterTest<T>, CounterTest.ZeroElementTests<T>, MutableCollectionTest.ZeroElementTests<T> {

    }

    interface OneElementTests<T>
            extends MutableCounterTest<T>, CounterTest.OneElementTests<T>, MutableCollectionTest.OneElementTests<T> {

        @Test
        default void testSet() {
            final MutableCounter<T> counter = this.createTestCollection();
            counter.set(this.getCollectionElement(), 5);
            assertThat(counter.count(this.getCollectionElement())).isEqualTo(5);
        }

        @Test
        default void testSet_Negative() {
            final MutableCounter<T> counter = this.createTestCollection();
            assertThrows(IllegalArgumentException.class, () -> counter.set(this.getCollectionElement(), -5));
            assertThat(counter.count(this.getCollectionElement())).isEqualTo(1);
        }

        @Test
        default void testSet_Inserts() {
            final MutableCounter<T> counter = this.createTestCollection();
            final T newElement = this.createTestObject();
            counter.set(newElement, 2);
            assertThat(counter.count(newElement)).isEqualTo(2);
        }

    }

}
