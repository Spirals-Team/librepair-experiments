package net.coljate.list.impl;

import net.coljate.NewObjectCreator;
import net.coljate.SameObjectCreator;
import net.coljate.list.MutableList;
import net.coljate.list.MutableListTest;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MutableLinkedListTest {

    @Nested
    class EmptyLinkedListTest extends NewObjectCreator implements MutableListTest.ZeroElementTests<Object> {

        @Override
        public MutableLinkedList<Object> createTestCollection() {
            return MutableLinkedList.copyOf();
        }

    }

    @Nested
    class SingletonLinkedListTest extends SameObjectCreator implements MutableListTest.OneElementTests<Object> {

        @Override
        public MutableList<Object> createTestCollection() {
            return MutableLinkedList.copyOf(this.getCollectionElement());
        }

    }

}
