package net.mirwaldt.jcomparison.core.collection.list;

import net.mirwaldt.jcomparison.core.collection.list.api.ListComparator;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.api.ListDifference;
import net.mirwaldt.jcomparison.core.collection.list.api.ListSimilarity;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntListFactory;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;
import static org.junit.Assert.assertFalse;

/**
 * Created by Michael on 06.12.2017.
 */
public class FindDifferencesAndSimilaritiesUniquesListComparisonDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInLists() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(1, 2, 4);
        final List<Integer> rightList = Arrays.asList(4, 2, 1);

        final ListComparator<Integer> listComparator = DefaultComparators.<Integer>createDefaultListComparatorBuilder().build();

        final ListComparisonResult<Integer> listComparisonResult = listComparator.compare(leftList, rightList);


        assertTrue(listComparisonResult.hasSimilarities());
        final ListSimilarity<Integer> listSimilarity = listComparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = listSimilarity.getSimilarElements();
        Assertions.assertEquals(1, similarElements.size());
        Assertions.assertEquals(leftList.get(1), similarElements.get(1).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = listSimilarity.getSimilarFrequencies();
        assertEquals(3, similarFrequencies.size());
        assertEquals(1, (similarFrequencies.get(leftList.get(0))).intValue());
        assertEquals(1, (similarFrequencies.get(leftList.get(1))).intValue());
        assertEquals(1, (similarFrequencies.get(leftList.get(2))).intValue());
        
        final Map<Integer, ImmutableIntList> similarPositions = listSimilarity.getSimilarPositions();
        Assertions.assertEquals(1, similarPositions.size());
        Assertions.assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), similarPositions.get(leftList.get(1)));


        assertTrue(listComparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = listComparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());
        
        assertEquals(0, listDifference.getElementsOnlyInRightList().size());
        
        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(2, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(0), rightList.get(0)), differentElements.get(0));
        assertEquals(new ImmutableIntPair(leftList.get(2), rightList.get(2)), differentElements.get(2));

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(2, differentPositions.size());
        Assertions.assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(0)),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(leftList.get(0)));
        Assertions.assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.singletonList(0))),
                differentPositions.get(leftList.get(2)));

        
        assertFalse(listComparisonResult.hasComparisonResults());
    }
}
