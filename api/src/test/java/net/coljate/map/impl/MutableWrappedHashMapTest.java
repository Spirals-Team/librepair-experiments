package net.coljate.map.impl;

import net.coljate.map.MutableMap;
import net.coljate.map.MutableMapTest;
import net.coljate.map.NewObjectEntryCreator;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MutableWrappedHashMapTest {

    @Nested
    class ZeroElementTest extends NewObjectEntryCreator implements MutableMapTest.ZeroEntryTests<Object, Object> {

        @Override
        public MutableMap<Object, Object> createTestCollection() {
            return MutableWrappedHashMap.create();
        }

    }
    
}
