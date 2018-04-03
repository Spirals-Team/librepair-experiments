package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class FirstHitSubstringComparatorTest {

    private DefaultSubstringComparator substringComparator
            = DefaultComparators.createDefaultSubstringComparatorBuilder().findFirstResultOnly().build();

    @Test
    public void test_twoStrings_differentAtStart() throws ComparisonFailedException {
        final String leftValue = " abc de".replaceAll(" ", "");
        final String rightValue = "l  de".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertFalse(comparisonResult.hasSimilarities());


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
        final String leftValue = " ab  cd e".replaceAll(" ", "");
        final String rightValue = "ab  dc e".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity firstSubstringCommon = substringCommons.get(0);
        assertEquals(0, firstSubstringCommon.getPositionInLeftString());
        assertEquals(0, firstSubstringCommon.getPositionInRightString());
        assertEquals("ab", firstSubstringCommon.getSimilarSubstring());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtEnd() throws ComparisonFailedException {
        final String leftValue = " abc  de".replaceAll(" ", "");
        final String rightValue = "abc  fg".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity substringCommon = substringCommons.get(0);
        assertEquals(0, substringCommon.getPositionInLeftString());
        assertEquals(0, substringCommon.getPositionInRightString());
        assertEquals("abc", substringCommon.getSimilarSubstring());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtStartAndEnd() throws ComparisonFailedException {
        final String leftValue = "a    bc e".replaceAll(" ", "");
        final String rightValue = "lmn  bc f".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("a", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("lmn", firstSubstringDifference.getSubstringInRightString());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtStartAndMiddle() throws ComparisonFailedException {
        final String leftValue = "ab   c de fgh".replaceAll(" ", "");
        final String rightValue = "lmn  c    fgh".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("ab", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("lmn", firstSubstringDifference.getSubstringInRightString());


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
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity firstSubstringCommon = substringCommons.get(0);
        assertEquals(0, firstSubstringCommon.getPositionInLeftString());
        assertEquals(0, firstSubstringCommon.getPositionInRightString());
        assertEquals("abcd", firstSubstringCommon.getSimilarSubstring());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtStartAndMiddleAndEnd() throws ComparisonFailedException {
        final String leftValue = "abc d poi ef ghi".replaceAll(" ", "");
        final String rightValue = "cra d iop ef".replaceAll(" ", "");

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("abc", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("cra", firstSubstringDifference.getSubstringInRightString());


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
