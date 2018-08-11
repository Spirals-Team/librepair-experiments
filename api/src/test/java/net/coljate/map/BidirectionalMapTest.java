package net.coljate.map;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 *
 * @author Ollie
 */
public interface BidirectionalMapTest<K, V> extends MapTest<K, V> {

    @Override
    BidirectionalMap<K, V> createTestCollection();

    interface ZeroEntryTests<K, V> extends BidirectionalMapTest<K, V>, MapTest.ZeroEntryTests<K, V> {

        @Test
        default void testCount_Inverse() {
            assertThat(this.createTestCollection().inverseView().count()).isEqualTo(0);
        }

        @Test
        default void testIsEmpty_Inverse() {
            assertTrue(this.createTestCollection().inverseView().isEmpty());
        }

    }

    interface OneEntryTests<K, V> extends BidirectionalMapTest<K, V>, MapTest.OneEntryTests<K, V> {

        @Test
        default void testCount_Inverse() {
            assertThat(this.createTestCollection().inverseView().count()).isEqualTo(1);
        }

        @Test
        default void testIsEmpty_Inverse() {
            assertFalse(this.createTestCollection().inverseView().isEmpty());
        }

    }

}
