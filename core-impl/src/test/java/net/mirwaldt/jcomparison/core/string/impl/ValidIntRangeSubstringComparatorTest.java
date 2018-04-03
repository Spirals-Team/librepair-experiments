package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;
import net.mirwaldt.jcomparison.core.util.view.impl.ImmutableIntRange;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class ValidIntRangeSubstringComparatorTest {

    @Test
    public void test_twoEmptyStrings() throws ComparisonFailedException {
        final String leftValue = "";
        final String rightValue = "";

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 0);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 0);


        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange);


        assertFalse(comparisonResult.hasSimilarities());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoStrings_differentAtStartAndMiddleAndEnd_fromStartToMiddle() throws ComparisonFailedException {
        final String leftValue = "abc d poi ef ghi".replaceAll(" ", "");
        final String rightValue = "cra d ixp ef".replaceAll(" ", "");

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(0, 7);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(0, 7);

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity firstSubstringCommon = substringCommons.get(0);
        assertEquals(3, firstSubstringCommon.getPositionInLeftString());
        assertEquals(3, firstSubstringCommon.getPositionInRightString());
        assertEquals("d", firstSubstringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(2, substringDifferences.size());

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

        
        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoStrings_differentAtStartAndMiddleAndEnd_fromLeftToRightWithinTheMiddle() throws ComparisonFailedException {
        final String leftValue = "abc d poi ef ghi".replaceAll(" ", "");
        final String rightValue = "cra d ixp ef".replaceAll(" ", "");

        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(3, 9);
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(2, 7);

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity firstSubstringCommon = substringCommons.get(0);
        assertEquals(3, firstSubstringCommon.getPositionInLeftString());
        assertEquals(3, firstSubstringCommon.getPositionInRightString());
        assertEquals("d", firstSubstringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(3, firstSubstringDifference.getPositionInLeftString());
        assertEquals(2, firstSubstringDifference.getPositionInRightString());
        assertEquals("", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("a", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(4, secondSubstringDifference.getPositionInLeftString());
        assertEquals(4, secondSubstringDifference.getPositionInRightString());
        assertEquals("poief", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("ixp", secondSubstringDifference.getSubstringInRightString());

        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoStrings_differentAtStartAndMiddleAndEnd_fromMiddleToEnd() throws ComparisonFailedException {
        final String leftValue = "abc d poi ef ghi".replaceAll(" ", "");
        final String rightValue = "cra d ixp ef".replaceAll(" ", "");


        final DefaultSubstringComparator substringComparator =
                DefaultComparators.createDefaultSubstringComparatorBuilder().build();

        final ImmutableIntRange leftIntRange = new ImmutableIntRange(4, leftValue.length());
        final ImmutableIntRange rightIntRange = new ImmutableIntRange(7, rightValue.length());

        final SubstringComparisonResult comparisonResult = substringComparator.compare(leftValue, rightValue, leftIntRange, rightIntRange);


        assertTrue(comparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringCommons = comparisonResult.getSimilarities();
        assertNotNull(substringCommons);
        assertEquals(1, substringCommons.size());

        final SubstringSimilarity secondSubstringCommon = substringCommons.get(0);
        assertEquals(7, secondSubstringCommon.getPositionInLeftString());
        assertEquals(7, secondSubstringCommon.getPositionInRightString());
        assertEquals("ef", secondSubstringCommon.getSimilarSubstring());


        assertTrue(comparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = comparisonResult.getDifferences();
        assertNotNull(substringDifferences);
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(4, firstSubstringDifference.getPositionInLeftString());
        assertEquals(7, firstSubstringDifference.getPositionInRightString());
        assertEquals("poi", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(9, secondSubstringDifference.getPositionInLeftString());
        assertEquals(9, secondSubstringDifference.getPositionInRightString());
        assertEquals("ghi", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("", secondSubstringDifference.getSubstringInRightString());

        assertFalse(comparisonResult.hasComparisonResults());
    }

}
