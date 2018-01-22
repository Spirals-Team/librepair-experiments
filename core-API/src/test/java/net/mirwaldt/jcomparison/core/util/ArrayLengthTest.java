package net.mirwaldt.jcomparison.core.util;

import net.mirwaldt.jcomparison.core.util.ArrayAccessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Michael on 22.06.2017.
 */
public class ArrayLengthTest {

    @Test
    public void test_nonArray() {
        assertThrows(IllegalArgumentException.class, () -> ArrayAccessor.getLength(new Object()));
    }

    @Test
    public void test_oneDimensionalObjectArray_empty() {
        assertEquals(0, ArrayAccessor.getLength(new Object[0]));
    }

    @Test
    public void test_oneDimensionalObjectArray() {
        assertEquals(1, ArrayAccessor.getLength(new Object[1]));
    }

    @Test
    public void test_twoDimensionalObjectArray() {
        assertEquals(2, ArrayAccessor.getLength(new Object[2][3]));
    }



    @Test
    public void test_oneDimensionalStringArray_empty() {
        assertEquals(0, ArrayAccessor.getLength(new String[0]));
    }

    @Test
    public void test_oneDimensionalStringArray() {
        assertEquals(1, ArrayAccessor.getLength(new String[1]));
    }

    @Test
    public void test_twoDimensionalStringArray() {
        assertEquals(2, ArrayAccessor.getLength(new String[2][3]));
    }




    @Test
    public void test_oneDimensionalByteArray_empty() {
        assertEquals(0, ArrayAccessor.getLength(new byte[0]));
    }

    @Test
    public void test_oneDimensionalByteArray() {
        assertEquals(1, ArrayAccessor.getLength(new byte[1]));
    }

    @Test
    public void test_twoDimensionalByteArray() {
        assertEquals(2, ArrayAccessor.getLength(new byte[2][3]));
    }



    @Test
    public void test_oneDimensionalCharArray_empty() {
        assertEquals(0, ArrayAccessor.getLength(new char[0]));
    }

    @Test
    public void test_oneDimensionalCharArray() {
        assertEquals(1, ArrayAccessor.getLength(new char[1]));
    }

    @Test
    public void test_twoDimensionalCharArray() {
        assertEquals(2, ArrayAccessor.getLength(new char[2][3]));
    }




    @Test
    public void test_oneDimensionalShortArray_empty() {
        assertEquals(0, ArrayAccessor.getLength(new short[0]));
    }

    @Test
    public void test_oneDimensionalShortArray() {
        assertEquals(1, ArrayAccessor.getLength(new short[1]));
    }

    @Test
    public void test_twoDimensionalShortArray() {
        assertEquals(2, ArrayAccessor.getLength(new short[2][3]));
    }



    @Test
    public void test_oneDimensionalIntArray_empty() {
        assertEquals(0, ArrayAccessor.getLength(new int[0]));
    }

    @Test
    public void test_oneDimensionalIntArray() {
        assertEquals(1, ArrayAccessor.getLength(new int[1]));
    }

    @Test
    public void test_twoDimensionalIntArray() {
        assertEquals(2, ArrayAccessor.getLength(new int[2][3]));
    }



    @Test
    public void test_oneDimensionalLongArray_empty() {
        assertEquals(0, ArrayAccessor.getLength(new long[0]));
    }

    @Test
    public void test_oneDimensionalLongArray() {
        assertEquals(1, ArrayAccessor.getLength(new long[1]));
    }

    @Test
    public void test_twoDimensionalLongArray() {
        assertEquals(2, ArrayAccessor.getLength(new long[2][3]));
    }



    @Test
    public void test_oneDimensionalFloatArray_empty() {
        assertEquals(0, ArrayAccessor.getLength(new float[0]));
    }

    @Test
    public void test_oneDimensionalFloatArray() {
        assertEquals(1, ArrayAccessor.getLength(new float[1]));
    }

    @Test
    public void test_twoDimensionalFloatArray() {
        assertEquals(2, ArrayAccessor.getLength(new float[2][3]));
    }



    @Test
    public void test_oneDimensionalDoubleArray_empty() {
        assertEquals(0, ArrayAccessor.getLength(new double[0]));
    }

    @Test
    public void test_oneDimensionalDoubleArray() {
        assertEquals(1, ArrayAccessor.getLength(new double[1]));
    }

    @Test
    public void test_twoDimensionalDoubleArray() {
        assertEquals(2, ArrayAccessor.getLength(new double[2][3]));
    }
}
