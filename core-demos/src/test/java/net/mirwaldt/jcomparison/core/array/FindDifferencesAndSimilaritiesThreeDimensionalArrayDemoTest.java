package net.mirwaldt.jcomparison.core.array;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.array.impl.DefaultArrayWrapper;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntArrayImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.*;

public class FindDifferencesAndSimilaritiesThreeDimensionalArrayDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInThreeDimensionalArrays() throws Exception {
        final String[][][] leftStringArray = new String[][][] { { {"a", "b"} , {"1"}}, { {"l", "m"} , {"3", "4"} } };
        final String[][][] rightStringArray = new String[][][] { { {"a", "c"} , {"1", "2"}}, {} };

        final ArrayComparator<String[][][]> intArrayComparator = DefaultComparators.createDefaultArrayComparator();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftStringArray, rightStringArray);


        assertTrue(comparisonResult.hasSimilarities());
        final Map<List<Integer>, ValueSimilarity<Object>> similarity = comparisonResult.getSimilarities();
        assertEquals(2, similarity.size());
        assertEquals(leftStringArray[0][0][0], similarity.get(ImmutableIntArrayImmutableIntList.newImmutableIntArrayIntList(0, 0, 0)).getSimilarValue());
        assertEquals(rightStringArray[0][1][0], similarity.get(ImmutableIntArrayImmutableIntList.newImmutableIntArrayIntList(0, 1, 0)).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(2, additionalItemsOnlyInLeftArray.size());
        assertEquals(new DefaultArrayWrapper(leftStringArray[1][0]), additionalItemsOnlyInLeftArray.get(new ImmutableTwoElementsImmutableIntList(1, 0)));
        assertEquals(new DefaultArrayWrapper(leftStringArray[1][1]), additionalItemsOnlyInLeftArray.get(new ImmutableTwoElementsImmutableIntList(1, 1)));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        assertEquals(rightStringArray[0][1][1], additionalItemsOnlyInRightArray.get(ImmutableIntArrayImmutableIntList.newImmutableIntArrayIntList(0, 1, 1)));

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        assertEquals(ValueComparators.pairFactory.createPair(leftStringArray[0][0][1], rightStringArray[0][0][1]), differentValues.get(ImmutableIntArrayImmutableIntList.newImmutableIntArrayIntList(0, 0, 1)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
