package net.mirwaldt.jcomparison.core.collection.set.impl;

import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.collection.set.api.SetDifference;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BasicDefaultSetComparatorTest {

    private DefaultSetComparator<Integer> simpleIntSetComparator
            = DefaultComparators.<Integer>createDefaultSetComparatorBuilder().build();

    @Test
    public void test_emptySets() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>();
        final Set<Integer> rightSet = new HashSet<>();

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertFalse(comparisonResult.hasSimilarities());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_leftSetEmpty_nonNullElements() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>();
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(1,2,3));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(3, addedElements.size());
        assertTrue(addedElements.contains(1));
        assertTrue(addedElements.contains(2));
        assertTrue(addedElements.contains(3));

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(0, removedElements.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_rightSetEmpty_nonNullElements() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,2,3));
        final Set<Integer> rightSet = new HashSet<>();

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(0, addedElements.size());

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(3, removedElements.size());
        assertTrue(removedElements.contains(1));
        assertTrue(removedElements.contains(2));
        assertTrue(removedElements.contains(3));


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_bothCompletelyEqual_nonNullElements() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,2,3));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(1,2,3));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(3, retainedElements.size());
        assertTrue(retainedElements.contains(1));
        assertTrue(retainedElements.contains(2));
        assertTrue(retainedElements.contains(3));


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_bothCompletelyDifferent_nonNullElements() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,2,3));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(4,5,6));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(3, addedElements.size());
        assertTrue(addedElements.contains(4));
        assertTrue(addedElements.contains(5));
        assertTrue(addedElements.contains(6));

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(3, removedElements.size());
        assertTrue(removedElements.contains(1));
        assertTrue(removedElements.contains(2));
        assertTrue(removedElements.contains(3));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_bothDifferent_oneCommonElement_nonNullElements() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,2,3));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(3,4,5));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(1, retainedElements.size());
        assertTrue(retainedElements.contains(3));


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(2, addedElements.size());
        assertTrue(addedElements.contains(4));
        assertTrue(addedElements.contains(5));

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(2, removedElements.size());
        assertTrue(removedElements.contains(1));
        assertTrue(removedElements.contains(2));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_bothDifferent_twoCommonElements_nonNullElements() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,2,3));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(2,3,4));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(2, retainedElements.size());
        assertTrue(retainedElements.contains(2));
        assertTrue(retainedElements.contains(3));


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(1, addedElements.size());
        assertTrue(addedElements.contains(4));

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(1, removedElements.size());
        assertTrue(removedElements.contains(1));
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_leftSetEmpty_nullElement() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>();
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(1,null,3));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(3, addedElements.size());
        assertTrue(addedElements.contains(1));
        assertTrue(addedElements.contains(null));
        assertTrue(addedElements.contains(3));

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(0, removedElements.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_rightSetEmpty_nullElement() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(null,2,3));
        final Set<Integer> rightSet = new HashSet<>();

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(0, addedElements.size());

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(3, removedElements.size());
        assertTrue(removedElements.contains(null));
        assertTrue(removedElements.contains(2));
        assertTrue(removedElements.contains(3));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_bothCompletelyEqual_onlyNullElement() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>();
        leftSet.add(null);
        final Set<Integer> rightSet = new HashSet<>();
        rightSet.add(null);

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(1, retainedElements.size());
        assertTrue(retainedElements.contains(null));


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_bothCompletelyEqual_nullElement() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,2,null));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(1,2,null));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(3, retainedElements.size());
        assertTrue(retainedElements.contains(1));
        assertTrue(retainedElements.contains(2));
        assertTrue(retainedElements.contains(null));


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_bothCompletelyDifferent_nullElement() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,2,3));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(null,5,6));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(3, addedElements.size());
        assertTrue(addedElements.contains(null));
        assertTrue(addedElements.contains(5));
        assertTrue(addedElements.contains(6));

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(3, removedElements.size());
        assertTrue(removedElements.contains(1));
        assertTrue(removedElements.contains(2));
        assertTrue(removedElements.contains(3));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_bothDifferent_oneCommonElement_nullElement() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,2,null));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(null,4,5));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(1, retainedElements.size());
        assertTrue(retainedElements.contains(null));


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(2, addedElements.size());
        assertTrue(addedElements.contains(4));
        assertTrue(addedElements.contains(5));

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(2, removedElements.size());
        assertTrue(removedElements.contains(1));
        assertTrue(removedElements.contains(2));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_bothDifferent_twoCommonElements_nullElement() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,null,3));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(null,3,4));

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(2, retainedElements.size());
        assertTrue(retainedElements.contains(null));
        assertTrue(retainedElements.contains(3));


        assertTrue(comparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> addedElements = setDifference.getElementsOnlyInRightSet();
        assertEquals(1, addedElements.size());
        assertTrue(addedElements.contains(4));

        final Set<Integer> removedElements = setDifference.getElementsOnlyInLeftSet();
        assertEquals(1, removedElements.size());
        assertTrue(removedElements.contains(1));


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
