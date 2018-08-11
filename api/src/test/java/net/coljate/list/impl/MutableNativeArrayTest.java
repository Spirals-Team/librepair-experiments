package net.coljate.list.impl;

import net.coljate.NewObjectCreator;
import net.coljate.list.MutableArrayTest;

import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

/**
 *
 * @author Ollie
 */
@SuppressWarnings({"unchecked", "rawtypes"})
public class MutableNativeArrayTest {

    @Nested
    class EmptyNativeArrayTest extends NewObjectCreator implements MutableArrayTest.ZeroElementTests<Object> {

        @Override
        public MutableNativeArray<Object> createTestCollection() {
            return MutableNativeArray.viewOf(new Object[0]);
        }

        @Test
        @Override
        public void testPrefix() {
            assertThrows(ArrayIndexOutOfBoundsException.class, MutableArrayTest.ZeroElementTests.super::testPrefix);
        }

        @Test
        @Override
        public void testSuffix() {
            assertThrows(ArrayIndexOutOfBoundsException.class, MutableArrayTest.ZeroElementTests.super::testSuffix);
        }

    }

}
