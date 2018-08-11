package net.coljate.list;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
/**
 *
 * @author Ollie
 */
public interface MutableArrayTest<T> extends ArrayTest<T>, MutableListTest<T> {

    @Override
    MutableArray<T> createTestCollection();

    interface ZeroElementTests<T> extends MutableArrayTest<T>, ArrayTest.ZeroElementTests<T>, MutableListTest.ZeroElementTests<T> {

        @Test
        default void testResize() {
            final MutableArray<T> array = this.createTestCollection();
            assertThat(array.length()).isEqualTo(0);
            array.resize(1);
            assertThat(array.length()).isEqualTo(1);
            array.resize(1);
            assertThat(array.length()).isEqualTo(1);
        }

    }

    interface OneElementTests<T> extends MutableArrayTest<T>, ArrayTest.OneElementTests<T>, MutableListTest.OneElementTests<T> {

    }

}
