package net.mirwaldt.jcomparison.core.array;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntArrayImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import org.junit.Test;

import java.util.Map;

import static junit.framework.TestCase.*;

public class FindDifferencesAndSimilaritiesThreeDimensionalArrayDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInThreeDimensionalArrays() throws ComparisonFailedException {
        final String[][][] leftStringArray = new String[][][] { { {"a", "b"} , {"1"}}, { {"l", "m"} , {"3", "4"} } };
        final String[][][] rightStringArray = new String[][][] { { {"a", "c"} , {"1", "2"}}, {} };

        final ArrayComparator<String[][][]> intArrayComparator = DefaultComparators.createDefaultArrayComparator();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftStringArray, rightStringArray);


        assertTrue(comparisonResult.hasSimilarity());
        final Map<ImmutableIntList, ?> similarity = comparisonResult.getSimilarity();
        assertEquals(2, similarity.size());
        assertEquals(leftStringArray[0][0][0], similarity.get(ImmutableIntArrayImmutableIntList.newImmutableIntArrayIntList(0, 0, 0)));
        assertEquals(rightStringArray[0][1][0], similarity.get(ImmutableIntArrayImmutableIntList.newImmutableIntArrayIntList(0, 1, 0)));


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(2, additionalItemsOnlyInLeftArray.size());
        assertEquals(leftStringArray[1][0], additionalItemsOnlyInLeftArray.get(new ImmutableTwoElementsImmutableIntList(1, 0)));
        assertEquals(leftStringArray[1][1], additionalItemsOnlyInLeftArray.get(new ImmutableTwoElementsImmutableIntList(1, 1)));

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        assertEquals(rightStringArray[0][1][1], additionalItemsOnlyInRightArray.get(ImmutableIntArrayImmutableIntList.newImmutableIntArrayIntList(0, 1, 1)));

        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());
        assertEquals(new ImmutablePair<>(leftStringArray[0][0][1], rightStringArray[0][0][1]), differentValues.get(ImmutableIntArrayImmutableIntList.newImmutableIntArrayIntList(0, 0, 1)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
