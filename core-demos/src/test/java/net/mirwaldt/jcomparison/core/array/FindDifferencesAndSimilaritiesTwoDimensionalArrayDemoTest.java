package net.mirwaldt.jcomparison.core.array;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.array.impl.DefaultArrayWrapper;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableDoublePair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;

public class FindDifferencesAndSimilaritiesTwoDimensionalArrayDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInTwoDimensionalArrays() throws ComparisonFailedException {
        final double[][] leftDoubleArray = new double[][] { {}, { 1.0d , 2.0d, 3.3d }, {} };
        final double[][] rightDoubleArray = new double[][] { null, { 1.0d, 2.1d }, { 4.5 }};

        final ArrayComparator<double[][]> intArrayComparator = DefaultComparators.createDefaultArrayComparator();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftDoubleArray, rightDoubleArray);


        assertTrue(comparisonResult.hasSimilarities());
        final Map<List<Integer>, ValueSimilarity<Object>> similarity = comparisonResult.getSimilarities();
        assertEquals(1, similarity.size());
        assertEquals(leftDoubleArray[1][0], similarity.get(new ImmutableTwoElementsImmutableIntList(1, 0)).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        assertEquals(leftDoubleArray[1][2], additionalItemsOnlyInLeftArray.get(new ImmutableTwoElementsImmutableIntList(1, 2)));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        assertEquals(rightDoubleArray[2][0], additionalItemsOnlyInRightArray.get(new ImmutableTwoElementsImmutableIntList(2, 0)));

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(2, differentValues.size());
        assertEquals(new ImmutablePair<>(
                new DefaultArrayWrapper(leftDoubleArray[0]), 
                rightDoubleArray[0]
                ), 
                differentValues.get(new ImmutableOneElementImmutableIntList(0)));
        assertEquals(new ImmutableDoublePair(leftDoubleArray[1][1], rightDoubleArray[1][1]), differentValues.get(new ImmutableTwoElementsImmutableIntList(1, 1)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
