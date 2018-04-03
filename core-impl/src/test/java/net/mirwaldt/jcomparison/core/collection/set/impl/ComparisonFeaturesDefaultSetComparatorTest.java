package net.mirwaldt.jcomparison.core.collection.set.impl;

import net.mirwaldt.jcomparison.core.collection.set.api.SetComparator;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.collection.set.api.SetDifference;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonFeaturesDefaultSetComparatorTest {
    
    @Test
    public void test_oneComparisonFeature_elementsOnlyInLeftSet() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(2, 3));

        final DefaultSetComparator<Integer> simpleIntSetComparator
                = DefaultComparators.<Integer>createDefaultSetComparatorBuilder()
                .useComparisonFeatures(EnumSet.of(SetComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_SET))
                .build();

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertFalse(comparisonResult.hasSimilarities());
        

        assertTrue(comparisonResult.hasDifferences());

        final SetDifference<Integer> setDifference = comparisonResult.getDifferences();
        assertNotNull(setDifference);
        
        final Set<Integer> elementsOnlyInLeftSet = setDifference.getElementsOnlyInLeftSet();
        assertEquals(1, elementsOnlyInLeftSet.size());
        assertTrue(elementsOnlyInLeftSet.contains(1));

        assertEquals(0, setDifference.getElementsOnlyInRightSet().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_elementsOnlyInRightSet() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(2, 3));

        final DefaultSetComparator<Integer> simpleIntSetComparator
                = DefaultComparators.<Integer>createDefaultSetComparatorBuilder()
                .useComparisonFeatures(EnumSet.of(SetComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_SET))
                .build();

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());

        final SetDifference<Integer> setDifference = comparisonResult.getDifferences();
        assertNotNull(setDifference);

        assertEquals(0, setDifference.getElementsOnlyInLeftSet().size());

        final Set<Integer> elementsOnlyInRightSet = setDifference.getElementsOnlyInRightSet();
        assertEquals(1, elementsOnlyInRightSet.size());
        assertTrue(elementsOnlyInRightSet.contains(3));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_elementsInBothSets() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(2, 3));

        final DefaultSetComparator<Integer> simpleIntSetComparator
                = DefaultComparators.<Integer>createDefaultSetComparatorBuilder()
                .useComparisonFeatures(EnumSet.of(SetComparator.ComparisonFeature.ELEMENTS_IN_BOTH_SETS))
                .build();

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(1, retainedElements.size());
        assertTrue(retainedElements.contains(2));

        
        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_oneComparisonFeature_elementsOnlyInLeftSet_elementsInBothSets() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(2, 3));

        final DefaultSetComparator<Integer> simpleIntSetComparator
                = DefaultComparators.<Integer>createDefaultSetComparatorBuilder()
                .useComparisonFeatures(EnumSet.of(SetComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_SET, SetComparator.ComparisonFeature.ELEMENTS_IN_BOTH_SETS))
                .build();

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(1, retainedElements.size());
        assertTrue(retainedElements.contains(2));


        assertTrue(comparisonResult.hasDifferences());

        final SetDifference<Integer> setDifference = comparisonResult.getDifferences();
        assertNotNull(setDifference);

        final Set<Integer> elementsOnlyInLeftSet = setDifference.getElementsOnlyInLeftSet();
        assertEquals(1, elementsOnlyInLeftSet.size());
        assertTrue(elementsOnlyInLeftSet.contains(1));

        assertEquals(0, setDifference.getElementsOnlyInRightSet().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_elementsOnlyInRightSet_elementsInBothSets() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1, 2));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(2, 3));

        final DefaultSetComparator<Integer> simpleIntSetComparator
                = DefaultComparators.<Integer>createDefaultSetComparatorBuilder()
                .useComparisonFeatures(EnumSet.of(SetComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_SET, SetComparator.ComparisonFeature.ELEMENTS_IN_BOTH_SETS))
                .build();

        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);


        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(1, retainedElements.size());
        assertTrue(retainedElements.contains(2));


        assertTrue(comparisonResult.hasDifferences());

        final SetDifference<Integer> setDifference = comparisonResult.getDifferences();
        assertNotNull(setDifference);

        assertEquals(0, setDifference.getElementsOnlyInLeftSet().size());

        final Set<Integer> elementsOnlyInRightSet = setDifference.getElementsOnlyInRightSet();
        assertEquals(1, elementsOnlyInRightSet.size());
        assertTrue(elementsOnlyInRightSet.contains(3));


        assertFalse(comparisonResult.hasComparisonResults());
    }

}
