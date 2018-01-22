package net.mirwaldt.jcomparison.core.string;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;
import net.mirwaldt.jcomparison.core.string.impl.DefaultLongestSubstringComparator;
import net.mirwaldt.jcomparison.core.util.SubstringComparisonResultFormatter;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordDelimiterLongestSubstringComparatorTest {

    private DefaultLongestSubstringComparator substringComparator =
            DefaultComparators.createDefaultLongestSubstringComparatorBuilder()
                    .useCustomWordDelimiter((character)->" ".equals(String.valueOf((char)character)))
                    .build();
    
    @Test
    public void test() throws ComparisonFailedException {
        final String leftString = "This is a car.";
        final String rightString = "That is awesome.";

        SubstringComparisonResult longestSubstringComparisonResult = substringComparator.compare(leftString, rightString);
        
        assertTrue(longestSubstringComparisonResult.hasDifference());
        final List<SubstringDifference> substringDifferences = longestSubstringComparisonResult.getDifference();
        assertEquals(3, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(2, firstSubstringDifference.getPositionInLeftString());
        assertEquals(2, firstSubstringDifference.getPositionInRightString());
        assertEquals("is", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("at", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(9, secondSubstringDifference.getPositionInLeftString());
        assertEquals(9, secondSubstringDifference.getPositionInRightString());
        assertEquals("", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("wesome.", secondSubstringDifference.getSubstringInRightString());

        final SubstringDifference thirdSubstringDifference = substringDifferences.get(2);
        assertEquals(10, thirdSubstringDifference.getPositionInLeftString());
        assertEquals(16, thirdSubstringDifference.getPositionInRightString());
        assertEquals("car.", thirdSubstringDifference.getSubstringInLeftString());
        assertEquals("", thirdSubstringDifference.getSubstringInRightString());

        
        assertTrue(longestSubstringComparisonResult.hasSimilarity());
        final List<SubstringSimilarity> substringSimilarities = longestSubstringComparisonResult.getSimilarity();
        assertEquals(3, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("Th", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(5, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(5, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("is", secondSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity thirdSubstringSimilarity = substringSimilarities.get(2);
        assertEquals(8, thirdSubstringSimilarity.getPositionInLeftString());
        assertEquals(8, thirdSubstringSimilarity.getPositionInRightString());
        assertEquals("a", thirdSubstringSimilarity.getSimilarSubstring());

        
        assertFalse(longestSubstringComparisonResult.hasComparisonResults());
    }
    
}
