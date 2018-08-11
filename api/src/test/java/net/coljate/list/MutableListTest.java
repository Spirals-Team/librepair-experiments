package net.coljate.list;

import net.coljate.collection.MutableCollectionTest;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertTrue;
/**
 *
 * @author Ollie
 */
public interface MutableListTest<T> extends ListTest<T>, MutableCollectionTest<T> {

    @Override
    MutableList<T> createTestCollection();

    @Test
    default void testPrefix() {

        final MutableList<T> list = this.createTestCollection();
        final int initialCount = list.count();
        final T element = this.createTestObject();

        list.prefix(element);

        assertTrue(list.contains(element));
        assertThat(list.count()).isEqualTo(initialCount + 1);
        assertThat(list.first()).isEqualTo(element);
        if (initialCount == 0) {
            assertThat(list.last()).isEqualTo(element);
        }

    }

    @Test
    default void testSuffix() {

        final MutableList<T> list = this.createTestCollection();
        final int initialCount = list.count();
        final T element = this.createTestObject();

        list.suffix(element);

        assertTrue(list.contains(element));
        assertThat(list.count()).isEqualTo(initialCount + 1);
        assertThat(list.last()).isEqualTo(element);
        if (initialCount == 0) {
            assertThat(list.first()).isEqualTo(element);
        }

    }

    interface ZeroElementTests<T> extends MutableListTest<T>, ListTest.ZeroElementTests<T>, MutableCollectionTest.ZeroElementTests<T> {

    }

    interface OneElementTests<T> extends MutableListTest<T>, ListTest.OneElementTests<T>, MutableCollectionTest.OneElementTests<T> {

    }

}
