package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class ComparisonFeatureSubstringComparatorTest {

    @Test
    public void test_twoStrings_differentAtStartAndMiddleAndEnd_similaritiesOnly() throws ComparisonFailedException {
        final String leftValue = "abc d poi ef ghi".replaceAll(" ", "");
        final String rightValue = "cra d ixp ef".replaceAll(" ", "");
        
        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().findSimilaritiesOnly().build();
        
        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(2, substringCommons.size());

        final SubstringSimilarity firstSubstringCommon = substringCommons.get(0);
        assertEquals(3, firstSubstringCommon.getPositionInLeftString());
        assertEquals(3, firstSubstringCommon.getPositionInRightString());
        assertEquals("d", firstSubstringCommon.getSimilarSubstring());

        final SubstringSimilarity secondSubstringCommon = substringCommons.get(1);
        assertEquals(7, secondSubstringCommon.getPositionInLeftString());
        assertEquals(7, secondSubstringCommon.getPositionInRightString());
        assertEquals("ef", secondSubstringCommon.getSimilarSubstring());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoStrings_differentAtStartAndMiddleAndEnd_differencesOnly() throws ComparisonFailedException {
        final String leftValue = "abc d poi ef ghi".replaceAll(" ", "");
        final String rightValue = "cra d ixp ef".replaceAll(" ", "");

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().findDifferencesOnly().build();

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertFalse(comparisonResult.hasSimilarities());

        
        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(3, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("abc", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("cra", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(4, secondSubstringDifference.getPositionInLeftString());
        assertEquals(4, secondSubstringDifference.getPositionInRightString());
        assertEquals("poi", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("ixp", secondSubstringDifference.getSubstringInRightString());

        final SubstringDifference thirdSubstringDifference = substringDifferences.get(2);
        assertEquals(9, thirdSubstringDifference.getPositionInLeftString());
        assertEquals(9, thirdSubstringDifference.getPositionInRightString());
        assertEquals("ghi", thirdSubstringDifference.getSubstringInLeftString());
        assertEquals("", thirdSubstringDifference.getSubstringInRightString());


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
