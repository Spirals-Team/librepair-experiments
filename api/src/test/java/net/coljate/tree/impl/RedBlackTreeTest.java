package net.coljate.tree.impl;

import net.coljate.map.Entry;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;

import net.coljate.tree.MutableBinaryTreeMap;
import net.coljate.tree.MutableBinaryTreeMapTest;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class RedBlackTreeTest {

    class BaseIntegerKeyTest {

        private int counter;

        @BeforeEach
        public final void resetKeyCounter() {
            counter = 0;
        }

        public Entry<Integer, Object> createTestObject() {
            return Entry.of(counter++, new Object());
        }

    }

    @Nested
    class EmptyRedBlackTreeTest
            extends BaseIntegerKeyTest
            implements MutableBinaryTreeMapTest.ZeroNodeTests<Integer, Object> {

        @Override
        public MutableBinaryTreeMap<Integer, Object, ?> createTestCollection() {
            return RedBlackTreeMap.keyComparing();
        }

        @Override
        @Disabled //FIXME implement, reenable
        public void testImmutableCopy() {
            ZeroNodeTests.super.testImmutableCopy();
        }

    }

}
