package net.coljate.set.impl;

import net.coljate.NewObjectCreator;
import net.coljate.SameObjectCreator;
import net.coljate.set.ImmutableSetTest;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ImmutableWrappedSetTest {

    @Nested
    class OneElementTest extends SameObjectCreator implements ImmutableSetTest.OneElementTests<Object> {

        @Override
        public ImmutableWrappedSet<Object> createTestCollection() {
            return ImmutableWrappedSet.copyIntoHashSet(this.getCollectionElement());
        }

    }

    @Nested
    class MultiElementTest extends NewObjectCreator implements ImmutableSetTest.MultiElementTests<Object> {

        @Override
        public ImmutableWrappedSet<Object> createTestCollection(final java.util.List<Object> list) {
            return ImmutableWrappedSet.copyIntoHashSet(list);
        }

    }

}
