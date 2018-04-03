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

public class FilterElementsDefaultSetComparatorTest {
    
    @Test
    public void test_filterOutAllBelowThree() throws IllegalArgumentException, ComparisonFailedException {
        final Set<Integer> leftSet = new HashSet<>(Arrays.asList(1,2,3));
        final Set<Integer> rightSet = new HashSet<>(Arrays.asList(2,3,4));

        final DefaultSetComparator<Integer> simpleIntSetComparator
                = DefaultComparators.<Integer>createDefaultSetComparatorBuilder().filterElements((element)->2 < element).build();
        
        final SetComparisonResult<Integer> comparisonResult = simpleIntSetComparator.compare(leftSet, rightSet);
        

        assertTrue(comparisonResult.hasSimilarities());

        final Set<Integer> retainedElements = comparisonResult.getSimilarities();
        assertNotNull(retainedElements);
        assertEquals(1, retainedElements.size());
        assertTrue(retainedElements.contains(3));


        assertTrue(comparisonResult.hasDifferences());
        
        final SetDifference<Integer> setDifference =  comparisonResult.getDifferences();
        assertNotNull(setDifference);

        assertEquals(0, setDifference.getElementsOnlyInLeftSet().size());
        
        final Set<Integer> elementsOnlyInRightSet = setDifference.getElementsOnlyInRightSet();
        assertEquals(1, elementsOnlyInRightSet.size());
        assertTrue(elementsOnlyInRightSet.contains(4));


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
