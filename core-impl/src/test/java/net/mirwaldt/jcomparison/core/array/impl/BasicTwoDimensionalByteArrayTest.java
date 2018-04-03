package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableBytePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 20.06.2017.
 */
public class BasicTwoDimensionalByteArrayTest {

    private final ArrayComparator<byte[][]> byteArrayComparator = DefaultComparators.createDefaultArrayComparator();

    @Test
    public void test_bothShallowEmptyArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{};
        final byte[][] rightByteArray = new byte[][]{};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());
        assertFalse(comparisonResult.hasDifferences());
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_bothDeepEmptyArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{}};
        final byte[][] rightByteArray = new byte[][]{{}};

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
    public void test_leftArrayEmpty_firstRightElementEmptyArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{};
        final byte[][] rightByteArray = new byte[][]{{}};

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
    public void test_firstLeftElementEmptyArray_rightArrayEmpty() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{}};
        final byte[][] rightByteArray = new byte[][]{};

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
    public void test_firstLeftElementEmptyArray_firstRightElementNullInsteadOfArray() throws Exception {
        final byte[][] leftByteArray = new byte[][]{{}};
        final byte[][] rightByteArray = new byte[][]{null};

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
    public void test_firstLeftElementNullInsteadOfArray_firstRightElementEmptyArray() throws Exception {
        final byte[][] leftByteArray = new byte[][]{null};
        final byte[][] rightByteArray = new byte[][]{{}};

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
    public void test_oneElementNullInsteadOfArrayAndOneElementEmptyArray_inBothCombinations() throws Exception {
        final byte[][] leftByteArray = new byte[][]{null, {}};
        final byte[][] rightByteArray = new byte[][]{{}, null};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(2, differentValues.size());
        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableOneElementImmutableIntList(0);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[0], rightByteArray[0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));
        final ImmutableIntList arrayPositionOfSecondPairOfDifferentElements = new ImmutableOneElementImmutableIntList(1);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[1], rightByteArray[1]), differentValues.get(arrayPositionOfSecondPairOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftArrayEmpty_firstRightElementNullInsteadOfArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{};
        final byte[][] rightByteArray = new byte[][]{null};

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
        final byte[][] leftByteArray = new byte[][]{null};
        final byte[][] rightByteArray = new byte[][]{};

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
    public void test_emptyLeftArray_oneRightElement() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{};
        final byte[][] rightByteArray = new byte[][]{{0x1B}};

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
    public void test_oneLeftElement_emptyRightArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x1A}};
        final byte[][] rightByteArray = new byte[][]{};

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
    public void test_emptyLeftArray_twoRightElements() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{};
        final byte[][] rightByteArray = new byte[][]{{0x1B, 0x2C}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());

        final ImmutableIntList firstArrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(rightByteArray[0]), additionalItemsOnlyInRightArray.get(firstArrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoLeftElements_emptyRightArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x1A, 0x2B}};
        final byte[][] rightByteArray = new byte[][]{};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());

        final ImmutableIntList firstArrayPosition = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new DefaultArrayWrapper(leftByteArray[0]), additionalItemsOnlyInLeftArray.get(firstArrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstLeftElementNullInsteadOfArray_firstRightElementNullInsteadOfArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{null};
        final byte[][] rightByteArray = new byte[][]{null};

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
    public void test_firstLeftElementNullInsteadOfArray_firstRightElementValue() throws Exception {
        final byte[][] leftByteArray = new byte[][]{null};
        final byte[][] rightByteArray = new byte[][]{{0x2B}};

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
    public void test_firstLeftElementValue_firstRightElementNullInsteadOfArray() throws Exception {
        final byte[][] leftByteArray = new byte[][]{{0x1A}};
        final byte[][] rightByteArray = new byte[][]{null};

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
    public void test_firstLeftElementEmptyArray_firstRightElementValue() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{}};
        final byte[][] rightByteArray = new byte[][]{{0x2B}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());

        final ImmutableIntList firstArrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(rightByteArray[0][0], additionalItemsOnlyInRightArray.get(firstArrayPosition));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_firstLeftElementValue_firstRightElementEmptyArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x1A}};
        final byte[][] rightByteArray = new byte[][]{{}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());

        final ImmutableIntList firstArrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftByteArray[0][0], additionalItemsOnlyInLeftArray.get(firstArrayPosition));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElement() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x1A}};
        final byte[][] rightByteArray = new byte[][]{{0x1A}};

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
    public void test_oneDifferenElement() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x1A}};
        final byte[][] rightByteArray = new byte[][]{{0x2B}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new ImmutableBytePair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentAndOneSimilarElement_inTheSameArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x1A, 0x3C}};
        final byte[][] rightByteArray = new byte[][]{{0x1A, 0x2B}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPositionOfSimilarElement = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftByteArray[0][0], similarElements.get(arrayPositionOfSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElements = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableBytePair(leftByteArray[0][1], rightByteArray[0][1]), differentValues.get(arrayPositionOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentAndOneSimilarElement_inDifferentArrays() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x3C}, {0x1A}};
        final byte[][] rightByteArray = new byte[][]{{0x2B}, {0x1A}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPositionOfSimilarElement = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertEquals(leftByteArray[1][0], similarElements.get(arrayPositionOfSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElements = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new ImmutableBytePair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPositionOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoDifferentAndTwoSimilarElements_nonMixed() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x5C, 0x6E}, {0x1A, 0x2B}};
        final byte[][] rightByteArray = new byte[][]{{0x4D, 0x7F}, {0x1A, 0x2B}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(2, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertEquals(leftByteArray[1][0], similarElements.get(arrayPositionOfFirstSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfSecondSimilarElement = new ImmutableTwoElementsImmutableIntList(1, 1);
        assertEquals(leftByteArray[1][1], similarElements.get(arrayPositionOfSecondSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(2, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new ImmutableBytePair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfSecondPairOfDifferentElements = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableBytePair(leftByteArray[0][1], rightByteArray[0][1]), differentValues.get(arrayPositionOfSecondPairOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoDifferentAndTwoSimilarElements_mixed() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x5C, 0x1A}, {0x2B, 0x6E}};
        final byte[][] rightByteArray = new byte[][]{{0x4D, 0x1A}, {0x2B, 0x7F}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(2, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(leftByteArray[0][1], similarElements.get(arrayPositionOfFirstSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfSecondSimilarElement = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertEquals(leftByteArray[1][0], similarElements.get(arrayPositionOfSecondSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(2, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new ImmutableBytePair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfSecondPairOfDifferentElements = new ImmutableTwoElementsImmutableIntList(1, 1);
        assertEquals(new ImmutableBytePair(leftByteArray[1][1], rightByteArray[1][1]), differentValues.get(arrayPositionOfSecondPairOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_nearlyAllTogether_leftAdditional() throws Exception {
        final byte[][] leftByteArray = new byte[][]{{0x3C}, {0x1A}, {}, null, {}, null, null, {0x5F}, {}, {0x7B}, {0x7C}};
        final byte[][] rightByteArray = new byte[][]{{0x2B}, {0x1A}, {}, {}, null, null, {0x4E}, null, {0x6A}, {}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(3, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertEquals(leftByteArray[1][0], similarElements.get(arrayPositionOfFirstSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfSecondSimilarElement = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftByteArray[2], similarElements.get(arrayPositionOfSecondSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfThirdSimilarElement = new ImmutableOneElementImmutableIntList(5);
        assertEquals(leftByteArray[5], similarElements.get(arrayPositionOfThirdSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();


        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(2, additionalItemsOnlyInLeftArray.size());

        final ImmutableIntList arrayPositionOfFirstAdditionalElementOnlyInLeftArray = new ImmutableTwoElementsImmutableIntList(9, 0);
        assertEquals(leftByteArray[9][0], additionalItemsOnlyInLeftArray.get(arrayPositionOfFirstAdditionalElementOnlyInLeftArray));

        final ImmutableIntList arrayPositionOfSecondAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(10);
        assertEquals(new DefaultArrayWrapper(leftByteArray[10]), additionalItemsOnlyInLeftArray.get(arrayPositionOfSecondAdditionalElementOnlyInLeftArray));


        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(5, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new ImmutableBytePair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfSecondPairOfDifferentElements = new ImmutableOneElementImmutableIntList(3);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[3], rightByteArray[3]), differentValues.get(arrayPositionOfSecondPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfThirdPairOfDifferentElements = new ImmutableOneElementImmutableIntList(4);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[4], rightByteArray[4]), differentValues.get(arrayPositionOfThirdPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfFourthPairOfDifferentElements = new ImmutableOneElementImmutableIntList(6);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[6], rightByteArray[6]), differentValues.get(arrayPositionOfFourthPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfFifthPairOfDifferentElements = new ImmutableOneElementImmutableIntList(7);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[7], rightByteArray[7]), differentValues.get(arrayPositionOfFifthPairOfDifferentElements));


        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());

        final ImmutableIntList arrayPositionOfSecondAdditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(8, 0);
        assertEquals(rightByteArray[8][0], additionalItemsOnlyInRightArray.get(arrayPositionOfSecondAdditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_nearlyAllTogether_rightAdditional() throws Exception {
        final byte[][] leftByteArray = new byte[][]{{0x3C}, {0x1A}, {}, null, {}, null, null, {0x5F}, {}, {0x7B}};
        final byte[][] rightByteArray = new byte[][]{{0x2B}, {0x1A}, {}, {}, null, null, {0x4E}, null, {0x6A}, {}, {0x7D}};

        final ArrayComparisonResult comparisonResult = byteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(3, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertEquals(leftByteArray[1][0], similarElements.get(arrayPositionOfFirstSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfSecondSimilarElement = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftByteArray[2], similarElements.get(arrayPositionOfSecondSimilarElement).getSimilarValue());

        final ImmutableIntList arrayPositionOfThirdSimilarElement = new ImmutableOneElementImmutableIntList(5);
        assertEquals(leftByteArray[5], similarElements.get(arrayPositionOfThirdSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();


        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());

        final ImmutableIntList arrayPositionOFirstAdditionalElementOnlyInLeftArray = new ImmutableTwoElementsImmutableIntList(9, 0);
        assertEquals(leftByteArray[9][0], additionalItemsOnlyInLeftArray.get(arrayPositionOFirstAdditionalElementOnlyInLeftArray));


        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(5, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new ImmutableBytePair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfSecondPairOfDifferentElements = new ImmutableOneElementImmutableIntList(3);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[3], rightByteArray[3]), differentValues.get(arrayPositionOfSecondPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfThirdPairOfDifferentElements = new ImmutableOneElementImmutableIntList(4);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[4], rightByteArray[4]), differentValues.get(arrayPositionOfThirdPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfFourthPairOfDifferentElements = new ImmutableOneElementImmutableIntList(6);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[6], rightByteArray[6]), differentValues.get(arrayPositionOfFourthPairOfDifferentElements));

        final ImmutableIntList arrayPositionOfFifthPairOfDifferentElements = new ImmutableOneElementImmutableIntList(7);
        assertEquals(ValueComparators.pairFactory.createPair(leftByteArray[7], rightByteArray[7]), differentValues.get(arrayPositionOfFifthPairOfDifferentElements));


        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(2, additionalItemsOnlyInRightArray.size());

        final ImmutableIntList arrayPositionOfFirstAdditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(8, 0);
        assertEquals(rightByteArray[8][0], additionalItemsOnlyInRightArray.get(arrayPositionOfFirstAdditionalElementOnlyInRightArray));

        final ImmutableIntList arrayPositionOfSecondAdditionalElementOnlyInRightArray = new ImmutableOneElementImmutableIntList(10);
        assertEquals(new DefaultArrayWrapper(rightByteArray[10]), additionalItemsOnlyInRightArray.get(arrayPositionOfSecondAdditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
