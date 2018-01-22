package net.mirwaldt.jcomparison.core.collection.list.uniques;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListDifference;
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
public class FindDifferencesAndSimilaritiesUniqueElementsListComparisonDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInLists() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(1, 2, 4);
        final List<Integer> rightList = Arrays.asList(1, 3, 2, 6);

        final UniquesListComparator<Integer> uniquesListComparator = DefaultComparators.<Integer>createDefaultUniquesListComparatorBuilder().build();

        final UniquesListComparisonResult<Integer> uniquesListComparisonResult = uniquesListComparator.compare(leftList, rightList);


        assertTrue(uniquesListComparisonResult.hasDifference());
        final UniquesListDifference<Integer> uniquesListDifference = uniquesListComparisonResult.getDifference();

        final Map<Integer, Integer> elementsOnlyInLeftList = uniquesListDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(2, (elementsOnlyInLeftList.get(4)).intValue());

        final Map<Integer, Pair<Integer>> elementsMovedBetweenLists = uniquesListDifference.getElementsMovedBetweenLists();
        assertEquals(1, elementsMovedBetweenLists.size());
        assertEquals(new ImmutableIntPair(1, 2), (elementsMovedBetweenLists.get(2)));

        final Map<Integer, Integer> elementsOnlyInRightList = uniquesListDifference.getElementsOnlyInRightList();
        assertEquals(2, elementsOnlyInRightList.size());
        assertEquals(1, (elementsOnlyInRightList.get(3)).intValue());
        assertEquals(3, (elementsOnlyInRightList.get(6)).intValue());



        assertTrue(uniquesListComparisonResult.hasSimilarity());
        final Map<Integer, Integer> similarity = uniquesListComparisonResult.getSimilarity();
        assertEquals(1, similarity.size());
        assertEquals(0, (similarity.get(1)).intValue());



        assertFalse(uniquesListComparisonResult.hasComparisonResults());
    }
}
