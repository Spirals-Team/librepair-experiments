package net.mirwaldt.jcomparison.core.map.impl;

import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapDifference;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class FirstHitDefaultMapComparatorTest {
    
    @Test
    public void test_findFirstDifference() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(1, "1");
        leftMap.put(2, "2");
        leftMap.put(3, "3");

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(2, "2");
        rightMap.put(3, "4");
        rightMap.put(5, "5");
        
        final DefaultMapComparator<Integer, String> defaultMapComparator
                = DefaultComparators.<Integer, String>createDefaultMapComparatorBuilder()
                .findDifferencesOnly()
                .findFirstResultOnly()
                .build();
        
        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(1, entriesOnlyInRightMap.size() + differentValueEntries.size() + entriesOnlyInLeftMap.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_findFirstSimilarity() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(1, "1");
        leftMap.put(2, "2");
        leftMap.put(3, "3");

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(2, "2");
        rightMap.put(3, "3");
        rightMap.put(5, "5");

        final DefaultMapComparator<Integer, String> defaultMapComparator
                = DefaultComparators.<Integer, String>createDefaultMapComparatorBuilder()
                .findSimilaritiesOnly()
                .findFirstResultOnly()
                .build();

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<Integer, ValueSimilarity<String>> retainedEntries = comparisonResult.getSimilarities();
        assertNotNull(retainedEntries);
        assertEquals(1, retainedEntries.size());
        

        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
