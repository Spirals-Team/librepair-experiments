package net.coljate.map.impl;

import net.coljate.map.Entry;
import net.coljate.map.MutableMap;
import net.coljate.map.MutableMapTest;
import net.coljate.map.NewObjectEntryCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

import java.util.List;

/**
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class ChainedHashMapTest {

    @Nested
    class EmptyMapTest extends NewObjectEntryCreator implements MutableMapTest.ZeroEntryTests<Object, Object> {

        @Override
        public MutableMap<Object, Object> createTestCollection() {
            return ChainedHashMap.create(1);
        }

    }

    @Nested
    class SingletonMapTest extends NewObjectEntryCreator implements MutableMapTest.OneEntryTests<Object, Object> {

        private Entry<Object, Object> entry;

        @BeforeEach
        public final void resetEntry() {
            entry = this.createTestObject();
        }

        @Override
        public MutableMap<Object, Object> createTestCollection() {
            final MutableMap<Object, Object> map = ChainedHashMap.create(10);
            map.add(this.getCollectionElement());
            return map;
        }

        @Override
        public Entry<Object, Object> getCollectionElement() {
            return entry;
        }

    }

    @Nested
    class DualMapTest extends NewObjectEntryCreator implements MutableMapTest.TwoEntryTests<Object, Object> {

        @Override
        public MutableMap<Object, Object> createTestCollection(final List<Entry<Object, Object>> list) {
            final MutableMap<Object, Object> map = ChainedHashMap.create(list.size());
            list.forEach(map::put);
            return map;
        }

    }

}
