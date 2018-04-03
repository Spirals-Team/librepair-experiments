package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BasicSubstringComparatorComplexTest {
    private DefaultSubstringComparator substringComparator =
            DefaultComparators.createDefaultSubstringComparatorBuilder().build();

    @Test
    public void test_twoStrings_differentAtStart() throws ComparisonFailedException {
        final String leftValue = " abc de".replaceAll(" ", "");
        final String rightValue = "l  de".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity substringCommon = substringCommons.get(0);
        assertEquals(3, substringCommon.getPositionInLeftString());
        assertEquals(1, substringCommon.getPositionInRightString());
        assertEquals("de", substringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(1, substringDifferences.size());

        final SubstringDifference substringDifference = substringDifferences.get(0);
        assertEquals(0, substringDifference.getPositionInLeftString());
        assertEquals(0, substringDifference.getPositionInRightString());
        assertEquals("abc", substringDifference.getSubstringInLeftString());
        assertEquals("l", substringDifference.getSubstringInRightString());
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtMiddle() throws ComparisonFailedException {
        final String leftValue = " ab cd e".replaceAll(" ", "");
        final String rightValue = "ab dc e".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(2, substringCommons.size());

        final SubstringSimilarity firstSubstringCommon = substringCommons.get(0);
        assertEquals(0, firstSubstringCommon.getPositionInLeftString());
        assertEquals(0, firstSubstringCommon.getPositionInRightString());
        assertEquals("ab", firstSubstringCommon.getSimilarSubstring());

        final SubstringSimilarity secondSubstringCommon = substringCommons.get(1);
        assertEquals(4, secondSubstringCommon.getPositionInLeftString());
        assertEquals(4, secondSubstringCommon.getPositionInRightString());
        assertEquals("e", secondSubstringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(1, substringDifferences.size());

        final SubstringDifference substringDifference = substringDifferences.get(0);
        assertEquals(2, substringDifference.getPositionInLeftString());
        assertEquals(2, substringDifference.getPositionInRightString());
        assertEquals("cd", substringDifference.getSubstringInLeftString());
        assertEquals("dc", substringDifference.getSubstringInRightString());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtEnd() throws ComparisonFailedException {
        final String leftValue = " abc de".replaceAll(" ", "");
        final String rightValue = "abc fg".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity substringCommon = substringCommons.get(0);
        assertEquals(0, substringCommon.getPositionInLeftString());
        assertEquals(0, substringCommon.getPositionInRightString());
        assertEquals("abc", substringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(1, substringDifferences.size());

        final SubstringDifference substringDifference = substringDifferences.get(0);
        assertEquals(3, substringDifference.getPositionInLeftString());
        assertEquals(3, substringDifference.getPositionInRightString());
        assertEquals("de", substringDifference.getSubstringInLeftString());
        assertEquals("fg", substringDifference.getSubstringInRightString());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtStartAndEnd() throws ComparisonFailedException {
        final String leftValue = " a     bc e".replaceAll(" ", "");
        final String rightValue = "lmn  bc f".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity substringCommon = substringCommons.get(0);
        assertEquals(1, substringCommon.getPositionInLeftString());
        assertEquals(3, substringCommon.getPositionInRightString());
        assertEquals("bc", substringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("a", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("lmn", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(3, secondSubstringDifference.getPositionInLeftString());
        assertEquals(5, secondSubstringDifference.getPositionInRightString());
        assertEquals("e", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("f", secondSubstringDifference.getSubstringInRightString());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtStartAndMiddle() throws ComparisonFailedException {
        final String leftValue = " ab   c de fgh".replaceAll(" ", "");
        final String rightValue = "lmn  c    fgh".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(2, substringCommons.size());

        final SubstringSimilarity firstSubstringCommon = substringCommons.get(0);
        assertEquals(2, firstSubstringCommon.getPositionInLeftString());
        assertEquals(3, firstSubstringCommon.getPositionInRightString());
        assertEquals("c", firstSubstringCommon.getSimilarSubstring());

        final SubstringSimilarity secondSubstringCommon = substringCommons.get(1);
        assertEquals(5, secondSubstringCommon.getPositionInLeftString());
        assertEquals(4, secondSubstringCommon.getPositionInRightString());
        assertEquals("fgh", secondSubstringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("ab", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("lmn", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(3, secondSubstringDifference.getPositionInLeftString());
        assertEquals(4, secondSubstringDifference.getPositionInRightString());
        assertEquals("de", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("", secondSubstringDifference.getSubstringInRightString());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtMiddleAtEnd() throws ComparisonFailedException {
        final String leftValue = " abcd ef gh  ".replaceAll(" ", "");
        final String rightValue = "abcd fe gh i".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(2, substringCommons.size());

        final SubstringSimilarity firstSubstringCommon = substringCommons.get(0);
        assertEquals(0, firstSubstringCommon.getPositionInLeftString());
        assertEquals(0, firstSubstringCommon.getPositionInRightString());
        assertEquals("abcd", firstSubstringCommon.getSimilarSubstring());

        final SubstringSimilarity secondSubstringCommon = substringCommons.get(1);
        assertEquals(6, secondSubstringCommon.getPositionInLeftString());
        assertEquals(6, secondSubstringCommon.getPositionInRightString());
        assertEquals("gh", secondSubstringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(4, firstSubstringDifference.getPositionInLeftString());
        assertEquals(4, firstSubstringDifference.getPositionInRightString());
        assertEquals("ef", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("fe", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(8, secondSubstringDifference.getPositionInLeftString());
        assertEquals(8, secondSubstringDifference.getPositionInRightString());
        assertEquals("", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("i", secondSubstringDifference.getSubstringInRightString());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtStartAndMiddleAndEnd() throws ComparisonFailedException {
        final String leftValue = " abc d poi ef ghi".replaceAll(" ", "");
        final String rightValue = "cra d ixp ef".replaceAll(" ", "");

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
