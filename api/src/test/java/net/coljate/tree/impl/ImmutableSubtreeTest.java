package net.coljate.tree.impl;

import net.coljate.map.NewObjectEntryCreator;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assumptions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;

import net.coljate.tree.ImmutableTreeMap;
import net.coljate.tree.ImmutableTreeMapTest;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ImmutableSubtreeTest extends SubtreeTest {

    @Nested
    class LeafTreeTest extends NewObjectEntryCreator implements ImmutableTreeMapTest.OneNodeTests<Object, Object> {

        private ImmutableSubtree<Object, Object> lastTree;

        @BeforeEach
        public void beforeTree() {
            Assumptions.assumeTrue(lastTree == null);
            lastTree = ImmutableSubtree.of(new Object(), new Object());
        }

        @AfterEach
        public final void afterTree() {
            lastTree = null;
        }

        @Override
        public ImmutableTreeMap<Object, Object, ?> createTestCollection() {
            return lastTree;
        }

        @Override
        public ImmutableSubtree<Object, Object> getCollectionElement() {
            return lastTree;
        }

        @Override
        @Disabled //TODO
        public void testMutableCopy() {
            ImmutableTreeMapTest.OneNodeTests.super.testMutableCopy();
        }

    }

}
