package net.coljate.set.impl;

import net.coljate.set.EnumSet;
import net.coljate.set.SetTest;
import net.coljate.set.impl.EnumSetTest.TestEnum;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

/**
 *
 * @author ollie
 */
public interface EnumSetTest extends SetTest<TestEnum> {

    interface OneElementTests extends SetTest.OneElementTests<TestEnum> {

        @Override
        EnumSet<TestEnum> createTestCollection();

        @Override
        default TestEnum getCollectionElement() {
            return TestEnum.TWO;
        }

        @Test
        @Override
        default void testContains() {
            final EnumSet<TestEnum> set = this.createTestCollection();
            for (final TestEnum e : TestEnum.values()) {
                if (e == this.getCollectionElement()) {
                    assertTrue(set.contains(e));
                } else {
                    assertFalse(set.contains(e));
                }
            }
        }

    }

    enum TestEnum {

        ONE,
        TWO,
        THREE,
        FOUR;

    }

}
