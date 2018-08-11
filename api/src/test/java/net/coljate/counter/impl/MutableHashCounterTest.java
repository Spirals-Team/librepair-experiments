package net.coljate.counter.impl;

import java.util.Arrays;

import net.coljate.NewObjectCreator;
import net.coljate.SameObjectCreator;
import net.coljate.counter.MutableCounter;
import net.coljate.counter.MutableCounterTest;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
public class MutableHashCounterTest {

    @Nested
    class EmptyCounterTest extends NewObjectCreator implements MutableCounterTest.ZeroElementTests<Object> {

        @Override
        public MutableHashCounter<Object> createTestCollection() {
            return MutableHashCounter.create();
        }

    }

    @Nested
    class SingletonCounterTest extends SameObjectCreator implements MutableCounterTest.OneElementTests<Object> {

        @Override
        public MutableCounter<Object> createTestCollection() {
            return MutableHashCounter.copyOf(Arrays.asList(this.getCollectionElement()));
        }

    }

}
