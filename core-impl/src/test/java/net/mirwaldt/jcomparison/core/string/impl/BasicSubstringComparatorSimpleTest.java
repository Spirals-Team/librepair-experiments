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
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BasicSubstringComparatorSimpleTest {
    private DefaultSubstringComparator substringComparator =
            DefaultComparators.createDefaultSubstringComparatorBuilder().build();

    @Test
    public void test_twoEmptyStrings() throws ComparisonFailedException {
        final String leftValue = "";
        final String rightValue = "";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);

        assertFalse(comparisonResult.hasSimilarities());

        assertFalse(comparisonResult.hasDifferences());
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoCompletelyDifferentStrings_leftEmptyString() throws ComparisonFailedException {
        final String leftValue = "";
        final String rightValue = "lmnop";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);

        assertCompletelyDifferent(leftValue, rightValue, comparisonResult);
    }

    @Test
    public void test_twoCompletelyDifferentStrings_rightEmptyString() throws ComparisonFailedException {
        final String leftValue = "abcde";
        final String rightValue = "";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);

        assertCompletelyDifferent(leftValue, rightValue, comparisonResult);
    }

    @Test
    public void test_twoCompletelyDifferentStrings_sameLength() throws ComparisonFailedException {
        final String leftValue = "abcde";
        final String rightValue = "lmnop";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);

        assertCompletelyDifferent(leftValue, rightValue, comparisonResult);
    }

    @Test
    public void test_twoCompletelyDifferentStrings_leftLonger() throws ComparisonFailedException {
        final String leftValue = "abcde";
        final String rightValue = "lmn";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);

        assertCompletelyDifferent(leftValue, rightValue, comparisonResult);
    }

    @Test
    public void test_twoCompletelyDifferentStrings_rightLonger() throws ComparisonFailedException {
        final String leftValue = "abc";
        final String rightValue = "lmnop";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);

        assertCompletelyDifferent(leftValue, rightValue, comparisonResult);
    }

    @Test
    public void test_equalNonEmptyStrings() throws ComparisonFailedException {
        final String leftValue = "abcde";
        final String rightValue = "abcde";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity substringCommon = substringCommons.get(0);
        assertEquals(0, substringCommon.getPositionInLeftString());
        assertEquals(0, substringCommon.getPositionInRightString());
        assertEquals(leftValue, substringCommon.getSimilarSubstring());


        assertFalse(comparisonResult.hasDifferences());
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_special1() throws ComparisonFailedException {
        final String leftValue = "poi";
        final String rightValue = "iop";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity substringCommon = substringCommons.get(0);
        assertEquals(1, substringCommon.getPositionInLeftString());
        assertEquals(1, substringCommon.getPositionInRightString());
        assertEquals("o", substringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("p", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("i", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(2, secondSubstringDifference.getPositionInLeftString());
        assertEquals(2, secondSubstringDifference.getPositionInRightString());
        assertEquals("i", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("p", secondSubstringDifference.getSubstringInRightString());
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_special2() throws ComparisonFailedException {
        final String leftValue = "poi";
        final String rightValue = "ilp";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(1, substringDifferences.size());

        final SubstringDifference substringDifference = substringDifferences.get(0);
        assertEquals(0, substringDifference.getPositionInLeftString());
        assertEquals(0, substringDifference.getPositionInRightString());
        assertEquals("poi", substringDifference.getSubstringInLeftString());
        assertEquals("ilp", substringDifference.getSubstringInRightString());
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_special3() throws ComparisonFailedException {
        final String leftValue = "poi";
        final String rightValue = "ip";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity substringCommon = substringCommons.get(0);
        assertEquals(0, substringCommon.getPositionInLeftString());
        assertEquals(1, substringCommon.getPositionInRightString());
        assertEquals("p", substringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("i", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(1, secondSubstringDifference.getPositionInLeftString());
        assertEquals(2, secondSubstringDifference.getPositionInRightString());
        assertEquals("oi", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("", secondSubstringDifference.getSubstringInRightString());
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_twoStrings_special4() throws ComparisonFailedException {
        final String leftValue = "ip";
        final String rightValue = "poi";

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity substringCommon = substringCommons.get(0);
        assertEquals(1, substringCommon.getPositionInLeftString());
        assertEquals(0, substringCommon.getPositionInRightString());
        assertEquals("p", substringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("i", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(2, secondSubstringDifference.getPositionInLeftString());
        assertEquals(1, secondSubstringDifference.getPositionInRightString());
        assertEquals("", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("oi", secondSubstringDifference.getSubstringInRightString());

        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    private void assertCompletelyDifferent(final String leftValue, final String rightValue,
                                           final SubstringComparisonResult comparisonResult) {
        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(1, substringDifferences.size());

        final SubstringDifference substringDifference = substringDifferences.get(0);
        assertEquals(0, substringDifference.getPositionInLeftString());
        assertEquals(0, substringDifference.getPositionInRightString());
        assertEquals(leftValue, substringDifference.getSubstringInLeftString());
        assertEquals(rightValue, substringDifference.getSubstringInRightString());


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
