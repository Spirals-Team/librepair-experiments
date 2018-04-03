package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableBytePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntArrayImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 22.06.2017.
 */
public class BasicMultiDimensionalByteArrayTest {

    private final ArrayComparator<byte[][][]> byteArrayComparator = DefaultComparators.createDefaultArrayComparator();

    @Test
    public void test_emptyArray_oneDimension() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{};
        final byte[][][] rightByteArray = new byte[][][]{};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());
        assertFalse(comparisonResult.hasDifferences());
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_emptyArray_twoDimensions() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{}};
        final byte[][][] rightByteArray = new byte[][][]{{}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(leftByteArray[0], similarElements.get(arrayPosition).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_emptyArray_threeDimensions() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{}}};
        final byte[][][] rightByteArray = new byte[][][]{{{}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftByteArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftEmptyTwoDimensionalArray_rightEmptyOneDimensionalArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{}};
        final byte[][][] rightByteArray = new byte[][][]{};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(leftByteArray[0]), additionalItemsOnlyInLeftArray.get(arrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_leftEmptyThreeDimensionalArray_rightEmptyOneDimensionalArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{}}};
        final byte[][][] rightByteArray = new byte[][][]{};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(leftByteArray[0]), additionalItemsOnlyInLeftArray.get(arrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftEmptyOneDimensionalArray_rightEmptyTwoDimensionalArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{};
        final byte[][][] rightByteArray = new byte[][][]{{}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(rightByteArray[0]), additionalItemsOnlyInRightArray.get(arrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_leftEmptyOneDimensionalArray_rightEmptyThreeDimensionalArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{};
        final byte[][][] rightByteArray = new byte[][][]{{{}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(rightByteArray[0]), additionalItemsOnlyInRightArray.get(arrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftEmptyThreeDimensionalArray_rightEmptyTwoDimensionalArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{}}};
        final byte[][][] rightByteArray = new byte[][][]{{}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new DefaultArrayWrapper(leftByteArray[0][0]), additionalItemsOnlyInLeftArray.get(arrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftEmptyTwoDimensionalArray_rightEmptyThreeDimensionalArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{}};
        final byte[][][] rightByteArray = new byte[][][]{{{}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new DefaultArrayWrapper(rightByteArray[0][0]), additionalItemsOnlyInRightArray.get(arrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftArrayEmpty_firstRightElementNullInsteadOfArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{};
        final byte[][][] rightByteArray = new byte[][][]{null};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(rightByteArray[0], additionalItemsOnlyInRightArray.get(arrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstLeftElementNullInsteadOfArray_rightArrayEmpty() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{null};
        final byte[][][] rightByteArray = new byte[][][]{};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(leftByteArray[0], additionalItemsOnlyInLeftArray.get(arrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstLeftElementNullInsteadOfArray_firstRightElementNullInsteadOfArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{null};
        final byte[][][] rightByteArray = new byte[][][]{null};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(leftByteArray[0], similarElements.get(arrayPosition).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftArrayEmpty_firstFirstRightElementNullInsteadOfArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{};
        final byte[][][] rightByteArray = new byte[][][]{{null}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(rightByteArray[0]), additionalItemsOnlyInRightArray.get(arrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstFirstLeftElementNullInsteadOfArray_rightArrayEmpty() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{null}};
        final byte[][][] rightByteArray = new byte[][][]{};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(leftByteArray[0]), additionalItemsOnlyInLeftArray.get(arrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftTwoDimensionalArrayEmpty_firstRightElementNullInsteadOfArray() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{{}};
        final byte[][][] rightByteArray = new byte[][][]{null};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0], rightByteArray[0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstLeftElementNullInsteadOfArray_rightTwoDimensionalArrayEmpty() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{null};
        final byte[][][] rightByteArray = new byte[][][]{{}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0], rightByteArray[0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftThreeDimensionalArrayEmpty_firstRightElementNullInsteadOfArray() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{{{}}};
        final byte[][][] rightByteArray = new byte[][][]{null};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0], rightByteArray[0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstLeftElementNullInsteadOfArray_rightThreeDimensionalArrayEmpty() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{null};
        final byte[][][] rightByteArray = new byte[][][]{{{}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0], rightByteArray[0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftThreeDimensionalArrayEmpty_firstFirstRightElementNullInsteadOfArray() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{{{}}};
        final byte[][][] rightByteArray = new byte[][][]{{null}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstFirstLeftElementNullInsteadOfArray_rightThreeDimensionalArrayEmpty() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{{null}};
        final byte[][][] rightByteArray = new byte[][][]{{{}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstFirstLeftElementNullInsteadOfArray_firstFirstRightElementNullInsteadOfArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{null}};
        final byte[][][] rightByteArray = new byte[][][]{{null}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableTwoElementsImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftByteArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftElementValue_rightOneDimensionalArrayEmpty() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x10}}};
        final byte[][][] rightByteArray = new byte[][][]{};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(leftByteArray[0]), additionalItemsOnlyInLeftArray.get(arrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftElementValue_rightTwoDimensionalArrayEmpty() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x10}}};
        final byte[][][] rightByteArray = new byte[][][]{{}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new DefaultArrayWrapper(leftByteArray[0][0]), additionalItemsOnlyInLeftArray.get(arrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftElementValue_rightThreeDimensionalArrayEmpty() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x10}}};
        final byte[][][] rightByteArray = new byte[][][]{{{}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPosition = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(leftByteArray[0][0][0], additionalItemsOnlyInLeftArray.get(arrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_leftElementValue_firstRightElementNullInsteadOfArray() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{{{0x10}}};
        final byte[][][] rightByteArray = new byte[][][]{null};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0], rightByteArray[0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftElementValue_firstFirstRightElementNullInsteadOfArray() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{{{0x10}}};
        final byte[][][] rightByteArray = new byte[][][]{{null}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0,0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftOneDimensionalArrayEmpty_rightElementValue() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{};
        final byte[][][] rightByteArray = new byte[][][]{{{0x10}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(rightByteArray[0]), additionalItemsOnlyInRightArray.get(arrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftTwoDimensionalArrayEmpty_rightElementValue() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x10}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPosition =  new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new DefaultArrayWrapper(rightByteArray[0][0]), additionalItemsOnlyInRightArray.get(arrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftThreeDimensionalArrayEmpty_rightElementValue() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x10}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPosition = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(rightByteArray[0][0][0], additionalItemsOnlyInRightArray.get(arrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstLeftElementNullInsteadOfArray_rightElementValue() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{{{0x10}}};
        final byte[][][] rightByteArray = new byte[][][]{null};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0], rightByteArray[0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstFirstLeftElementNullInsteadOfArray_rightElementValue() throws Exception {
        final byte[][][] leftByteArray = new byte[][][]{{{0x10}}};
        final byte[][][] rightByteArray = new byte[][][]{{null}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElement() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x1A}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x1A}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPosition = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(leftByteArray[0][0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferenElement() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x1A}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x2B}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(new ImmutableBytePair(leftByteArray[0][0][0], rightByteArray[0][0][0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentAndOneSimilarElement_inTheSameArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x1A, 0x3C}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x1A, 0x2B}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPositionOfSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(leftByteArray[0][0][0], similarElements.get(arrayPositionOfSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 1});
        assertEquals(new ImmutableBytePair(leftByteArray[0][0][1], rightByteArray[0][0][1]), differentValues.get(arrayPositionOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentAndOneSimilarElement_inDifferentArraysOfSecondDimension() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x3C}, {0x1A}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x2B}, {0x1A}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPositionOfSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{0, 1, 0});
        assertEquals(leftByteArray[0][1][0], similarElements.get(arrayPositionOfSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(new ImmutableBytePair(leftByteArray[0][0][0], rightByteArray[0][0][0]), differentValues.get(arrayPositionOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentAndOneSimilarElement_inDifferentArraysOfFirstDimension() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x3C}}, {{0x1A}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x2B}}, {{0x1A}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPositionOfSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{1, 0, 0});
        assertEquals(leftByteArray[1][0][0], similarElements.get(arrayPositionOfSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(new ImmutableBytePair(leftByteArray[0][0][0], rightByteArray[0][0][0]), differentValues.get(arrayPositionOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoDifferentAndTwoSimilarElements_nonMixedInLastDimensionArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x5C, 0x6E}, {0x1A, 0x2B}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x4D, 0x7F}, {0x1A, 0x2B}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(2, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{0, 1, 0});
        assertEquals(leftByteArray[0][1][0], similarElements.get(arrayPositionOfFirstSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfSecondSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{0, 1, 1});
        assertEquals(leftByteArray[0][1][1], similarElements.get(arrayPositionOfSecondSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(2, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(new ImmutableBytePair(leftByteArray[0][0][0], rightByteArray[0][0][0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfSecondPairOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 1});
        assertEquals(new ImmutableBytePair(leftByteArray[0][0][1], rightByteArray[0][0][1]), differentValues.get(arrayPositionOfSecondPairOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoDifferentAndTwoSimilarElements_mixedInLastDimensionArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x5C, 0x1A}, {0x2B, 0x6E}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x4D, 0x1A}, {0x2B, 0x7F}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(2, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 1});
        assertEquals(leftByteArray[0][0][1], similarElements.get(arrayPositionOfFirstSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfSecondSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{0, 1, 0});
        assertEquals(leftByteArray[0][1][0], similarElements.get(arrayPositionOfSecondSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(2, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(new ImmutableBytePair(leftByteArray[0][0][0], rightByteArray[0][0][0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfSecondPairOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 1, 1});
        assertEquals(new ImmutableBytePair(leftByteArray[0][1][1], rightByteArray[0][1][1]), differentValues.get(arrayPositionOfSecondPairOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoDifferentAndTwoSimilarElements_nonMixedInSecondDimensionArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x5C}, {0x6E}}, {{0x1A}, {0x2B}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x4D}, {0x7F}}, {{0x1A}, {0x2B}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(2, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{1, 0, 0});
        assertEquals(leftByteArray[1][0][0], similarElements.get(arrayPositionOfFirstSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfSecondSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{1, 1, 0});
        assertEquals(leftByteArray[1][1][0], similarElements.get(arrayPositionOfSecondSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(2, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(new ImmutableBytePair(leftByteArray[0][0][0], rightByteArray[0][0][0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfSecondPairOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 1, 0});
        assertEquals(new ImmutableBytePair(leftByteArray[0][1][0], rightByteArray[0][1][0]), differentValues.get(arrayPositionOfSecondPairOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoDifferentAndTwoSimilarElements_mixedInSecondDimensionArray() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x5C}, {0x1A}}, {{0x2B}, {0x6E}}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x4D}, {0x1A}}, {{0x2B}, {0x7F}}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(2, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{0, 1, 0});
        assertEquals(leftByteArray[0][1][0], similarElements.get(arrayPositionOfFirstSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfSecondSimilarElement = new ImmutableIntArrayImmutableIntList(new int[]{1, 0, 0});
        assertEquals(leftByteArray[1][0][0], similarElements.get(arrayPositionOfSecondSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(2, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{0, 0, 0});
        assertEquals(new ImmutableBytePair(leftByteArray[0][0][0], rightByteArray[0][0][0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfSecondPairOfDifferentElements = new ImmutableIntArrayImmutableIntList(new int[]{1, 1, 0});
        assertEquals(new ImmutableBytePair(leftByteArray[1][1][0], rightByteArray[1][1][0]), differentValues.get(arrayPositionOfSecondPairOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
