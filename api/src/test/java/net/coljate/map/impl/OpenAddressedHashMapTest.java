package net.coljate.map.impl;

import net.coljate.map.Entry;
import net.coljate.map.MutableMap;
import net.coljate.map.MutableMapTest;
import net.coljate.map.NewObjectEntryCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;

class OpenAddressedHashMapTest {

    @Nested
    class EmptyMapTest extends NewObjectEntryCreator implements MutableMapTest.ZeroEntryTests<Object, Object> {

        @Override
        public MutableMap<Object, Object> createTestCollection() {
            return OpenAddressedHashMap.create(1);
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
            final MutableMap<Object, Object> map = OpenAddressedHashMap.create(10);
            map.add(this.getCollectionElement());
            return map;
        }

        @Override
        public Entry<Object, Object> getCollectionElement() {
            return entry;
        }

    }

}