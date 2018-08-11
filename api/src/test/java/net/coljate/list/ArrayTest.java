package net.coljate.list;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
/**
 *
 * @author Ollie
 */
public interface ArrayTest<T> extends ListTest<T> {

    @Override
    Array<T> createTestCollection();

    interface ZeroElementTests<T> extends ArrayTest<T>, ListTest.ZeroElementTests<T> {

    }

    interface OneElementTests<T> extends ArrayTest<T>, ListTest.OneElementTests<T> {

        @Test
        default void testGet() {
            assertThat(this.createTestCollection().get(0)).isEqualTo(this.getCollectionElement());
        }

        @Test
        default void testGet_IndexOutOfBounds() {
            assertThrows(IndexOutOfBoundsException.class, () -> this.createTestCollection().get(-1));
            assertThrows(IndexOutOfBoundsException.class, () -> this.createTestCollection().get(1));
        }

    }

}
