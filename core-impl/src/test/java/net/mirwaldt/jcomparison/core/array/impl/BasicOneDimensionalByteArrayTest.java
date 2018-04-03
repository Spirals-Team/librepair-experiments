package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableBytePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 20.06.2017.
 */
public class BasicOneDimensionalByteArrayTest {

    private final ArrayComparator<byte[]> byteArrayComparator = DefaultComparators.createDefaultArrayComparator();

    @Test
    public void test_bothEmpty() throws ComparisonFailedException {
        final byte[] leftByteArray = new byte[] {};
        final byte[] rightByteArray = new byte[] {};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());
        assertFalse(comparisonResult.hasDifferences());
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftEmpty() throws ComparisonFailedException {
        final byte[] leftByteArray = new byte[] {};
        final byte[] rightByteArray = new byte[] { 0x1C };

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        assertEquals(rightByteArray[0], additionalItemsOnlyInRightArray.get(new ImmutableOneElementImmutableIntList(0)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_rightEmpty() throws ComparisonFailedException {
        final byte[] leftByteArray = new byte[] { 0x0F };
        final byte[] rightByteArray = new byte[] {  };

        final ArrayComparisonResult comparisonResult =  byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        assertEquals(leftByteArray[0], additionalItemsOnlyInLeftArray.get(new ImmutableOneElementImmutableIntList(0)));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElement() throws ComparisonFailedException {
        final byte[] leftByteArray = new byte[] { 0x5E };
        final byte[] rightByteArray = new byte[] { 0x5E };

        final ArrayComparisonResult comparisonResult =  byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        assertEquals(leftByteArray[0], similarElements.get(new ImmutableOneElementImmutableIntList(0)).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentElement() throws ComparisonFailedException {
        final byte[] leftByteArray = new byte[] { 0x5C };
        final byte[] rightByteArray = new byte[] { 0x4F };

        final ArrayComparisonResult comparisonResult =  byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        assertEquals(new ImmutableBytePair(leftByteArray[0], rightByteArray[0]), differentValues.get(new ImmutableOneElementImmutableIntList(0)));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_oneSimilarElement_oneAdditionalElementInLeftArray() throws ComparisonFailedException {
        final byte[] leftByteArray = new byte[] { 0x5E, 0x6C };
        final byte[] rightByteArray = new byte[] { 0x5E };

        final ArrayComparisonResult comparisonResult =  byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        assertEquals(leftByteArray[0], similarElements.get(new ImmutableOneElementImmutableIntList(0)).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        assertEquals(leftByteArray[1], additionalItemsOnlyInLeftArray.get(new ImmutableOneElementImmutableIntList(1)));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentElement_oneAdditionalElementInRightArray() throws ComparisonFailedException {
        final byte[] leftByteArray = new byte[] { 0x5C };
        final byte[] rightByteArray = new byte[] { 0x4F, 0x6A };

        final ArrayComparisonResult comparisonResult =  byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        assertEquals(new ImmutableBytePair(leftByteArray[0], rightByteArray[0]), differentValues.get(new ImmutableOneElementImmutableIntList(0)));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        assertEquals(rightByteArray[1], additionalItemsOnlyInRightArray.get(new ImmutableOneElementImmutableIntList(1)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentAndOneSimilarElement() throws ComparisonFailedException {
        final byte[] leftByteArray = new byte[] { 0x3B, 0x7A };
        final byte[] rightByteArray = new byte[] { 0x3B, 0x6A };

        final ArrayComparisonResult comparisonResult =  byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        assertEquals(leftByteArray[0], similarElements.get(new ImmutableOneElementImmutableIntList(0)).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        assertEquals(new ImmutableBytePair(leftByteArray[1], rightByteArray[1]), differentValues.get(new ImmutableOneElementImmutableIntList(1)));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoDifferentAndTwoSimilarElements() throws ComparisonFailedException {
        final byte[] leftByteArray = new byte[] { 0x3B, 0x7A, 0x4C, 0x2D };
        final byte[] rightByteArray = new byte[] { 0x3B, 0x6A, 0x4C, 0x1D };

        final ArrayComparisonResult comparisonResult =  byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(2, similarElements.size());
        assertEquals(leftByteArray[0], similarElements.get(new ImmutableOneElementImmutableIntList(0)).getSimilarValue());
        assertEquals(leftByteArray[2], similarElements.get(new ImmutableOneElementImmutableIntList(2)).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(2, differentValues.size());
        assertEquals(new ImmutableBytePair(leftByteArray[1], rightByteArray[1]), differentValues.get(new ImmutableOneElementImmutableIntList(1)));
        assertEquals(new ImmutableBytePair(leftByteArray[3], rightByteArray[3]), differentValues.get(new ImmutableOneElementImmutableIntList(3)));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
