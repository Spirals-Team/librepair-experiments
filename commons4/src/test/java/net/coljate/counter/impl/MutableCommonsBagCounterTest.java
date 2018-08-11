package net.coljate.counter.impl;

import net.coljate.NewObjectCreator;
import net.coljate.SameObjectCreator;
import net.coljate.counter.MutableCounter;
import net.coljate.counter.MutableCounterTest;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MutableCommonsBagCounterTest {

    @Nested
    class EmptyHashCounterTest extends NewObjectCreator implements MutableCounterTest.ZeroElementTests<Object> {

        @Override
        public MutableCounter<Object> createTestCollection() {
            return MutableCommonsBagCounter.createHashCounter();
        }

    }

    @Nested
    class NonEmptyHashCounterTest extends SameObjectCreator implements MutableCounterTest.OneElementTests<Object> {

        @Override
        public MutableCounter<Object> createTestCollection() {
            return MutableCommonsBagCounter.createHashCounter(this.getCollectionElement());
        }

    }

}
