package net.mirwaldt.jcomparison.core.map.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.map.api.MapComparisonResult;
import net.mirwaldt.jcomparison.core.map.api.MapDifference;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.string.impl.ImmutableSubstringComparisonResult;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;

import static net.mirwaldt.jcomparison.core.map.api.MapComparator.ComparisonFeature.*;
import static org.junit.jupiter.api.Assertions.*;

public class OneComparisonFeatureDefaultMapComparatorTest {

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
    public void test_entriesOnlyInLeftMapOnly() throws IllegalArgumentException, ComparisonFailedException {
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
                = DefaultComparators.<Integer, Object>createDefaultMapComparatorBuilder()
                .useComparator(comparator)
                .considerComparisonFeatures(EnumSet.of(ENTRIES_ONLY_IN_LEFT_MAP))
                .build();
        final MapComparisonResult<Integer, Object> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, Object> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        final Map<Integer, Object> entriesOnlyInLeftMap = mapDifference.getEntriesOnlyInLeftMap();
        assertEquals(1, entriesOnlyInLeftMap.size());
        assertEquals(leftMap.get(1), entriesOnlyInLeftMap.get(1));

        assertEquals(0, mapDifference.getDifferentEntries().size());
        
        assertEquals(0, mapDifference.getEntriesOnlyInRightMap().size());
        

        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_entriesOnlyInRightMapOnly() throws IllegalArgumentException, ComparisonFailedException {
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
                = DefaultComparators.<Integer, Object>createDefaultMapComparatorBuilder()
                .useComparator(comparator)
                .considerComparisonFeatures(EnumSet.of(ENTRIES_ONLY_IN_RIGHT_MAP))
                .build();
        final MapComparisonResult<Integer, Object> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);
        

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, Object> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        assertEquals(0, mapDifference.getEntriesOnlyInLeftMap().size());

        assertEquals(0, mapDifference.getDifferentEntries().size());

        final Map<Integer, Object> entriesOnlyInRightMap = mapDifference.getEntriesOnlyInRightMap();
        assertEquals(1, entriesOnlyInRightMap.size());
        assertEquals(rightMap.get(8), entriesOnlyInRightMap.get(8));

        
        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_similarEntriesOnly() throws IllegalArgumentException, ComparisonFailedException {
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
                = DefaultComparators.<Integer, Object>createDefaultMapComparatorBuilder()
                .useComparator(comparator)
                .considerComparisonFeatures(EnumSet.of(SIMILAR_ENTRIES))
                .build();
        final MapComparisonResult<Integer, Object> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<Integer, ValueSimilarity<Object>> similarities = comparisonResult.getSimilarities();
        assertNotNull(similarities);
        assertEquals(1, similarities.size());

        assertEquals(leftMap.get(2), similarities.get(2).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_differentEntriesOnly() throws IllegalArgumentException, ComparisonFailedException {
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
                = DefaultComparators.<Integer, Object>createDefaultMapComparatorBuilder()
                .useComparator(comparator)
                .considerComparisonFeatures(EnumSet.of(DIFFERENT_ENTRIES))
                .build();
        final MapComparisonResult<Integer, Object> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final MapDifference<Integer, Object> mapDifference = comparisonResult.getDifferences();
        assertNotNull(mapDifference);

        assertEquals(0, mapDifference.getEntriesOnlyInLeftMap().size());

        final Map<Integer, Pair<Object>> differentValueEntries = mapDifference.getDifferentEntries();
        assertEquals(1, differentValueEntries.size());

        final Pair<Object> pair = differentValueEntries.get(3);
        assertNotNull(pair);
        assertEquals(leftMap.get(3), pair.getLeft());
        assertEquals(rightMap.get(3), pair.getRight());

        assertEquals(0, mapDifference.getEntriesOnlyInRightMap().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_compareEntriesDeepOnly() throws IllegalArgumentException, ComparisonFailedException {
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
                = DefaultComparators.<Integer, Object>createDefaultMapComparatorBuilder()
                .useComparator(comparator)
                .considerComparisonFeatures(EnumSet.of(COMPARE_ENTRIES_DEEP))
                .build();
        final MapComparisonResult<Integer, Object> comparisonResult = defaultMapComparator.compare(leftMap, rightMap);


        assertFalse(comparisonResult.hasSimilarities());

        
        assertFalse(comparisonResult.hasDifferences());

        
        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertTrue(comparisonResults.containsKey(5));
        assertEquals(ImmutableSubstringComparisonResult.class, comparisonResults.get(5).getClass());
    }
}
