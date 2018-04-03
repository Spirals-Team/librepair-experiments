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

public class FilterEntriesDefaultMapComparatorTest {
    
    @Test
    public void test_filterOutKey() throws IllegalArgumentException, ComparisonFailedException {
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
                .filterElements((entry)->1 < entry.getKey())
                .build();
        
        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<Integer, ValueSimilarity<String>> retainedEntries = comparisonResult.getSimilarities();
        assertNotNull(retainedEntries);
        assertEquals(1, retainedEntries.size());
        assertEquals("2", retainedEntries.get(2).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> addedEntries = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, addedEntries.size());
        assertEquals("5", addedEntries.get(5));

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(1, differentValueEntries.size());

        final Pair<String> pair = differentValueEntries.get(3);
        assertNotNull(pair);
        assertEquals("3", pair.getLeft());
        assertEquals("4", pair.getRight());

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, entriesOnlyInLeftMap.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_filterOutValue() throws IllegalArgumentException, ComparisonFailedException {
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
                .filterElements((entry)->!entry.getValue().equals("3"))
                .build();

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<Integer, ValueSimilarity<String>> retainedEntries = comparisonResult.getSimilarities();
        assertNotNull(retainedEntries);
        assertEquals(1, retainedEntries.size());

        assertEquals("2", retainedEntries.get(2).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> addedEntries = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, addedEntries.size());
        assertEquals("5", addedEntries.get(5));

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(0, differentValueEntries.size());

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(1, entriesOnlyInLeftMap.size());
        assertEquals("1", entriesOnlyInLeftMap.get(1));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_filterKeyAndValue() throws IllegalArgumentException, ComparisonFailedException {
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
                .filterElements((entry)-> entry.getKey() == 5 || entry.getValue().equals("2"))
                .build();

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<Integer, ValueSimilarity<String>> retainedEntries = comparisonResult.getSimilarities();
        assertNotNull(retainedEntries);
        assertEquals(1, retainedEntries.size());

        assertEquals("2", retainedEntries.get(2).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> addedEntries = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, addedEntries.size());
        assertEquals("5", addedEntries.get(5));

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(0, differentValueEntries.size());

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, entriesOnlyInLeftMap.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
