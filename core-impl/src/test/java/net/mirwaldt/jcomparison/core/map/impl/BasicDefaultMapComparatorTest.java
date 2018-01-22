package net.mirwaldt.jcomparison.core.map.impl;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapDifference;
import org.junit.jupiter.api.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class BasicDefaultMapComparatorTest {

    private DefaultMapComparator<Integer, String> defaultMapComparator
            = DefaultComparators.<Integer, String>createDefaultMapComparatorBuilder().build();

    @Test
    public void test_emptyMaps() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();

        final Map<Integer, String> rightMap = new LinkedHashMap<>();

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);

        assertFalse(comparisonResult.hasSimilarity());
        assertFalse(comparisonResult.hasDifference());
    }

    @Test
    public void test_leftMapEmpty() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(1, "1");
        rightMap.put(2, "2");
        rightMap.put(null, "3");
        rightMap.put(4, null);

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);

        assertFalse(comparisonResult.hasSimilarity());

        assertTrue(comparisonResult.hasDifference());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifference();
        assertNotNull(mapDifference);

        final Map<Integer, String> addedEntries = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(4, addedEntries.size());

        assertEquals("1", addedEntries.get(1));

        assertEquals("2", addedEntries.get(2));

        assertTrue(addedEntries.containsKey(null));
        assertEquals("3", addedEntries.get(null));

        assertTrue(addedEntries.containsKey(4));
        assertEquals(null, addedEntries.get(4));

        final Map<Integer, Pair<String>> changedEntries = mapDifference.getDifferentValueEntries();
        assertEquals(0, changedEntries.size());

        final Map<Integer, String> removedEntries = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, removedEntries.size());
    }

    @Test
    public void test_rightMapEmpty() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(1, "1");
        leftMap.put(2, "2");
        leftMap.put(3, null);
        leftMap.put(null, null);

        final Map<Integer, String> rightMap = new LinkedHashMap<>();

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertFalse(comparisonResult.hasSimilarity());

        assertTrue(comparisonResult.hasDifference());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifference();
        assertNotNull(mapDifference);

        final Map<Integer, String> addedEntries = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(0, addedEntries.size());

        final Map<Integer, Pair<String>> changedEntries = mapDifference.getDifferentValueEntries();
        assertEquals(0, changedEntries.size());

        final Map<Integer, String> removedEntries = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(4, removedEntries.size());

        assertEquals("1", removedEntries.get(1));

        assertEquals("2", removedEntries.get(2));

        assertTrue(removedEntries.containsKey(3));
        assertEquals(null, removedEntries.get(3));

        assertTrue(removedEntries.containsKey(null));
        assertEquals(null, removedEntries.get(null));
    }

    @Test
    public void test_twoCompletelyDifferentMaps_sameSize() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(1, "1");
        leftMap.put(null, "2");
        leftMap.put(3, null);
        leftMap.put(4, "3");

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(1, "2");
        rightMap.put(null, "3");
        rightMap.put(3, "4");
        rightMap.put(4, null);

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifference();
        assertNotNull(mapDifference);

        final Map<Integer, String> addedEntries = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(0, addedEntries.size());

        final Map<Integer, Pair<String>> changedEntries = mapDifference.getDifferentValueEntries();
        assertEquals(4, changedEntries.size());

        final Pair<String> firstPair = changedEntries.get(1);
        assertNotNull(firstPair);
        assertEquals("1", firstPair.getLeft());
        assertEquals("2", firstPair.getRight());

        final Pair<String> secondPair = changedEntries.get(null);
        assertNotNull(secondPair);
        assertEquals("2", secondPair.getLeft());
        assertEquals("3", secondPair.getRight());

        final Pair<String> thirdPair = changedEntries.get(3);
        assertNotNull(thirdPair);
        assertNull(thirdPair.getLeft());
        assertEquals("4", thirdPair.getRight());

        final Pair<String> fourthPair = changedEntries.get(4);
        assertNotNull(fourthPair);
        assertEquals("3", fourthPair.getLeft());
        assertNull(fourthPair.getRight());

        final Map<Integer, String> removedEntries = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, removedEntries.size());
    }

    @Test
    public void test_oneChangedEntry_specialNullValueEntry_1() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(null, null);

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(null, "3");

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifference();
        assertNotNull(mapDifference);

        final Map<Integer, String> addedEntries = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(0, addedEntries.size());

        final Map<Integer, Pair<String>> changedEntries = mapDifference.getDifferentValueEntries();
        assertEquals(1, changedEntries.size());

        final Pair<String> secondPair = changedEntries.get(null);
        assertNotNull(secondPair);
        assertNull(secondPair.getLeft());
        assertEquals("3", secondPair.getRight());

        final Map<Integer, String> removedEntries = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, removedEntries.size());
    }

    @Test
    public void test_oneChangedEntry_specialNullValueEntry_2() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(null, "1");

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(null, null);

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifference();
        assertNotNull(mapDifference);

        final Map<Integer, String> addedEntries = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(0, addedEntries.size());

        final Map<Integer, Pair<String>> changedEntries = mapDifference.getDifferentValueEntries();
        assertEquals(1, changedEntries.size());

        final Pair<String> pair = changedEntries.get(null);
        assertNotNull(pair);
        assertEquals("1", pair.getLeft());
        assertNull(pair.getRight());

        final Map<Integer, String> removedEntries = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, removedEntries.size());
    }

    @Test
    public void test_completelyEqualMaps() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(1, "1");
        leftMap.put(null, "2");
        leftMap.put(3, null);

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(1, "1");
        rightMap.put(null, "2");
        rightMap.put(3, null);

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarity());

        final Map<Integer, String> retainedEntries = comparisonResult.getSimilarity();
        assertNotNull(retainedEntries);
        assertEquals(3, retainedEntries.size());

        assertEquals("1", retainedEntries.get(1));

        assertEquals("2", retainedEntries.get(null));

        assertTrue(retainedEntries.containsKey(3));
        assertNull(retainedEntries.get(3));


        assertFalse(comparisonResult.hasDifference());
    }

    @Test
    public void test_completelyEqualMaps_oneNullKeyNullValueEntry() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(null, null);

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(null, null);

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarity());

        final Map<Integer, String> retainedEntries = comparisonResult.getSimilarity();
        assertNotNull(retainedEntries);
        assertEquals(1, retainedEntries.size());

        assertTrue(retainedEntries.containsKey(null));
        assertNull(retainedEntries.get(null));


        assertFalse(comparisonResult.hasDifference());
    }

    @Test
    public void test_differentMaps() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(1, "1");
        leftMap.put(2, "2");
        leftMap.put(3, "3");

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(2, "2");
        rightMap.put(3, "4");
        rightMap.put(5, "5");

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarity());

        final Map<Integer, String> retainedEntries = comparisonResult.getSimilarity();
        assertNotNull(retainedEntries);
        assertEquals(1, retainedEntries.size());

        assertEquals("2", retainedEntries.get(2));


        assertTrue(comparisonResult.hasDifference());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifference();
        assertNotNull(mapDifference);

        final Map<Integer, String> addedEntries = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, addedEntries.size());

        assertEquals("5", addedEntries.get(5));

        final Map<Integer, Pair<String>> changedEntries = mapDifference.getDifferentValueEntries();
        assertEquals(1, changedEntries.size());

        final Pair<String> pair = changedEntries.get(3);
        assertNotNull(pair);
        assertEquals("3", pair.getLeft());
        assertEquals("4", pair.getRight());

        final Map<Integer, String> removedEntries = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(1, removedEntries.size());

        assertEquals("1", removedEntries.get(1));
    }
}
