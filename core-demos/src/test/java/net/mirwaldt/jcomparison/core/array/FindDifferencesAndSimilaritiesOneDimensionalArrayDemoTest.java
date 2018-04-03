package net.mirwaldt.jcomparison.core.array;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;

public class FindDifferencesAndSimilaritiesOneDimensionalArrayDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInOneDimensionalArrays() throws ComparisonFailedException {
        final int[] leftIntArray = new int[] { 1, 2 };
        final int[] rightIntArray = new int[] { 1, 3, 4 };

        final ArrayComparator<int[]> intArrayComparator = DefaultComparators.createDefaultArrayComparator();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftIntArray, rightIntArray);

        assertTrue(comparisonResult.hasSimilarities());
        final Map<List<Integer>, ValueSimilarity<Object>> similarity = comparisonResult.getSimilarities();
        assertEquals(1, similarity.size());
        assertEquals(leftIntArray[0], similarity.get(new ImmutableOneElementImmutableIntList(0)).getSimilarValue());

        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        assertEquals(rightIntArray[2], additionalItemsOnlyInRightArray.get(new ImmutableOneElementImmutableIntList(2)));

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        assertEquals(new ImmutableIntPair(leftIntArray[1], rightIntArray[1]), differentValues.get(new ImmutableOneElementImmutableIntList(1)));

        assertFalse(comparisonResult.hasComparisonResults());
    }
}
