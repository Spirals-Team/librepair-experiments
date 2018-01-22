package net.mirwaldt.jcomparison.core.collection.list.duplicates;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListDifference;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListSimilarity;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Michael on 06.12.2017.
 */
public class FindDifferencesAndSimilaritiesDuplicatesListComparisonDemoTest {

    @Test
    public void test_findDifferencesAndSimilaritiesInLists() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(1, 2, 4);
        final List<Integer> rightList = Arrays.asList(1, 1, 4, 6);

        final DuplicatesListComparator<Integer> duplicatesListComparator = DefaultComparators.<Integer>createDefaultDuplicatesListComparatorBuilder().build();

        final DuplicatesListComparisonResult<Integer> duplicatesListComparisonResult = duplicatesListComparator.compare(leftList, rightList);


        assertTrue(duplicatesListComparisonResult.hasDifference());
        final DuplicatesListDifference<Integer> duplicatesListDifference = duplicatesListComparisonResult.getDifference();

        final Map<Integer, Integer> elementsOnlyInLeftList = duplicatesListDifference.getElementsAdditionalInLeftList();
        assertEquals(0, elementsOnlyInLeftList.size());

        final Map<Integer, Integer> elementsOnlyInRightList = duplicatesListDifference.getElementsAdditionalInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(6, (elementsOnlyInRightList.get(3)).intValue());

        final Map<Integer, Pair<Integer>> differentElements = duplicatesListDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(2, 1), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = duplicatesListDifference.getDifferentFrequencies();
        assertEquals(3, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 2), differentFrequencies.get(1));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(2));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(6));


        assertTrue(duplicatesListComparisonResult.hasSimilarity());
        final DuplicatesListSimilarity<Integer> duplicatesListSimilarity = duplicatesListComparisonResult.getSimilarity();

        final Map<Integer, Integer> similarElements = duplicatesListSimilarity.getSimilarElements();
        assertEquals(2, similarElements.size());
        assertEquals(1, (similarElements.get(0)).intValue());
        assertEquals(4, (similarElements.get(2)).intValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarity.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(1, (similarFrequencies.get(4)).intValue());


        assertFalse(duplicatesListComparisonResult.hasComparisonResults());
    }
}
