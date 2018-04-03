package net.mirwaldt.jcomparison.core.string;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparator;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
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
public class FindOnlyDifferencesSubstringComparisonDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInStrings()
            throws IllegalArgumentException, ComparisonFailedException {
        final String leftString = "This is a car and I drove it.";
        final String rightString = "That is awesome and I love it.";

        final SubstringComparator substringComparator = createDefaultSubstringComparatorBuilder().findDifferencesOnly().build();

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);

        assertTrue(substringComparisonResult.hasDifferences());
        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(6, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(2, firstSubstringDifference.getPositionInLeftString());
        assertEquals(2, firstSubstringDifference.getPositionInRightString());
        assertEquals("", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("at ", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(5, secondSubstringDifference.getPositionInLeftString());
        assertEquals(8, secondSubstringDifference.getPositionInRightString());
        assertEquals("is ", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("", secondSubstringDifference.getSubstringInRightString());

        final SubstringDifference thirdSubstringDifference = substringDifferences.get(2);
        assertEquals(9, thirdSubstringDifference.getPositionInLeftString());
        assertEquals(9, thirdSubstringDifference.getPositionInRightString());
        assertEquals("", thirdSubstringDifference.getSubstringInLeftString());
        assertEquals("wesome", thirdSubstringDifference.getSubstringInRightString());

        final SubstringDifference fourthSubstringDifference = substringDifferences.get(3);
        assertEquals(10, fourthSubstringDifference.getPositionInLeftString());
        assertEquals(16, fourthSubstringDifference.getPositionInRightString());
        assertEquals("c", fourthSubstringDifference.getSubstringInLeftString());
        assertEquals("", fourthSubstringDifference.getSubstringInRightString());

        final SubstringDifference fifthSubstringDifference = substringDifferences.get(4);
        assertEquals(12, fifthSubstringDifference.getPositionInLeftString());
        assertEquals(17, fifthSubstringDifference.getPositionInRightString());
        assertEquals("r a", fifthSubstringDifference.getSubstringInLeftString());
        assertEquals("", fifthSubstringDifference.getSubstringInRightString());

        final SubstringDifference sixthSubstringDifference = substringDifferences.get(5);
        assertEquals(20, sixthSubstringDifference.getPositionInLeftString());
        assertEquals(22, sixthSubstringDifference.getPositionInRightString());
        assertEquals("dr", sixthSubstringDifference.getSubstringInLeftString());
        assertEquals("l", sixthSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasSimilarities());
    }
}
