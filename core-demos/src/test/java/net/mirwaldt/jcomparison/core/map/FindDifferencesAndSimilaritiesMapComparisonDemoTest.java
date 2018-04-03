package net.mirwaldt.jcomparison.core.map;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.map.api.MapComparator;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapDifference;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Michael on 28.11.2017.
 */
public class FindDifferencesAndSimilaritiesMapComparisonDemoTest {
    @Test
    public void test_findDifferencesAndSimilaritiesInMaps() throws ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(1, "1");
        leftMap.put(2, "3");
        leftMap.put(5, "5");

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(1, "1");
        rightMap.put(2, "4");
        rightMap.put(6, "6");

        final MapComparator<Integer, String> intToStringMapComparator =
                DefaultComparators.<Integer, String>createDefaultMapComparatorBuilder().build();

        final MapComparisonResult<Integer, String> intToStringMapComparisonResult =
                intToStringMapComparator.compare(leftMap, rightMap);

        assertTrue(intToStringMapComparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = intToStringMapComparisonResult.getDifferences();

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(1, entriesOnlyInLeftMap.size());
        assertTrue(entriesOnlyInLeftMap.containsKey(5));
        assertEquals("5", entriesOnlyInLeftMap.get(5));

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(1, differentValueEntries.size());
        assertTrue(differentValueEntries.containsKey(2));
        assertEquals(new ImmutablePair<String>("3", "4"), differentValueEntries.get(2));

        final Map<Integer, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, entriesOnlyInRightMap.size());
        assertTrue(entriesOnlyInRightMap.containsKey(6));
        assertEquals("6", entriesOnlyInRightMap.get(6));

        assertTrue(intToStringMapComparisonResult.hasSimilarities());
        final Map<Integer, ValueSimilarity<String>> similarity = intToStringMapComparisonResult.getSimilarities();
        assertEquals(1, similarity.size());
        assertTrue(similarity.containsKey(1));
        assertEquals("1", similarity.get(1).getSimilarValue());
    }
}
