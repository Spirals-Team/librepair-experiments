package net.mirwaldt.jcomparison.core.map;

import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.map.api.MapComparator;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapDifference;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 28.11.2017.
 */
public class FindFirstDifferenceOnlyMapComparisonDemoTest {
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
                DefaultComparators.<Integer, String>createDefaultMapComparatorBuilder().findDifferencesOnly().findFirstResultOnly().build();

        final MapComparisonResult<Integer, String> intToStringMapComparisonResult =
                intToStringMapComparator.compare(leftMap, rightMap);

        assertTrue(intToStringMapComparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = intToStringMapComparisonResult.getDifferences();

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        final Map<Integer, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, entriesOnlyInLeftMap.size() + differentValueEntries.size() + entriesOnlyInRightMap.size());

        assertFalse(intToStringMapComparisonResult.hasSimilarities());
    }
}
