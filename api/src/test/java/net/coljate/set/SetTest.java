package net.coljate.set;

import net.coljate.TestObjectCreator;
import net.coljate.collection.CollectionTest;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Spliterator;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * @author Ollie
 */
public interface SetTest<T> extends CollectionTest<T> {

    @Override
    Set<T> createTestCollection();

    @Test
    default int testSpliterator_Characteristics() {
        final int characteristics = this.createTestCollection().spliterator().characteristics();
        assertThat(characteristics & Spliterator.DISTINCT)
                .isEqualTo(Spliterator.DISTINCT)
                .describedAs("Spliterator characteristics %s must contain distinct bit.", Integer.toBinaryString(characteristics));
        return characteristics;
    }

    interface ZeroElementTests<T> extends SetTest<T>, CollectionTest.ZeroElementTests<T> {

    }

    interface OneElementTests<T> extends SetTest<T>, CollectionTest.OneElementTests<T> {

    }

    interface MultiElementTests<T> extends SetTest<T>, TestObjectCreator<T> {

        @Override
        default Set<T> createTestCollection() {
            return this.createTestCollection(java.util.Collections.emptyList());
        }

        default Set<T> createTestCollection(final T t1) {
            return this.createTestCollection(Arrays.asList(t1));
        }

        default Set<T> createTestCollection(final T t1, final T t2) {
            final java.util.List<T> list = new java.util.ArrayList<>();
            list.add(t1);
            list.add(t2);
            return this.createTestCollection(list);
        }

        Set<T> createTestCollection(java.util.List<T> list);

        @Test
        default void testCount() {
            assertThat(this.createTestCollection(this.createTestObject(), this.createTestObject()).count()).isEqualTo(2);
        }

        @Test
        default void testIntersection() {
            final T a = this.createTestObject(), b = this.createTestObject(), c = this.createTestObject();
            final Set<T> first = this.createTestCollection(a, b);
            final Set<T> second = this.createTestCollection(b, c);
            final Set<T> and = first.intersection(second);
            assertThat(and.count()).isEqualTo(1);
            assertFalse(and.contains(a));
            assertTrue(and.contains(b));
            assertFalse(and.contains(c));
        }

        @Test
        default void testOr() {
            final T a = this.createTestObject(), b = this.createTestObject(), c = this.createTestObject();
            final Set<T> first = this.createTestCollection(a, b);
            final Set<T> second = this.createTestCollection(b, c);
            final Set<T> or = first.union(second);
            assertThat(or.count()).isEqualTo(3);
        }

        @Test
        default void testNot() {
            final T a = this.createTestObject(), b = this.createTestObject(), c = this.createTestObject();
            final Set<T> first = this.createTestCollection(a, b);
            final Set<T> second = this.createTestCollection(b, c);
            final Set<T> not = first.not(second);
            assertTrue(not.contains(a));
            assertFalse(not.contains(b));
            assertFalse(not.contains(c));
            assertThat(not.count()).isEqualTo(1);
        }

        @Test
        default void testXor() {
            final T a = this.createTestObject(), b = this.createTestObject(), c = this.createTestObject();
            final Set<T> first = this.createTestCollection(a, b);
            final Set<T> second = this.createTestCollection(b, c);
            final Set<T> xor = first.xor(second);
            assertTrue(xor.contains(a));
            assertFalse(xor.contains(b));
            assertTrue(xor.contains(c));
            assertThat(xor.count()).isEqualTo(2);
        }

    }

}
