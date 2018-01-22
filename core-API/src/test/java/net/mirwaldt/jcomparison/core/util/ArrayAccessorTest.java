package net.mirwaldt.jcomparison.core.util;

import net.mirwaldt.jcomparison.core.util.ArrayAccessor;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Created by Michael on 22.06.2017.
 */
public class ArrayAccessorTest {

    @Test
    public void test_nonArray() {
        assertThrows(IllegalArgumentException.class, () -> ArrayAccessor.getElementAtIndex(new Object(), 0));
    }
    
    // Object ----------------------------------------------------------------------------------------------------------

    @Test
    public void test_oneDimensionalObjectArray_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new Object[]{}, 0));
    }

    @Test
    public void test_oneDimensionalObjectArray_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new Object[]{}, -1));
    }

    @Test
    public void test_oneDimensionalObjectArray_oneObject() {
        final Object firstObject = new Object();
        assertEquals(firstObject, ArrayAccessor.getElementAtIndex(new Object[]{firstObject}, 0));
    }

    @Test
    public void test_oneDimensionalObjectArray_twoObject_pickFirst() {
        final Object firstObject = new Object();
        final Object secondObject = new Object();
        assertEquals(firstObject, ArrayAccessor.getElementAtIndex(new Object[]{firstObject, secondObject}, 0));
    }

    @Test
    public void test_oneDimensionalObjectArray_twoObjects_pickSecond() {
        final Object firstObject = new Object();
        final Object secondObject = new Object();
        assertEquals(secondObject, ArrayAccessor.getElementAtIndex(new Object[]{firstObject, secondObject}, 1));
    }

    @Test
    public void test_oneDimensionalObjectArray_twoObjects_pickThird() {
        final Object firstObject = new Object();
        final Object secondObject = new Object();
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new Object[]{firstObject, secondObject}, 2));
    }

    @Test
    public void test_twoDimensionalObjectArray_oneObject() {
        final Object[] objectArray = new Object[0];
        assertEquals(objectArray, ArrayAccessor.getElementAtIndex(new Object[][]{objectArray}, 0));
    }
    
    // String ----------------------------------------------------------------------------------------------------------

    @Test
    public void test_oneDimensionalStringArray_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new String[]{}, 0));
    }

    @Test
    public void test_oneDimensionalStringArray_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new String[]{}, -1));
    }

    @Test
    public void test_oneDimensionalStringArray_oneString() {
        final String firstString = "string1";
        assertEquals(firstString, ArrayAccessor.getElementAtIndex(new String[]{firstString}, 0));
    }

    @Test
    public void test_oneDimensionalStringArray_twoStrings_pickFirst() {
        final String firstString = "string1";
        final String secondString = "string2";
        assertEquals(firstString, ArrayAccessor.getElementAtIndex(new String[]{firstString, secondString}, 0));
    }

    @Test
    public void test_oneDimensionalStringArray_twoStrings_pickSecond() {
        final String firstString = "string1";
        final String secondString = "string2";
        assertEquals(secondString, ArrayAccessor.getElementAtIndex(new String[]{firstString, secondString}, 1));
    }

    @Test
    public void test_oneDimensionalStringArray_twoStrings_pickThird() {
        final String firstString = "string1";
        final String secondString = "string2";
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new String[]{firstString, secondString}, 2));
    }

    @Test
    public void test_twoDimensionalStringArray_oneStringArray() {
        final String[] stringArray = new String[0];
        assertEquals(stringArray, ArrayAccessor.getElementAtIndex(new String[][]{stringArray}, 0));
    }
    
    // byte ------------------------------------------------------------------------------------------------------------

    @Test
    public void test_oneDimensionalByteArray_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new byte[]{}, 0));
    }

    @Test
    public void test_oneDimensionalByteArray_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new byte[]{}, -1));
    }

    @Test
    public void test_oneDimensionalByteArray_oneByte() {
        final byte firstByte = 0x01;
        assertEquals(firstByte, ArrayAccessor.getElementAtIndex(new byte[]{firstByte}, 0));
    }

    @Test
    public void test_oneDimensionalByteArray_twoBytes_pickFirst() {
        final byte firstByte = 0x01;
        final byte secondByte = 0x02;
        assertEquals(firstByte, ArrayAccessor.getElementAtIndex(new byte[]{firstByte, secondByte}, 0));
    }

    @Test
    public void test_oneDimensionalByteArray_twoBytes_pickSecond() {
        final byte firstByte = 0x01;
        final byte secondByte = 0x02;
        assertEquals(secondByte, ArrayAccessor.getElementAtIndex(new byte[]{firstByte, secondByte}, 1));
    }

    @Test
    public void test_oneDimensionalByteArray_twoBytes_pickThird() {
        final byte firstByte = 0x01;
        final byte secondByte = 0x02;
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new byte[]{firstByte, secondByte}, 2));
    }

    @Test
    public void test_twoDimensionalByteArray_oneByteArray() {
        final byte[] byteArray = new byte[0];
        assertEquals(byteArray, ArrayAccessor.getElementAtIndex(new byte[][]{byteArray}, 0));
    }

    // char ------------------------------------------------------------------------------------------------------------

    @Test
    public void test_oneDimensionalCharArray_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new char[]{}, 0));
    }

    @Test
    public void test_oneDimensionalCharArray_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new char[]{}, -1));
    }

    @Test
    public void test_oneDimensionalCharArray_oneChar() {
        final char firstChar = 'a';
        assertEquals(firstChar, ArrayAccessor.getElementAtIndex(new char[]{firstChar}, 0));
    }

    @Test
    public void test_oneDimensionalCharArray_twoChars_pickFirst() {
        final char firstChar = 'a';
        final char secondChar = 'b';
        assertEquals(firstChar, ArrayAccessor.getElementAtIndex(new char[]{firstChar, secondChar}, 0));
    }

    @Test
    public void test_oneDimensionalCharArray_twoChars_pickSecond() {
        final char firstChar = 'a';
        final char secondChar = 'b';
        assertEquals(secondChar, ArrayAccessor.getElementAtIndex(new char[]{firstChar, secondChar}, 1));
    }

    @Test
    public void test_oneDimensionalCharArray_twoChars_pickThird() {
        final char firstChar = 'a';
        final char secondChar = 'b';
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new char[]{firstChar, secondChar}, 2));
    }

    @Test
    public void test_twoDimensionalCharArray_oneCharArray() {
        final char[] charArray = new char[0];
        assertEquals(charArray, ArrayAccessor.getElementAtIndex(new char[][]{charArray}, 0));
    }
    
    // short -----------------------------------------------------------------------------------------------------------

    @Test
    public void test_oneDimensionalShortArray_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new short[]{}, 0));
    }

    @Test
    public void test_oneDimensionalShorteArray_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new short[]{}, -1));
    }

    @Test
    public void test_oneDimensionalShortArray_oneShort() {
        final short firstShort = 1;
        assertEquals(firstShort, ArrayAccessor.getElementAtIndex(new short[]{firstShort}, 0));
    }

    @Test
    public void test_oneDimensionalShortArray_twoShorts_pickFirst() {
        final short firstShort = 1;
        final short secondShort = 2;
        assertEquals(firstShort, ArrayAccessor.getElementAtIndex(new short[]{firstShort, secondShort}, 0));
    }

    @Test
    public void test_oneDimensionalShortArray_twoShorts_pickSecond() {
        final short firstShort = 1;
        final short secondShort = 2;
        assertEquals(secondShort, ArrayAccessor.getElementAtIndex(new short[]{firstShort, secondShort}, 1));
    }

    @Test
    public void test_oneDimensionalShortArray_twoShorts_pickThird() {
        final short firstShort = 1;
        final short secondShort = 2;
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new short[]{firstShort, secondShort}, 2));
    }

    @Test
    public void test_twoDimensionalShortArray_oneShortArray() {
        final short[] shortArray = new short[0];
        assertEquals(shortArray, ArrayAccessor.getElementAtIndex(new short[][]{shortArray}, 0));
    }

    // int -------------------------------------------------------------------------------------------------------------

    @Test
    public void test_oneDimensionalIntArray_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new int[]{}, 0));
    }

    @Test
    public void test_oneDimensionalInteArray_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new int[]{}, -1));
    }

    @Test
    public void test_oneDimensionalIntArray_oneInt() {
        final int firstInt = 1;
        assertEquals(firstInt, ArrayAccessor.getElementAtIndex(new int[]{firstInt}, 0));
    }

    @Test
    public void test_oneDimensionalIntArray_twoInts_pickFirst() {
        final int firstInt = 1;
        final int secondInt = 2;
        assertEquals(firstInt, ArrayAccessor.getElementAtIndex(new int[]{firstInt, secondInt}, 0));
    }

    @Test
    public void test_oneDimensionalIntArray_twoInts_pickSecond() {
        final int firstInt = 1;
        final int secondInt = 2;
        assertEquals(secondInt, ArrayAccessor.getElementAtIndex(new int[]{firstInt, secondInt}, 1));
    }

    @Test
    public void test_oneDimensionalIntArray_twoInts_pickThird() {
        final int firstInt = 1;
        final int secondInt = 2;
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new int[]{firstInt, secondInt}, 2));
    }

    @Test
    public void test_twoDimensionalIntArray_oneIntArray() {
        final int[] intArray = new int[0];
        assertEquals(intArray, ArrayAccessor.getElementAtIndex(new int[][]{intArray}, 0));
    }
    
    // long ------------------------------------------------------------------------------------------------------------

    @Test
    public void test_oneDimensionalLongArray_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new long[]{}, 0));
    }

    @Test
    public void test_oneDimensionalLongeArray_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new long[]{}, -1));
    }

    @Test
    public void test_oneDimensionalLongArray_oneLong() {
        final long firstLong = 1;
        assertEquals(firstLong, ArrayAccessor.getElementAtIndex(new long[]{firstLong}, 0));
    }

    @Test
    public void test_oneDimensionalLongArray_twoLongs_pickFirst() {
        final long firstLong = 1;
        final long secondLong = 2;
        assertEquals(firstLong, ArrayAccessor.getElementAtIndex(new long[]{firstLong, secondLong}, 0));
    }

    @Test
    public void test_oneDimensionalLongArray_twoLongs_pickSecond() {
        final long firstLong = 1;
        final long secondLong = 2;
        assertEquals(secondLong, ArrayAccessor.getElementAtIndex(new long[]{firstLong, secondLong}, 1));
    }

    @Test
    public void test_oneDimensionalLongArray_twoLongs_pickThird() {
        final long firstLong = 1;
        final long secondLong = 2;
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new long[]{firstLong, secondLong}, 2));
    }

    @Test
    public void test_twoDimensionalLongArray_oneLongArray() {
        final long[] longArray = new long[0];
        assertEquals(longArray, ArrayAccessor.getElementAtIndex(new long[][]{longArray}, 0));
    }

    // float -----------------------------------------------------------------------------------------------------------

    @Test
    public void test_oneDimensionalFloatArray_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new float[]{}, 0));
    }

    @Test
    public void test_oneDimensionalFloateArray_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new float[]{}, -1));
    }

    @Test
    public void test_oneDimensionalFloatArray_oneFloat() {
        final float firstFloat = 1.0f;
        assertEquals(firstFloat, ArrayAccessor.getElementAtIndex(new float[]{firstFloat}, 0));
    }

    @Test
    public void test_oneDimensionalFloatArray_twoFloats_pickFirst() {
        final float firstFloat = 1.0f;
        final float secondFloat = 2.0f;
        assertEquals(firstFloat, ArrayAccessor.getElementAtIndex(new float[]{firstFloat, secondFloat}, 0));
    }

    @Test
    public void test_oneDimensionalFloatArray_twoFloats_pickSecond() {
        final float firstFloat = 1.0f;
        final float secondFloat = 2.0f;
        assertEquals(secondFloat, ArrayAccessor.getElementAtIndex(new float[]{firstFloat, secondFloat}, 1));
    }

    @Test
    public void test_oneDimensionalFloatArray_twoFloats_pickThird() {
        final float firstFloat = 1.0f;
        final float secondFloat = 2.0f;
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new float[]{firstFloat, secondFloat}, 2));
    }

    @Test
    public void test_twoDimensionalFloatArray_oneFloatArray() {
        final float[] floatArray = new float[0];
        assertEquals(floatArray, ArrayAccessor.getElementAtIndex(new float[][]{floatArray}, 0));
    }

    // double ----------------------------------------------------------------------------------------------------------

    @Test
    public void test_oneDimensionalDoubleArray_empty() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new double[]{}, 0));
    }

    @Test
    public void test_oneDimensionalDoubleeArray_negativeIndex() {
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new double[]{}, -1));
    }

    @Test
    public void test_oneDimensionalDoubleArray_oneDouble() {
        final double firstDouble = 1.0d;
        assertEquals(firstDouble, ArrayAccessor.getElementAtIndex(new double[]{firstDouble}, 0));
    }

    @Test
    public void test_oneDimensionalDoubleArray_twoDoubles_pickFirst() {
        final double firstDouble = 1.0d;
        final double secondDouble = 2.0d;
        assertEquals(firstDouble, ArrayAccessor.getElementAtIndex(new double[]{firstDouble, secondDouble}, 0));
    }

    @Test
    public void test_oneDimensionalDoubleArray_twoDoubles_pickSecond() {
        final double firstDouble = 1.0d;
        final double secondDouble = 2.0d;
        assertEquals(secondDouble, ArrayAccessor.getElementAtIndex(new double[]{firstDouble, secondDouble}, 1));
    }

    @Test
    public void test_oneDimensionalDoubleArray_twoDoubles_pickThird() {
        final double firstDouble = 1.0d;
        final double secondDouble = 2.0d;
        assertThrows(IndexOutOfBoundsException.class, () -> ArrayAccessor.getElementAtIndex(new double[]{firstDouble, secondDouble}, 2));
    }

    @Test
    public void test_twoDimensionalDoubleArray_oneDoubleArray() {
        final double[] doubleArray = new double[0];
        assertEquals(doubleArray, ArrayAccessor.getElementAtIndex(new double[][]{doubleArray}, 0));
    }
}
