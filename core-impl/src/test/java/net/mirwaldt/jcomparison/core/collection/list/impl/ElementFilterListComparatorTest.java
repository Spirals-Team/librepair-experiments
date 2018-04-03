package net.mirwaldt.jcomparison.core.collection.list.impl;

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

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class ElementFilterListComparatorTest {

    @Test
    public void test_emptyLists() throws ComparisonFailedException {
        final List<Integer> leftList = Collections.emptyList();
        final List<Integer> rightList = Collections.emptyList();
        
        final DefaultListComparator<Integer> defaultListComparator =
                DefaultComparators.<Integer>createDefaultListComparatorBuilder().filterElements((i) -> 1 < i).build();
        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_filterLeftListOnly() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, 2, 1, 2, 1);
        final List<Integer> rightList = Arrays.asList(3, 3, 2, 2, 3, 7);
        
        final DefaultListComparator<Integer> defaultListComparator =
                DefaultComparators.<Integer>createDefaultListComparatorBuilder().filterElements((i) -> 1 < i).build();
        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(2, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());
        assertEquals(leftList.get(3), similarElements.get(3).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(2), similarFrequencies.get(leftList.get(1)));

        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(2, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), similarPositions.get(leftList.get(0)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(3)), similarPositions.get(leftList.get(3)));

        
        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();
        
        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(5)), elementsOnlyInRightList.get(rightList.get(5)));
        
        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 3), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(5)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(3, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Arrays.asList(1, 4))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(5))),
                differentPositions.get(rightList.get(5)));
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_filterRightListOnly() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, 2, 1, 2, 1);
        final List<Integer> rightList = Arrays.asList(3, 3, 2, 2, 3, 7);

        final DefaultListComparator<Integer> defaultListComparator =
                DefaultComparators.<Integer>createDefaultListComparatorBuilder().filterElements((i) -> i < 7).build();
        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(2, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());
        assertEquals(leftList.get(3), similarElements.get(3).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(2), similarFrequencies.get(leftList.get(1)));

        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(2, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), similarPositions.get(leftList.get(0)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(3)), similarPositions.get(leftList.get(3)));
        
        
        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2, 4)), elementsOnlyInLeftList.get(leftList.get(2)));

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(3, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));
        assertEquals(new ImmutableIntPair(leftList.get(2), rightList.get(2)), differentElements.get(2));
        assertEquals(new ImmutableIntPair(leftList.get(4), rightList.get(4)), differentElements.get(4));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 3), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(2, 0), differentFrequencies.get(leftList.get(2)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(3, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Arrays.asList(2, 4)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Arrays.asList(1, 4))),
                differentPositions.get(rightList.get(1)));
        

        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_filterBothLists() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, 2, 1, 2, 1);
        final List<Integer> rightList = Arrays.asList(3, 3, 2, 2, 3, 7);

        final DefaultListComparator<Integer> defaultListComparator =
                DefaultComparators.<Integer>createDefaultListComparatorBuilder().filterElements((i) -> i != 3).build();
        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(3), similarElements.get(3).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(2), similarFrequencies.get(leftList.get(1)));
        
        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(3)), similarPositions.get(leftList.get(3)));

        
        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2, 4)), elementsOnlyInLeftList.get(leftList.get(2)));

        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(5)), elementsOnlyInRightList.get(rightList.get(5)));

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(2), rightList.get(2)), differentElements.get(2));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(2, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(5)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(3, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Arrays.asList(2, 4)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(5))),
                differentPositions.get(rightList.get(5)));
        

        assertFalse(comparisonResult.hasComparisonResults());
    }

}
