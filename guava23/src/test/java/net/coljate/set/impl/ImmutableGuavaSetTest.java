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
public class ImmutableGuavaSetTest {

    @Nested
    class ZeroElementTest extends NewObjectCreator implements ImmutableSetTest.ZeroElementTests<Object> {

        @Override
        public ImmutableGuavaSet<Object> createTestCollection() {
            return new ImmutableGuavaSet<>(com.google.common.collect.ImmutableSet.of());
        }

    }

    @Nested
    class OneElementTest extends SameObjectCreator implements ImmutableSetTest.OneElementTests<Object> {

        @Override
        public ImmutableGuavaSet<Object> createTestCollection() {
            return new ImmutableGuavaSet<>(com.google.common.collect.ImmutableSet.of(this.getCollectionElement()));
        }

    }

}
