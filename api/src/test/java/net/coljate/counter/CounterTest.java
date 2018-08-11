package net.coljate.counter;

import net.coljate.collection.CollectionTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 *
 * @author Ollie
 */
public interface CounterTest<T> extends CollectionTest<T> {

    @Override
    Counter<T> createTestCollection();

    @Test
    default void testCount_Missing() {
        assertThat(this.createTestCollection().count(new Object())).isEqualTo(0);
    }

    interface ZeroElementTests<T> extends CounterTest<T>, CollectionTest.ZeroElementTests<T> {

    }

    interface OneElementTests<T> extends CounterTest<T>, CollectionTest.OneElementTests<T> {

    }

}
