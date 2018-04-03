package net.mirwaldt.jcomparison.core.collection.list.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparator;
import net.mirwaldt.jcomparison.core.collection.list.api.ListComparisonResult;
import net.mirwaldt.jcomparison.core.collection.list.api.ListDifference;
import net.mirwaldt.jcomparison.core.collection.list.api.ListSimilarity;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.Test;

import java.util.Arrays;
import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

public class FirstHitListComparatorTest {

    final ItemComparator<Object, ComparisonResult<?, ?, ?>> comparator = new ItemComparator<Object, ComparisonResult<?, ?, ?>>() {
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
    public void test_oneComparisonFeature_elementsOnlyInLeftList() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1, 2);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(1, elementsOnlyInLeftList.size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());
        
        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_elementsOnlyInRightList() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1, 2);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(1, elementsOnlyInRightList.size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());
        
        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_similarElements() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 5, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_ELEMENTS
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());
        
        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_differentElements() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_ELEMENTS
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_similarFrequencies() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        
        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_differentFrequencies_leftElementGone() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(1, differentFrequencies.size());
        
        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }


    @Test
    public void test_oneComparisonFeature_differentFrequencies_righElementGone() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3);
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(1, differentFrequencies.size());
        
        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_differentFrequencies_frequencyChange() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 3, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(1, differentFrequencies.size());

        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_oneComparisonFeature_differentFrequencies_frequencyChangeOnly() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 3);
        final List<Object> rightList = Arrays.asList(3);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(1, differentFrequencies.size());
        
        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_compareElementsDeep() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", "xzy");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", "st", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertFalse(comparisonResult.hasDifferences());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
    }

    @Test
    public void test_oneComparisonFeature_similarPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 4, "abc");
        final List<Object> rightList = Arrays.asList(3, 4, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_POSITIONS
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());
        
        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_oneComparisonFeature_differentPositions_additionalDuplicateInLeftList() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 3);
        final List<Object> rightList = Arrays.asList(3, 7);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());
        
        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(1, differentPositions.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_oneComparisonFeature_differentPositions_additionalDuplicateInRightList() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 7);
        final List<Object> rightList = Arrays.asList(3, 3);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(1, differentPositions.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeature_differentPositions_onlyOneLementInTheLeftList() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3);
        final List<Object> rightList = Arrays.asList();

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(1, differentPositions.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_oneComparisonFeature_differentPositions_onlyOneLementInTheRightList() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList();
        final List<Object> rightList = Arrays.asList(7);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .findFirstResultOnly()
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(1, differentPositions.size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
}
