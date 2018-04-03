package net.mirwaldt.jcomparison.core.string.impl;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.api.SubstringDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringSimilarity;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class WordByWordSubstringComparatorTest {
    private DefaultSubstringComparator substringComparator =
            DefaultComparators.createDefaultSubstringComparatorBuilder()
                    .useCustomWordDelimiter((character)->" ".equals(String.valueOf((char)character)))
                    .build();

    @Test
    public void test_twoDifferentWordsLeft_oneWordRight() throws ComparisonFailedException {
        final String leftString = "abc xyz";
        final String rightString = "def ";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertFalse(substringComparisonResult.hasSimilarities());


        assertTrue(substringComparisonResult.hasDifferences());
        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("abc", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("def", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(4, secondSubstringDifference.getPositionInLeftString());
        assertEquals(4, secondSubstringDifference.getPositionInRightString());
        assertEquals("xyz", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoSimilarWordsLeft_oneWordRight() throws ComparisonFailedException {
        final String leftString = "abc xyz";
        final String rightString = "abc";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());
        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(1, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("abc", firstSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());
        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(4, firstSubstringDifference.getPositionInLeftString());
        assertEquals(3, firstSubstringDifference.getPositionInRightString());
        assertEquals("xyz", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }


    @Test
    public void test_oneWordLeft_twoDifferentWordsRight() throws ComparisonFailedException {
        final String leftString = "def";
        final String rightString = "abc xyz";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertFalse(substringComparisonResult.hasSimilarities());


        assertTrue(substringComparisonResult.hasDifferences());
        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("def", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("abc", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(3, secondSubstringDifference.getPositionInLeftString());
        assertEquals(4, secondSubstringDifference.getPositionInRightString());
        assertEquals("", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("xyz", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneWordLeft_twoSimilarWordsRight() throws ComparisonFailedException {
        final String leftString = "abc ";
        final String rightString = "abc xyz";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());
        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(1, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("abc", firstSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());
        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(4, firstSubstringDifference.getPositionInLeftString());
        assertEquals(4, firstSubstringDifference.getPositionInRightString());
        assertEquals("", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("xyz", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_twoSimilarWordsLeft_twoSimilarWordsRight() throws ComparisonFailedException {
        final String leftString = "abc xyz";
        final String rightString = "abc xyz";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());
        
        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(2, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("abc", firstSubstringSimilarity.getSimilarSubstring());
        
        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(4, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(4, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("xyz", secondSubstringSimilarity.getSimilarSubstring());


        assertFalse(substringComparisonResult.hasDifferences());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoWords_twoDifferentWordsLeft_twoSimilarWordsRight() throws ComparisonFailedException {
        final String leftString = "abc xyz";
        final String rightString = " def xyz";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(1, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(4, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(5, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("xyz", firstSubstringSimilarity.getSimilarSubstring());
        

        assertTrue(substringComparisonResult.hasDifferences());
        
        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(1, firstSubstringDifference.getPositionInRightString());
        assertEquals("abc", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("def", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_twoSimilarWordsLeft_twoDifferentWordsRight() throws ComparisonFailedException {
        final String leftString = "abc xyz";
        final String rightString = "abc  uvw";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(1, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("abc", firstSubstringSimilarity.getSimilarSubstring());

        
        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(4, firstSubstringDifference.getPositionInLeftString());
        assertEquals(5, firstSubstringDifference.getPositionInRightString());
        assertEquals("xyz", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("uvw", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoWords_twoDifferentWordsLeft_twoDifferentWordsRight() throws ComparisonFailedException {
        final String leftString = "abc xyz";
        final String rightString = "def uvw";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertFalse(substringComparisonResult.hasSimilarities());


        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("abc", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("def", firstSubstringDifference.getSubstringInRightString());


        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(4, secondSubstringDifference.getPositionInLeftString());
        assertEquals(4, secondSubstringDifference.getPositionInRightString());
        assertEquals("xyz", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("uvw", secondSubstringDifference.getSubstringInRightString());

        
        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordDifferentAtStart_secondWordSimilar() throws ComparisonFailedException {
        final String leftString = "abc xyz";
        final String rightString = "bc xyz";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());
        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(2, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(1, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("bc", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(4, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(3, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("xyz", secondSubstringSimilarity.getSimilarSubstring());
        
        
        assertTrue(substringComparisonResult.hasDifferences());
        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("a", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordDifferentAtMiddle_secondWordSimilar() throws ComparisonFailedException {
        final String leftString = "abcdeg xyz";
        final String rightString = "abfg xyz";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());
        
        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(3, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("ab", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(5, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(3, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("g", secondSubstringSimilarity.getSimilarSubstring());
        
        final SubstringSimilarity thirdSubstringSimilarity = substringSimilarities.get(2);
        assertEquals(7, thirdSubstringSimilarity.getPositionInLeftString());
        assertEquals(5, thirdSubstringSimilarity.getPositionInRightString());
        assertEquals("xyz", thirdSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());
        
        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(2, firstSubstringDifference.getPositionInLeftString());
        assertEquals(2, firstSubstringDifference.getPositionInRightString());
        assertEquals("cde", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("f", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoWords_firstWordDifferentAtEnd_secondWordSimilar() throws ComparisonFailedException {
        final String leftString = "a xyz";
        final String rightString = "ade xyz";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);

        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(2, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("a", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(2, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(4, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("xyz", secondSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(1, firstSubstringDifference.getPositionInLeftString());
        assertEquals(1, firstSubstringDifference.getPositionInRightString());
        assertEquals("", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("de", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordSimilar_secondWordDifferentAtStart() throws ComparisonFailedException {
        final String leftString = "abc xyz";
        final String rightString = "abc z";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(2, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("abc", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(6, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(4, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("z", secondSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(4, firstSubstringDifference.getPositionInLeftString());
        assertEquals(4, firstSubstringDifference.getPositionInRightString());
        assertEquals("xy", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordSimilar_secondWordDifferentAtMiddle() throws ComparisonFailedException {
        final String leftString = "ab xuv";
        final String rightString = "ab xzv";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(3, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("ab", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(3, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(3, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("x", secondSubstringSimilarity.getSimilarSubstring());
        
        final SubstringSimilarity thirdSubstringSimilarity = substringSimilarities.get(2);
        assertEquals(5, thirdSubstringSimilarity.getPositionInLeftString());
        assertEquals(5, thirdSubstringSimilarity.getPositionInRightString());
        assertEquals("v", thirdSubstringSimilarity.getSimilarSubstring());
        
        
        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(4, firstSubstringDifference.getPositionInLeftString());
        assertEquals(4, firstSubstringDifference.getPositionInRightString());
        assertEquals("u", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("z", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordSimilar_secondWordDifferentAtEnd() throws ComparisonFailedException {
        final String leftString = "abc xyzuv";
        final String rightString = "abc xyzw";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(2, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("abc", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(4, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(4, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("xyz", secondSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(1, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(7, firstSubstringDifference.getPositionInLeftString());
        assertEquals(7, firstSubstringDifference.getPositionInRightString());
        assertEquals("uv", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("w", firstSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoWords_firstWordDifferentAtStart_secondWordDifferentAtStart() throws ComparisonFailedException {
        final String leftString = "abce xyzuvw";
        final String rightString = "de yxzuvw";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(2, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(3, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(1, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("e", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(7, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(5, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("zuvw", secondSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("abc", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("d", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(5, secondSubstringDifference.getPositionInLeftString());
        assertEquals(3, secondSubstringDifference.getPositionInRightString());
        assertEquals("xy", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("yx", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }   
    
    @Test
    public void test_twoWords_firstWordDifferentAtStart_secondWordDifferentAtMiddle() throws ComparisonFailedException {
        final String leftString = "def xzu";
        final String rightString = "abcef xyzu";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(3, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(1, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(3, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("ef", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(4, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(6, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("x", secondSubstringSimilarity.getSimilarSubstring());
        
        final SubstringSimilarity thirdSubstringSimilarity = substringSimilarities.get(2);
        assertEquals(5, thirdSubstringSimilarity.getPositionInLeftString());
        assertEquals(8, thirdSubstringSimilarity.getPositionInRightString());
        assertEquals("zu", thirdSubstringSimilarity.getSimilarSubstring());
        
        
        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("d", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("abc", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(5, secondSubstringDifference.getPositionInLeftString());
        assertEquals(7, secondSubstringDifference.getPositionInRightString());
        assertEquals("", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("y", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordDifferentAtStart_secondWordDifferentAtEnd() throws ComparisonFailedException {
        final String leftString = "abcefg xyzuvw";
        final String rightString = "cdaefg xyzt";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(2, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(3, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(3, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("efg", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(7, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(7, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("xyz", secondSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(0, firstSubstringDifference.getPositionInLeftString());
        assertEquals(0, firstSubstringDifference.getPositionInRightString());
        assertEquals("abc", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("cda", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(10, secondSubstringDifference.getPositionInLeftString());
        assertEquals(10, secondSubstringDifference.getPositionInRightString());
        assertEquals("uvw", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("t", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoWords_firstWordDifferentAtMiddle_secondWordDifferentAtStart() throws ComparisonFailedException {
        final String leftString = "adce xswyzt";
        final String rightString = "abce yuvxzt";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(3, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("a", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(2, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(2, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("ce", secondSubstringSimilarity.getSimilarSubstring());
        
        final SubstringSimilarity thirdSubstringSimilarity = substringSimilarities.get(2);
        assertEquals(9, thirdSubstringSimilarity.getPositionInLeftString());
        assertEquals(9, thirdSubstringSimilarity.getPositionInRightString());
        assertEquals("zt", thirdSubstringSimilarity.getSimilarSubstring());

        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(1, firstSubstringDifference.getPositionInLeftString());
        assertEquals(1, firstSubstringDifference.getPositionInRightString());
        assertEquals("d", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("b", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(5, secondSubstringDifference.getPositionInLeftString());
        assertEquals(5, secondSubstringDifference.getPositionInRightString());
        assertEquals("xswy", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("yuvx", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordDifferentAtMiddle_secondWordDifferentAtMiddle() throws ComparisonFailedException {
        final String leftString = "abcef xuzt";
        final String rightString = "abcdf xvzt";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(4, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("abc", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(4, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(4, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("f", secondSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity thirdSubstringSimilarity = substringSimilarities.get(2);
        assertEquals(6, thirdSubstringSimilarity.getPositionInLeftString());
        assertEquals(6, thirdSubstringSimilarity.getPositionInRightString());
        assertEquals("x", thirdSubstringSimilarity.getSimilarSubstring());
        
        final SubstringSimilarity fourthSubstringSimilarity = substringSimilarities.get(3);
        assertEquals(8, fourthSubstringSimilarity.getPositionInLeftString());
        assertEquals(8, fourthSubstringSimilarity.getPositionInRightString());
        assertEquals("zt", fourthSubstringSimilarity.getSimilarSubstring());
        
        
        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(3, firstSubstringDifference.getPositionInLeftString());
        assertEquals(3, firstSubstringDifference.getPositionInRightString());
        assertEquals("e", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("d", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(7, secondSubstringDifference.getPositionInLeftString());
        assertEquals(7, secondSubstringDifference.getPositionInRightString());
        assertEquals("u", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("v", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordDifferentAtMiddle_secondWordDifferentAtEnd() throws ComparisonFailedException {
        final String leftString = "afcd xy";
        final String rightString = "abecd xyuvw";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(3, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("a", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(2, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(3, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("cd", secondSubstringSimilarity.getSimilarSubstring());
        
        final SubstringSimilarity thirdSubstringSimilarity = substringSimilarities.get(2);
        assertEquals(5, thirdSubstringSimilarity.getPositionInLeftString());
        assertEquals(6, thirdSubstringSimilarity.getPositionInRightString());
        assertEquals("xy", thirdSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(1, firstSubstringDifference.getPositionInLeftString());
        assertEquals(1, firstSubstringDifference.getPositionInRightString());
        assertEquals("f", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("be", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(7, secondSubstringDifference.getPositionInLeftString());
        assertEquals(8, secondSubstringDifference.getPositionInRightString());
        assertEquals("", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("uvw", secondSubstringDifference.getSubstringInRightString());

        assertFalse(substringComparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoWords_firstWordDifferentAtEnd_secondWordDifferentAtStart() throws ComparisonFailedException {
        final String leftString = "abcdfe z";
        final String rightString = "abcd xyz";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(2, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("abcd", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(7, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(7, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("z", secondSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(4, firstSubstringDifference.getPositionInLeftString());
        assertEquals(4, firstSubstringDifference.getPositionInRightString());
        assertEquals("fe", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(7, secondSubstringDifference.getPositionInLeftString());
        assertEquals(5, secondSubstringDifference.getPositionInRightString());
        assertEquals("", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("xy", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordDifferentAtEnd_secondWordDifferentAtMiddle() throws ComparisonFailedException {
        final String leftString = "ab xuvwz";
        final String rightString = "adef xz";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(3, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("a", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(3, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(5, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("x", secondSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity thirdSubstringSimilarity = substringSimilarities.get(2);
        assertEquals(7, thirdSubstringSimilarity.getPositionInLeftString());
        assertEquals(6, thirdSubstringSimilarity.getPositionInRightString());
        assertEquals("z", thirdSubstringSimilarity.getSimilarSubstring());

        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(1, firstSubstringDifference.getPositionInLeftString());
        assertEquals(1, firstSubstringDifference.getPositionInRightString());
        assertEquals("b", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("def", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(4, secondSubstringDifference.getPositionInLeftString());
        assertEquals(6, secondSubstringDifference.getPositionInRightString());
        assertEquals("uvw", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoWords_firstWordDifferentAtEnd_secondWordDifferentAtEnd() throws ComparisonFailedException {
        final String leftString = "abcde xyzuv";
        final String rightString = "abced xyzvu";

        final SubstringComparisonResult substringComparisonResult = substringComparator.compare(leftString, rightString);


        assertTrue(substringComparisonResult.hasSimilarities());

        final List<SubstringSimilarity> substringSimilarities = substringComparisonResult.getSimilarities();
        assertEquals(2, substringSimilarities.size());

        final SubstringSimilarity firstSubstringSimilarity = substringSimilarities.get(0);
        assertEquals(0, firstSubstringSimilarity.getPositionInLeftString());
        assertEquals(0, firstSubstringSimilarity.getPositionInRightString());
        assertEquals("abc", firstSubstringSimilarity.getSimilarSubstring());

        final SubstringSimilarity secondSubstringSimilarity = substringSimilarities.get(1);
        assertEquals(6, secondSubstringSimilarity.getPositionInLeftString());
        assertEquals(6, secondSubstringSimilarity.getPositionInRightString());
        assertEquals("xyz", secondSubstringSimilarity.getSimilarSubstring());


        assertTrue(substringComparisonResult.hasDifferences());

        final List<SubstringDifference> substringDifferences = substringComparisonResult.getDifferences();
        assertEquals(2, substringDifferences.size());

        final SubstringDifference firstSubstringDifference = substringDifferences.get(0);
        assertEquals(3, firstSubstringDifference.getPositionInLeftString());
        assertEquals(3, firstSubstringDifference.getPositionInRightString());
        assertEquals("de", firstSubstringDifference.getSubstringInLeftString());
        assertEquals("ed", firstSubstringDifference.getSubstringInRightString());

        final SubstringDifference secondSubstringDifference = substringDifferences.get(1);
        assertEquals(9, secondSubstringDifference.getPositionInLeftString());
        assertEquals(9, secondSubstringDifference.getPositionInRightString());
        assertEquals("uv", secondSubstringDifference.getSubstringInLeftString());
        assertEquals("vu", secondSubstringDifference.getSubstringInRightString());


        assertFalse(substringComparisonResult.hasComparisonResults());
    }
}
