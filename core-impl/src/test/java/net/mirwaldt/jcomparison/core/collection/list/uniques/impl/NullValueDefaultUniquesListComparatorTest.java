package net.mirwaldt.jcomparison.core.collection.list.uniques.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparator;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.uniques.api.UniquesListDifference;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NullValueDefaultUniquesListComparatorTest {

    private final UniquesListComparator<Integer> defaultUniquesListComparator =
            DefaultComparators.createDefaultUniquesListComparator();

    @Test
    public void test_leftListEmpty() throws ComparisonFailedException {
        final List<Integer> leftList = Collections.emptyList();
        final List<Integer> rightList = Arrays.asList(new Integer[] { null });

        final UniquesListComparisonResult<Integer> comparisonResult = defaultUniquesListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final UniquesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        assertEquals(0, duplicatesListDifference.getElementsOnlyInLeftList().size());

        final Map<Integer, Integer> elementsOnlyInRightList = duplicatesListDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(new Integer(0), elementsOnlyInRightList.get(rightList.get(0)));

        assertEquals(0, duplicatesListDifference.getElementsMovedBetweenLists().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_rightListEmpty() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(new Integer[] { null });
        final List<Integer> rightList = Collections.emptyList();

        final UniquesListComparisonResult<Integer> comparisonResult = defaultUniquesListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final UniquesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        final Map<Integer, Integer> elementsOnlyInLeftList = duplicatesListDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(new Integer(0), elementsOnlyInLeftList.get(leftList.get(0)));

        assertEquals(0, duplicatesListDifference.getElementsOnlyInRightList().size());

        assertEquals(0, duplicatesListDifference.getElementsMovedBetweenLists().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElementOnly() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(new Integer[] { null });
        final List<Integer> rightList = Arrays.asList(new Integer[] { null });

        final UniquesListComparisonResult<Integer> comparisonResult = defaultUniquesListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarity());
        final Map<Integer, Integer> duplicatesListSimilarities = comparisonResult.getSimilarity();
        assertEquals(1, duplicatesListSimilarities.size());
        assertEquals(new Integer(0), duplicatesListSimilarities.get(leftList.get(0)));


        assertFalse(comparisonResult.hasDifference());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentElementOnly() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(6);
        final List<Integer> rightList = Arrays.asList(new Integer[] { null });

        final UniquesListComparisonResult<Integer> comparisonResult = defaultUniquesListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final UniquesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        final Map<Integer, Integer> elementsOnlyInLeftList = duplicatesListDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(new Integer(0), elementsOnlyInLeftList.get(leftList.get(0)));

        final Map<Integer, Integer> elementsOnlyInRightList = duplicatesListDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(new Integer(0), elementsOnlyInRightList.get(rightList.get(0)));

        assertEquals(0, duplicatesListDifference.getElementsMovedBetweenLists().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentElementOnlyAtDifferentPlaces() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(null, 6);
        final List<Integer> rightList = Arrays.asList(7, null);

        final UniquesListComparisonResult<Integer> comparisonResult = defaultUniquesListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final UniquesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        final Map<Integer, Integer> elementsOnlyInLeftList = duplicatesListDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(new Integer(1), elementsOnlyInLeftList.get(leftList.get(1)));

        final Map<Integer, Integer> elementsOnlyInRightList = duplicatesListDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(new Integer(0), elementsOnlyInRightList.get(rightList.get(0)));

        final Map<Integer, Pair<Integer>> elementsMovedBetweenLists = duplicatesListDifference.getElementsMovedBetweenLists();
        assertEquals(1, elementsMovedBetweenLists.size());
        assertEquals(new ImmutableIntPair(0, 1), elementsMovedBetweenLists.get(leftList.get(0)));


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_twoDifferentElementOnlyAtDifferentPlaces() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(1, null, 6);
        final List<Integer> rightList = Arrays.asList(null, 7, 1);

        final UniquesListComparisonResult<Integer> comparisonResult = defaultUniquesListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final UniquesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        final Map<Integer, Integer> elementsOnlyInLeftList = duplicatesListDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(new Integer(2), elementsOnlyInLeftList.get(leftList.get(2)));

        final Map<Integer, Integer> elementsOnlyInRightList = duplicatesListDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(new Integer(1), elementsOnlyInRightList.get(rightList.get(1)));

        final Map<Integer, Pair<Integer>> elementsMovedBetweenLists = duplicatesListDifference.getElementsMovedBetweenLists();
        assertEquals(2, elementsMovedBetweenLists.size());
        assertEquals(new ImmutableIntPair(0, 2), elementsMovedBetweenLists.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(1, 0), elementsMovedBetweenLists.get(leftList.get(1)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_allDifferences() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(null, 2, 6);
        final List<Integer> rightList = Arrays.asList(7, 2, null);

        final UniquesListComparisonResult<Integer> comparisonResult = defaultUniquesListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarity());
        final Map<Integer, Integer> duplicatesListSimilarities = comparisonResult.getSimilarity();
        assertEquals(1, duplicatesListSimilarities.size());
        assertEquals(new Integer(1), duplicatesListSimilarities.get(leftList.get(1)));


        assertTrue(comparisonResult.hasDifference());
        final UniquesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        final Map<Integer, Integer> elementsOnlyInLeftList = duplicatesListDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(new Integer(2), elementsOnlyInLeftList.get(leftList.get(2)));

        final Map<Integer, Integer> elementsOnlyInRightList = duplicatesListDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(new Integer(0), elementsOnlyInRightList.get(rightList.get(0)));

        final Map<Integer, Pair<Integer>> elementsMovedBetweenLists = duplicatesListDifference.getElementsMovedBetweenLists();
        assertEquals(1, elementsMovedBetweenLists.size());
        assertEquals(new ImmutableIntPair(0, 2), elementsMovedBetweenLists.get(leftList.get(0)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
