package net.mirwaldt.jcomparison.core.collection.set;

import net.mirwaldt.jcomparison.core.collection.set.api.SetComparator;
import net.mirwaldt.jcomparison.core.collection.set.api.SetComparisonResult;
import net.mirwaldt.jcomparison.core.collection.set.api.SetDifference;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import org.junit.Test;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.Set;

import static junit.framework.TestCase.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Michael on 30.11.2017.
 */
public class FindDifferencesAndSimilaritiesSetComparisonDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInSets() throws ComparisonFailedException {
        final Set<Integer> leftSet = new LinkedHashSet<>(Arrays.asList(1, 2, 3));
        final Set<Integer> rightSet = new LinkedHashSet<>(Arrays.asList(3, null));

        final SetComparator<Integer> intSetComparator = DefaultComparators.<Integer>createDefaultSetComparatorBuilder().build();

        final SetComparisonResult<Integer> intSetComparisonResult = intSetComparator.compare(leftSet, rightSet);

        assertTrue(intSetComparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference = intSetComparisonResult.getDifferences();

        final Set<Integer> elementsOnlyInLeftSet = setDifference.getElementsOnlyInLeftSet();
        assertEquals(2, elementsOnlyInLeftSet.size());
        assertTrue(elementsOnlyInLeftSet.contains(1));
        assertTrue(elementsOnlyInLeftSet.contains(2));

        final Set<Integer> elementsOnlyInRightSet = setDifference.getElementsOnlyInRightSet();
        assertEquals(1, elementsOnlyInRightSet.size());
        assertTrue(elementsOnlyInRightSet.contains(null));

        assertTrue(intSetComparisonResult.hasSimilarities());
        final Set<Integer> similarInts = intSetComparisonResult.getSimilarities();
        assertEquals(1, similarInts.size());
        assertTrue(similarInts.contains(3));
    }
}
