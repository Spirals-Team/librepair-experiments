package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableBytePair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntArrayImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 06.07.2017.
 */
public class FirstHitArrayTest {

    private final ArrayComparator<byte[][]> twoDimensionalByteArrayComparator = DefaultComparators.<byte[][]>createDefaultArrayComparatorBuilder().findFirstResultOnly().build();
    private final ArrayComparator<byte[][][]> threeDimensionalByteArrayComparator = DefaultComparators.<byte[][][]>createDefaultArrayComparatorBuilder().findFirstResultOnly().build();

    @Test
    public void test_differentValues() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x3C}, {0x1A}};
        final byte[][] rightByteArray = new byte[][]{{0x2B}, {0x1A}};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();


        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());


        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(new ImmutableBytePair(leftByteArray[0][0], rightByteArray[0][0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));


        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_differentValues_leftEmptyArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{}, {0x1A}};
        final byte[][] rightByteArray = new byte[][]{{0x2B}, {0x1A}};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();


        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(rightByteArray[0][0], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_differentValues_rightEmptyArray() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x3C}, {0x1A}};
        final byte[][] rightByteArray = new byte[][]{{}, {0x1A}};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();


        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInLeftArray = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftByteArray[0][0], additionalItemsOnlyInLeftArray.get(arrayPositionOfadditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_differentValues_leftNull() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{null, {0x1A}};
        final byte[][] rightByteArray = new byte[][]{{0x2B}, {0x1A}};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();


        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());


        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new ImmutablePair<>(leftByteArray[0], rightByteArray[0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));


        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_differentValues_rightNull() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x3C}, {0x1A}};
        final byte[][] rightByteArray = new byte[][]{null, {0x1A}};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();


        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());


        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());

        final ImmutableIntList arrayPositionOfFirstPairOfDifferentElements = new ImmutableOneElementImmutableIntList(0);
        assertEquals(new ImmutablePair<>(leftByteArray[0], rightByteArray[0]), differentValues.get(arrayPositionOfFirstPairOfDifferentElements));


        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_similarValues() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x1A}, {0x3C}};
        final byte[][] rightByteArray = new byte[][]{{0x1A}, {0x2B}};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftByteArray[0][0], similarElements.get(arrayPositionOfFirstSimilarElement));


        assertFalse(comparisonResult.hasDifference());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_similarEmptyArrays() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{}, {0x3C}};
        final byte[][] rightByteArray = new byte[][]{{}, {0x2B}};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPositionOfFirstSimilarElement = new ImmutableOneElementImmutableIntList(0);
        assertEquals(leftByteArray[0], similarElements.get(arrayPositionOfFirstSimilarElement));


        assertFalse(comparisonResult.hasDifference());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftAdditional_values() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{0x3C}, {0x1A}};
        final byte[][] rightByteArray = new byte[][]{};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(0);
        assertEquals(leftByteArray[0], additionalItemsOnlyInLeftArray.get(arrayPositionOfadditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_rightAdditional_values() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{};
        final byte[][] rightByteArray = new byte[][]{{0x1A}, {0x2B}};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableOneElementImmutableIntList(0);
        assertEquals(rightByteArray[0], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftAdditional_nulls() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{null, null};
        final byte[][] rightByteArray = new byte[][]{};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(0);
        assertEquals(leftByteArray[0], additionalItemsOnlyInLeftArray.get(arrayPositionOfadditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_rightAdditional_nulls() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{};
        final byte[][] rightByteArray = new byte[][]{null, null};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableOneElementImmutableIntList(0);
        assertEquals(rightByteArray[0], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftAdditional_emptyArrays() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{{},{}};
        final byte[][] rightByteArray = new byte[][]{};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(0);
        assertEquals(leftByteArray[0], additionalItemsOnlyInLeftArray.get(arrayPositionOfadditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_rightAdditional_emptyArrays() throws ComparisonFailedException {
        final byte[][] leftByteArray = new byte[][]{};
        final byte[][] rightByteArray = new byte[][]{{},{}};

        final ArrayComparisonResult comparisonResult = twoDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableOneElementImmutableIntList(0);
        assertEquals(rightByteArray[0], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_threeDimensional_leftAdditional_emptyArrays() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{}},{}};
        final byte[][][] rightByteArray = new byte[][][]{};

        final ArrayComparisonResult comparisonResult = threeDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInLeftArray =  new ImmutableOneElementImmutableIntList(0);
        assertEquals(leftByteArray[0], additionalItemsOnlyInLeftArray.get(arrayPositionOfadditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_threeDimensional_rightAdditional_emptyArrays() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{};
        final byte[][][] rightByteArray = new byte[][][]{{{}},{}};

        final ArrayComparisonResult comparisonResult = threeDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableOneElementImmutableIntList(0);
        assertEquals(rightByteArray[0], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_threeDimensional_leftAdditional_value() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x12}},{}};
        final byte[][][] rightByteArray = new byte[][][]{};

        final ArrayComparisonResult comparisonResult = threeDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(0);
        assertEquals(leftByteArray[0], additionalItemsOnlyInLeftArray.get(arrayPositionOfadditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_threeDimensional_rightAdditional_value() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{};
        final byte[][][] rightByteArray = new byte[][][]{{{0x21}},{}};

        final ArrayComparisonResult comparisonResult = threeDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableOneElementImmutableIntList(0);
        assertEquals(rightByteArray[0], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_threeDimensional_leftAdditional_value_secondDimension() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x12, 0x34}},{}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x12}}};

        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY);
        final ArrayComparator<byte[][][]> threeDimensionalByteArrayComparator = DefaultComparators.<byte[][][]>createDefaultArrayComparatorBuilder().findFirstResultOnly().comparisonFeatures(comparisonFeatures).build();
        final ArrayComparisonResult comparisonResult = threeDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInLeftArray = new ImmutableIntArrayImmutableIntList(new int[]{0,0,1});
        assertEquals(leftByteArray[0][0][1], additionalItemsOnlyInLeftArray.get(arrayPositionOfadditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_threeDimensional_rightAdditional_value_secondDimension() throws ComparisonFailedException {
        final byte[][][] leftByteArray = new byte[][][]{{{0x12}},{}};
        final byte[][][] rightByteArray = new byte[][][]{{{0x12, 0x34}}};

        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY);
        final ArrayComparator<byte[][][]> threeDimensionalByteArrayComparator = DefaultComparators.<byte[][][]>createDefaultArrayComparatorBuilder().findFirstResultOnly().comparisonFeatures(comparisonFeatures).build();
        final ArrayComparisonResult comparisonResult = threeDimensionalByteArrayComparator.compare(leftByteArray, rightByteArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableIntArrayImmutableIntList(new int[]{0,0,1});
        assertEquals(rightByteArray[0][0][1], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
