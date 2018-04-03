package net.mirwaldt.jcomparison.core.collection.list.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.collection.list.api.ListDifference;
import net.mirwaldt.jcomparison.core.collection.list.api.ListSimilarity;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
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

public class BasicDefaultListComparatorTest {

    private final DefaultListComparator<Integer> defaultListComparator =
            DefaultComparators.createDefaultListComparator();

    @Test
    public void test_emptyLists() throws ComparisonFailedException {
        final List<Integer> leftList = Collections.emptyList();
        final List<Integer> rightList = Collections.emptyList();

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftListEmpty() throws ComparisonFailedException {
        final List<Integer> leftList = Collections.emptyList();
        final List<Integer> rightList = Collections.singletonList(1);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), elementsOnlyInRightList.get(rightList.get(0)));

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(1, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(0)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(1, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(0))),
                differentPositions.get(rightList.get(0)));
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_rightListEmpty() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(2, 5);
        final List<Integer> rightList = Collections.emptyList();

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();
        
        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(2, elementsOnlyInLeftList.size());        
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), elementsOnlyInLeftList.get(leftList.get(0)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInLeftList.get(leftList.get(1)));

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        
        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(2, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(0)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(0)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElementOnly_occuringOnce() throws ComparisonFailedException {
        final List<Integer> leftList = Collections.singletonList(3);
        final List<Integer> rightList = Collections.singletonList(3);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));
        
        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), similarPositions.get(leftList.get(0)));


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElementOnly_occuringTwice() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(4, 4);
        final List<Integer> rightList = Arrays.asList(4, 4);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(2, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());
        assertEquals(leftList.get(1), similarElements.get(1).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(2), similarFrequencies.get(leftList.get(0)));

        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(0, 1)), similarPositions.get(leftList.get(0)));
        
        
        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentElementOnly() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(6);
        final List<Integer> rightList = Arrays.asList(7);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), elementsOnlyInLeftList.get(leftList.get(0)));

        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), elementsOnlyInRightList.get(rightList.get(0)));

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(0), rightList.get(0)), differentElements.get(0));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(0)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(2, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(0)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(0)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(0))),
                differentPositions.get(rightList.get(0)));

        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoDifferentElementsOnly() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(6, 9);
        final List<Integer> rightList = Arrays.asList(7, 10);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(2, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), elementsOnlyInLeftList.get(leftList.get(0)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInLeftList.get(leftList.get(1)));

        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(2, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), elementsOnlyInRightList.get(rightList.get(0)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(2, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(0), rightList.get(0)), differentElements.get(0));
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(4, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(0)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        
        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(4, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(0)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(0)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(0))),
                differentPositions.get(rightList.get(0)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));

        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElement_oneDifferentElement() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, 5);
        final List<Integer> rightList = Arrays.asList(3, 7);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));

        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), similarPositions.get(leftList.get(0)));


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInLeftList.get(leftList.get(1)));

        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(2, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElement_oneDifferentElement_oneAdditionalLeftElement() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, 5, 9);
        final List<Integer> rightList = Arrays.asList(3, 7);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));

        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), similarPositions.get(leftList.get(0)));


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(2, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(2)), elementsOnlyInLeftList.get(leftList.get(2)));

        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(3, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        
        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(3, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));

        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElement_oneDifferentElement_twoAdditionalRightElements() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, 5);
        final List<Integer> rightList = Arrays.asList(3, 7, 12, 15);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));
        
        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), similarPositions.get(leftList.get(0)));
        
        
        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInLeftList.get(leftList.get(1)));

        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(3, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(2)), elementsOnlyInRightList.get(rightList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(3)), elementsOnlyInRightList.get(rightList.get(3)));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(4, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(3)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(4, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(3))),
                differentPositions.get(rightList.get(3)));
        

        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_oneSimilarElement_oneDifferentElement_frequencyChangeFromTwoToOne() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, 3);
        final List<Integer> rightList = Arrays.asList(3, 7);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(0, similarFrequencies.size());

        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), similarPositions.get(leftList.get(0)));
        

        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(2, 1), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(2, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        

        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_oneSimilarElement_oneDifferentElement_frequencyChangeFromOneToTwo() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, 5);
        final List<Integer> rightList = Arrays.asList(3, 3);

        final ListComparisonResult<Integer> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Integer>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(0, similarFrequencies.size());

        final Map<Integer, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), similarPositions.get(leftList.get(0)));


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Integer> listDifference = comparisonResult.getDifferences();

        final Map<Integer, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInLeftList.get(leftList.get(1)));

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 2), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(2, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoSimilarElements_twoDifferentElement_frequencyChanges() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, 2, 1, 2, 1);
        final List<Integer> rightList = Arrays.asList(3, 3, 2, 2, 3, 7);

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
        
        final Map<Integer, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(5)), elementsOnlyInRightList.get(rightList.get(5)));

        final Map<Integer, Pair<Integer>> differentElements = listDifference.getDifferentElements();
        assertEquals(3, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));
        assertEquals(new ImmutableIntPair(leftList.get(2), rightList.get(2)), differentElements.get(2));
        assertEquals(new ImmutableIntPair(leftList.get(4), rightList.get(4)), differentElements.get(4));

        final Map<Integer, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(3, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 3), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(2, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(5)));

        final Map<Integer, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(4, differentPositions.size());
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
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(5))),
                differentPositions.get(rightList.get(5)));

        
        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_oneSimilarElement_oneDifferentElement_oneComparisonResult() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final ItemComparator<Object, ComparisonResult<?, ?, ?>> comparator = new ItemComparator<Object, ComparisonResult<?, ?, ?>>() {
            private final ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> stringComparator = DefaultComparators.createSafeDefaultSubstringComparator(true);
            private final ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> equalsComparator = ValueComparators.immutableResultEqualsComparator();
            
            @Override
            public ComparisonResult<?, ?, ?> compare(Object leftItem, 
                                                     Object rightItem, 
                                                     VisitedObjectsTrace visitedObjectsTrace,
                                                     ComparatorOptions comparatorOptions) throws ComparisonFailedException {
                if(String.class.equals(leftItem.getClass())) {
                    return stringComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
                } else {
                    return equalsComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
                }
            }
        };
        
        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .build();
        
        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));

        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(0)), similarPositions.get(leftList.get(0)));

        
        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(2, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(2)), elementsOnlyInLeftList.get(leftList.get(2)));

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(2, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(2)), elementsOnlyInRightList.get(rightList.get(2)));

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair((int)leftList.get(1), (int)rightList.get(1)), differentElements.get(1));

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(4, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(4, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertNotNull(comparisonResults.get(2));
    }
}
