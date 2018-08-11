package net.coljate.map.impl;

import org.junit.jupiter.api.Nested;

import net.coljate.map.MutableBidirectionalMap;
import net.coljate.map.MutableBidirectionalMapTest;
import net.coljate.map.NewObjectEntryCreator;
import net.coljate.map.SameObjectEntryCreator;

/**
 *
 * @author Ollie
 */
public class MutableHashBidirectionalMapTest {

    @Nested
    class EmptyMutableBidirectionalMapTest extends NewObjectEntryCreator implements MutableBidirectionalMapTest.ZeroEntryTests<Object, Object> {

        @Override
        public MutableHashBidirectionalMap<Object, Object> createTestCollection() {
            return MutableHashBidirectionalMap.create();
        }

    }

    @Nested
    class OneEntryBidirectionalMapTest extends SameObjectEntryCreator implements MutableBidirectionalMapTest.OneEntryTests<Object, Object> {

        @Override
        public MutableBidirectionalMap<Object, Object> createTestCollection() {
            return MutableHashBidirectionalMap.create(this.getCollectionElement());
        }

    }

}
