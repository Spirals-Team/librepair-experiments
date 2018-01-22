package net.mirwaldt.jcomparison.core.collection.list.duplicates.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListDifference;
import net.mirwaldt.jcomparison.core.collection.list.duplicates.api.DuplicatesListSimilarity;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import org.junit.Test;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class NullValueDefaultDuplicatesListComparatorTest {

    private final DefaultDuplicatesListComparator<Integer> defaultDuplicatesListComparator =
            DefaultComparators.createDefaultDuplicatesListComparator();

    @Test
    public void test_leftListEmpty() throws ComparisonFailedException {
        final List<Integer> leftList = Collections.emptyList();
        final List<Integer> rightList = Arrays.asList(new Integer[] { null });

        final DuplicatesListComparisonResult<Integer> comparisonResult = defaultDuplicatesListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final DuplicatesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInLeftList().size());

        final Map<Integer, Integer> elementsOnlyInRightList = duplicatesListDifference.getElementsAdditionalInRightList();
        assertEquals(1, elementsOnlyInRightList.size());
        assertEquals(rightList.get(0), elementsOnlyInRightList.get(0));

        assertEquals(0, duplicatesListDifference.getDifferentElements().size());

        final Map<Integer, Pair<Integer>> differentFrequencies = duplicatesListDifference.getDifferentFrequencies();
        assertEquals(1, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(0)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_rightListEmpty() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(null, 5);
        final List<Integer> rightList = Collections.emptyList();

        final DuplicatesListComparisonResult<Integer> comparisonResult = defaultDuplicatesListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final DuplicatesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();


        final Map<Integer, Integer> elementsOnlyInLeftList = duplicatesListDifference.getElementsAdditionalInLeftList();
        assertEquals(2, elementsOnlyInLeftList.size());
        assertEquals(leftList.get(0), elementsOnlyInLeftList.get(0));
        assertEquals(leftList.get(1), elementsOnlyInLeftList.get(1));

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInRightList().size());

        assertEquals(0, duplicatesListDifference.getDifferentElements().size());

        final Map<Integer, Pair<Integer>> differentFrequencies = duplicatesListDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElementOnly_occuringOnce() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(new Integer[] { null });
        final List<Integer> rightList = Arrays.asList(new Integer[] { null });

        final DuplicatesListComparisonResult<Integer> comparisonResult = defaultDuplicatesListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarity());
        final DuplicatesListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarity();

        final Map<Integer, Integer> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0));

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));


        assertFalse(comparisonResult.hasDifference());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElementOnly_occuringTwice() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(null, null);
        final List<Integer> rightList = Arrays.asList(null, null);

        final DuplicatesListComparisonResult<Integer> comparisonResult = defaultDuplicatesListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarity());
        final DuplicatesListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarity();

        final Map<Integer, Integer> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(2, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0));
        assertEquals(leftList.get(1), similarElements.get(1));

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(2), similarFrequencies.get(leftList.get(0)));


        assertFalse(comparisonResult.hasDifference());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneDifferentElementOnly() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(new Integer[] { null });
        final List<Integer> rightList = Arrays.asList(7);

        final DuplicatesListComparisonResult<Integer> comparisonResult = defaultDuplicatesListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final DuplicatesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInLeftList().size());

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInRightList().size());

        final Map<Integer, Pair<Integer>> differentElements = duplicatesListDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutablePair<>(leftList.get(0), rightList.get(0)), differentElements.get(0));

        final Map<Integer, Pair<Integer>> differentFrequencies = duplicatesListDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(0)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoDifferentElementOnly() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(null, 9);
        final List<Integer> rightList = Arrays.asList(7, 8);

        final DuplicatesListComparisonResult<Integer> comparisonResult = defaultDuplicatesListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final DuplicatesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInLeftList().size());

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInRightList().size());

        final Map<Integer, Pair<Integer>> differentElements = duplicatesListDifference.getDifferentElements();
        assertEquals(2, differentElements.size());
        assertEquals(new ImmutablePair<>(leftList.get(0), rightList.get(0)), differentElements.get(0));
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = duplicatesListDifference.getDifferentFrequencies();
        assertEquals(4, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(0)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(0)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElement_oneDifferentElement() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(null, 5);
        final List<Integer> rightList = Arrays.asList(null, 7);

        final DuplicatesListComparisonResult<Integer> comparisonResult = defaultDuplicatesListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarity());
        final DuplicatesListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarity();

        final Map<Integer, Integer> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0));

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));


        assertTrue(comparisonResult.hasDifference());
        final DuplicatesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInLeftList().size());

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInRightList().size());

        final Map<Integer, Pair<Integer>> differentElements = duplicatesListDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = duplicatesListDifference.getDifferentFrequencies();
        assertEquals(2, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElement_oneDifferentElement_oneAdditionalLeftElement() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(null, 5, 9);
        final List<Integer> rightList = Arrays.asList(null, 7);

        final DuplicatesListComparisonResult<Integer> comparisonResult = defaultDuplicatesListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarity());
        final DuplicatesListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarity();

        final Map<Integer, Integer> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0));

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));


        assertTrue(comparisonResult.hasDifference());
        final DuplicatesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        final Map<Integer, Integer> elementsOnlyInLeftList = duplicatesListDifference.getElementsAdditionalInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());
        assertEquals(leftList.get(2), elementsOnlyInLeftList.get(2));

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInRightList().size());

        final Map<Integer, Pair<Integer>> differentElements = duplicatesListDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = duplicatesListDifference.getDifferentFrequencies();
        assertEquals(3, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneSimilarElement_oneDifferentElement_twoAdditionalRightElement() throws ComparisonFailedException {
        final List<Integer> leftList = Arrays.asList(3, null);
        final List<Integer> rightList = Arrays.asList(3, 7, 12, 15);

        final DuplicatesListComparisonResult<Integer> comparisonResult = defaultDuplicatesListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarity());
        final DuplicatesListSimilarity<Integer> duplicatesListSimilarities = comparisonResult.getSimilarity();

        final Map<Integer, Integer> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0));

        final Map<Integer, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));


        assertTrue(comparisonResult.hasDifference());
        final DuplicatesListDifference<Integer> duplicatesListDifference = comparisonResult.getDifference();

        assertEquals(0, duplicatesListDifference.getElementsAdditionalInLeftList().size());

        final Map<Integer, Integer> elementsOnlyInRightList = duplicatesListDifference.getElementsAdditionalInRightList();
        assertEquals(2, elementsOnlyInRightList.size());
        assertEquals(rightList.get(2), elementsOnlyInRightList.get(2));
        assertEquals(rightList.get(3), elementsOnlyInRightList.get(3));

        final Map<Integer, Pair<Integer>> differentElements = duplicatesListDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutablePair<>(leftList.get(1), rightList.get(1)), differentElements.get(1));

        final Map<Integer, Pair<Integer>> differentFrequencies = duplicatesListDifference.getDifferentFrequencies();
        assertEquals(4, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(3)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
