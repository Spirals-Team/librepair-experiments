package net.mirwaldt.jcomparison.core.string;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparator;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import org.junit.Test;

import java.util.List;

import static net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators.createDefaultSubstringComparatorBuilder;
import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Michael on 06.12.2017.
 */
public class FindOnlyFirstDifferenceSubstringComparisonDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInStrings()
            throws IllegalArgumentException, ComparisonFailedException {
        final String leftString = "This is a car and I drove it.";
        final String rightString = "That is awesome and I love it.";

        final SubstringComparator substringComparator = createDefaultSubstringComparatorBuilder().findDifferencesOnly().findFirstResultOnly().build();

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);

        assertTrue(substringComparisonResult.hasDifferences());
        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(2, firstSubstringDifference.getPositionInLeftString());
        assertEquals(2, firstSubstringDifference.getPositionInRightString());
        assertEquals("", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("at ", firstSubstringDifference.getSubstringInRightString());

        assertFalse(substringComparisonResult.hasSimilarities());
    }
}
