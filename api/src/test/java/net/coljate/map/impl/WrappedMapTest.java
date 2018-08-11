package net.coljate.map.impl;

import net.coljate.map.Map;
import net.coljate.map.MapTest;
import net.coljate.map.NewObjectEntryCreator;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class WrappedMapTest {

    @Nested
    class EmptyHashMapTest extends NewObjectEntryCreator implements MapTest.ZeroEntryTests<Object, Object> {

        @Override
        public Map<Object, Object> createTestCollection() {
            return new WrappedMap<>(new java.util.HashMap<>());
        }

    }

}
