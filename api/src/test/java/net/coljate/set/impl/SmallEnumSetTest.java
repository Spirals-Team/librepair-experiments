package net.coljate.set.impl;

import net.coljate.set.EnumSet;
import net.coljate.set.impl.EnumSetTest.TestEnum;

import org.junit.jupiter.api.Nested;

/**
 *
 * @author ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class SmallEnumSetTest {

    @Nested
    public class SingletonElementTests implements EnumSetTest.OneElementTests {

        @Override
        public EnumSet<TestEnum> createTestCollection() {
            return SmallEnumSet.of(this.getCollectionElement());
        }

    }

}
