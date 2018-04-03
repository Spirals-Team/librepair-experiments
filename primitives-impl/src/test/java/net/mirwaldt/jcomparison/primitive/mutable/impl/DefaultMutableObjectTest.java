package net.mirwaldt.jcomparison.primitive.mutable.impl;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class DefaultMutableObjectTest {
    
    @Test
    public void test_checkIfPrimitive_null() {
        DefaultMutableObject.checkIfPrimitive(null);
    }
    
    @Test
    public void test_checkIfPrimitive_nonPrimitive_anObject() {
        DefaultMutableObject.checkIfPrimitive(new Object());
    }

    @Test
    public void test_checkIfPrimitive_nonPrimitive_aString() {
        DefaultMutableObject.checkIfPrimitive("sString");
    }

    @Test
    public void test_checkIfPrimitive_primitive_aBoolean() {
        assertThrows(IllegalArgumentException.class, () -> DefaultMutableObject.checkIfPrimitive(false));
    }

    @Test
    public void test_checkIfPrimitive_primitive_aByte() {
        assertThrows(IllegalArgumentException.class, () -> DefaultMutableObject.checkIfPrimitive((byte) 0x1E));
    }

    @Test
    public void test_checkIfPrimitive_primitive_aChar() {
        assertThrows(IllegalArgumentException.class, () -> DefaultMutableObject.checkIfPrimitive('a'));
    }

    @Test
    public void test_checkIfPrimitive_primitive_aShort() {
        assertThrows(IllegalArgumentException.class, () -> DefaultMutableObject.checkIfPrimitive((short)1));
    }

    @Test
    public void test_checkIfPrimitive_primitive_anInt() {
        assertThrows(IllegalArgumentException.class, () -> DefaultMutableObject.checkIfPrimitive(0));
    }
    
    @Test
    public void test_checkIfPrimitive_primitive_aLong() {
        assertThrows(IllegalArgumentException.class, () -> DefaultMutableObject.checkIfPrimitive(-1L));
    }
    
    @Test
    public void test_checkIfPrimitive_primitive_aFloat() {
        assertThrows(IllegalArgumentException.class, () -> DefaultMutableObject.checkIfPrimitive(1.0f));
    }

    @Test
    public void test_checkIfPrimitive_primitive_aDouble() {
        assertThrows(IllegalArgumentException.class, () -> DefaultMutableObject.checkIfPrimitive(-0d));
    }
}
