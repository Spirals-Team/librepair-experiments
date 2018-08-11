package net.coljate.list.impl;

import net.coljate.NewObjectCreator;
import net.coljate.list.MutableListTest;
import net.coljate.list.QueueTest;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class WrappedDequeTest {

    @Nested
    class EmptyWrappedDequeTest
            extends NewObjectCreator
            implements QueueTest.ZeroElementTests<Object>, MutableListTest.ZeroElementTests<Object> {

        @Override
        public WrappedDeque<Object> createTestCollection() {
            return WrappedDeque.copyIntoArrayDeque();
        }

    }

}
