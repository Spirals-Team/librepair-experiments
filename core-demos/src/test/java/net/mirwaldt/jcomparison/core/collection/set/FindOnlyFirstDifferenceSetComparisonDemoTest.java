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
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Michael on 30.11.2017.
 */
public class FindOnlyFirstDifferenceSetComparisonDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInSets() throws ComparisonFailedException {
        final Set<Integer> leftSet = new LinkedHashSet<>(Arrays.asList(1, 2, 3));
        final Set<Integer> rightSet = new LinkedHashSet<>(Arrays.asList(3, null));

        final SetComparator<Integer> intSetComparator = DefaultComparators.<Integer>createDefaultSetComparatorBuilder().findDifferencesOnly().findFirstResultOnly().build();

        final SetComparisonResult<Integer> intSetComparisonResult = intSetComparator.compare(leftSet, rightSet);

        assertTrue(intSetComparisonResult.hasDifferences());
        final SetDifference<Integer> setDifference = intSetComparisonResult.getDifferences();

        final Set<Integer> elementsOnlyInLeftSet = setDifference.getElementsOnlyInLeftSet();
        final Set<Integer> elementsOnlyInRightSet = setDifference.getElementsOnlyInRightSet();
        assertEquals(1, elementsOnlyInLeftSet.size() + elementsOnlyInRightSet.size());

        assertFalse(intSetComparisonResult.hasSimilarities());
    }
}
