package net.mirwaldt.jcomparison.core.map.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.collection.set.impl.ImmutableSetComparisonResult;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapDifference;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.string.impl.ImmutableSubstringComparisonResult;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
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

        assertFalse(comparisonResult.hasSimilarities());
        
        
        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
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

        assertFalse(comparisonResult.hasSimilarities());

        
        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(4, entriesOnlyInRightMap.size());

        assertEquals("1", entriesOnlyInRightMap.get(1));

        assertEquals("2", entriesOnlyInRightMap.get(2));

        assertTrue(entriesOnlyInRightMap.containsKey(null));
        assertEquals("3", entriesOnlyInRightMap.get(null));

        assertTrue(entriesOnlyInRightMap.containsKey(4));
        assertEquals(null, entriesOnlyInRightMap.get(4));

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(0, differentValueEntries.size());

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, entriesOnlyInLeftMap.size());


        assertFalse(comparisonResult.hasComparisonResults());
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


        assertFalse(comparisonResult.hasSimilarities());

        
        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(0, entriesOnlyInRightMap.size());

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(0, differentValueEntries.size());

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(4, entriesOnlyInLeftMap.size());

        assertEquals("1", entriesOnlyInLeftMap.get(1));

        assertEquals("2", entriesOnlyInLeftMap.get(2));

        assertTrue(entriesOnlyInLeftMap.containsKey(3));
        assertEquals(null, entriesOnlyInLeftMap.get(3));

        assertTrue(entriesOnlyInLeftMap.containsKey(null));
        assertEquals(null, entriesOnlyInLeftMap.get(null));


        assertFalse(comparisonResult.hasComparisonResults());
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


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(0, entriesOnlyInRightMap.size());

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(4, differentValueEntries.size());

        final Pair<String> firstPair = differentValueEntries.get(1);
        assertNotNull(firstPair);
        assertEquals("1", firstPair.getLeft());
        assertEquals("2", firstPair.getRight());

        final Pair<String> secondPair = differentValueEntries.get(null);
        assertNotNull(secondPair);
        assertEquals("2", secondPair.getLeft());
        assertEquals("3", secondPair.getRight());

        final Pair<String> thirdPair = differentValueEntries.get(3);
        assertNotNull(thirdPair);
        assertNull(thirdPair.getLeft());
        assertEquals("4", thirdPair.getRight());

        final Pair<String> fourthPair = differentValueEntries.get(4);
        assertNotNull(fourthPair);
        assertEquals("3", fourthPair.getLeft());
        assertNull(fourthPair.getRight());

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, entriesOnlyInLeftMap.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneChangedEntry_specialNullValueEntry_1() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(null, null);

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(null, "3");

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(0, entriesOnlyInRightMap.size());

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(1, differentValueEntries.size());

        final Pair<String> secondPair = differentValueEntries.get(null);
        assertNotNull(secondPair);
        assertNull(secondPair.getLeft());
        assertEquals("3", secondPair.getRight());

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, entriesOnlyInLeftMap.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneChangedEntry_specialNullValueEntry_2() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(null, "1");

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(null, null);

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(0, entriesOnlyInRightMap.size());

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(1, differentValueEntries.size());

        final Pair<String> pair = differentValueEntries.get(null);
        assertNotNull(pair);
        assertEquals("1", pair.getLeft());
        assertNull(pair.getRight());

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(0, entriesOnlyInLeftMap.size());


        assertFalse(comparisonResult.hasComparisonResults());
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


        assertTrue(comparisonResult.hasSimilarities());

        final Map<Integer, ValueSimilarity<String>> similarities = comparisonResult.getSimilarities();
        assertNotNull(similarities);
        assertEquals(3, similarities.size());

        assertEquals("1", similarities.get(1).getSimilarValue());

        assertEquals("2", similarities.get(null).getSimilarValue());

        assertTrue(similarities.containsKey(3));
        assertNull(similarities.get(3).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_completelyEqualMaps_oneNullKeyNullValueEntry() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, String> leftMap = new LinkedHashMap<>();
        leftMap.put(null, null);

        final Map<Integer, String> rightMap = new LinkedHashMap<>();
        rightMap.put(null, null);

        final MapComparisonResult<Integer, String> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<Integer, ValueSimilarity<String>> similarities = comparisonResult.getSimilarities();
        assertNotNull(similarities);
        assertEquals(1, similarities.size());

        assertTrue(similarities.containsKey(null));
        assertNull(similarities.get(null).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
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


        assertTrue(comparisonResult.hasSimilarities());

        final Map<Integer, ValueSimilarity<String>> similarities = comparisonResult.getSimilarities();
        assertNotNull(similarities);
        assertEquals(1, similarities.size());

        assertEquals("2", similarities.get(2).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, String> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, String> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, entriesOnlyInRightMap.size());

        assertEquals("5", entriesOnlyInRightMap.get(5));

        final Map<Integer, Pair<String>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(1, differentValueEntries.size());

        final Pair<String> pair = differentValueEntries.get(3);
        assertNotNull(pair);
        assertEquals("3", pair.getLeft());
        assertEquals("4", pair.getRight());

        final Map<Integer, String> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(1, entriesOnlyInLeftMap.size());

        assertEquals("1", entriesOnlyInLeftMap.get(1));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    private final ItemComparator<Object, ComparisonResult<?, ?, ?>> comparator = new ItemComparator<Object, ComparisonResult<?, ?, ?>>() {
        private final ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> stringComparator = DefaultComparators.createSafeDefaultSubstringComparator(true);
        private final ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> equalsComparator = ValueComparators.immutableResultEqualsComparator();

        @Override
        public ComparisonResult<?, ?, ?> compare(Object leftItem,
                                                 Object rightItem,
                                                 VisitedObjectsTrace visitedObjectsTrace,
                                                 ComparatorOptions comparatorOptions) throws ComparisonFailedException {
            if(String.class.equals(leftItem.getClass())) {
                return stringComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
            } else {
                return equalsComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
            }
        }
    };

    @Test
    public void test_withComparisonResults() throws IllegalArgumentException, ComparisonFailedException {
        final Map<Integer, Object> leftMap = new LinkedHashMap<>();
        leftMap.put(1, true);
        leftMap.put(2, 2);
        leftMap.put(3, 3);
        leftMap.put(5, "6");

        final Map<Integer, Object> rightMap = new LinkedHashMap<>();
        rightMap.put(2, 2);
        rightMap.put(3, 4);
        rightMap.put(5, "7");
        rightMap.put(8, false);

        final DefaultMapComparator<Integer, Object> defaultMapComparator
                = DefaultComparators.<Integer, Object>createDefaultMapComparatorBuilder().useComparator(comparator).build();
        final MapComparisonResult<Integer, Object> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<Integer, ValueSimilarity<Object>> similarities = comparisonResult.getSimilarities();
        assertNotNull(similarities);
        assertEquals(1, similarities.size());

        assertEquals(leftMap.get(2), similarities.get(2).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, Object> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, Object> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(1, entriesOnlyInLeftMap.size());

        assertEquals(leftMap.get(1), entriesOnlyInLeftMap.get(1));

        final Map<Integer, Pair<Object>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(1, differentValueEntries.size());
        
        final Pair<Object> pair = differentValueEntries.get(3);
        assertNotNull(pair);
        assertEquals(leftMap.get(3), pair.getLeft());
        assertEquals(rightMap.get(3), pair.getRight());

        final Map<Integer, Object> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, entriesOnlyInRightMap.size());

        assertEquals(rightMap.get(8), entriesOnlyInRightMap.get(8));
        
        
        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertTrue(comparisonResults.containsKey(5));
        assertEquals(ImmutableSubstringComparisonResult.class, comparisonResults.get(5).getClass());
    }
}
