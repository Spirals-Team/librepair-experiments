package net.coljate.set.impl;

import net.coljate.NewObjectCreator;
import net.coljate.set.MutableSet;
import net.coljate.set.MutableSetTest;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MutableWrappedSetTest {

    @Nested
    class ZeroElementTests extends NewObjectCreator implements MutableSetTest.ZeroElementTests<Object> {

        @Override
        public MutableSet<Object> createTestCollection() {
            return MutableWrappedSet.of();
        }

    }

}
