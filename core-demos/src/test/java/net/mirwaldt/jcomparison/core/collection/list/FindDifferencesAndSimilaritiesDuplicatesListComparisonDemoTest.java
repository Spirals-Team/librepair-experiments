package net.mirwaldt.jcomparison.core.collection.list;

import net.mirwaldt.jcomparison.core.collection.list.api.ListDifference;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparator;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.api.ListSimilarity;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntListFactory;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

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

        final ListComparator<Integer> listComparator = DefaultComparators.<Integer>createDefaultListComparatorBuilder().build();

        final ListComparisonResult<Integer> listComparisonResult = listComparator.compare(leftList, rightList);


        assertTrue(listComparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = listComparisonResult.getDifferences();

        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        Assertions.assertEquals(1, elementsOnlyInLeftList.size());
        Assertions.assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInLeftList.get(leftList.get(1)));

        final Map<Integer, ImmutableIntList> elementsAdditionalInRightList = listDifference.getElementsOnlyInRightList();
        Assertions.assertEquals(1, elementsAdditionalInRightList.size());
        Assertions.assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsAdditionalInRightList.get(rightList.get(3)));

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(2, 1), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(3, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 2), differentFrequencies.get(1));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(2));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(6));


        assertTrue(listComparisonResult.hasSimilarities());
        final ListSimilarity<Integer> listSimilarity = listComparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = listSimilarity.getSimilarElements();
        assertEquals(2, similarElements.size());
        assertEquals(1, (similarElements.get(0)).getSimilarValue().intValue());
        assertEquals(4, (similarElements.get(2)).getSimilarValue().intValue());

        final Map<Integer, Integer> similarFrequencies = listSimilarity.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(1, (similarFrequencies.get(4)).intValue());


        assertFalse(listComparisonResult.hasComparisonResults());
    }
}
