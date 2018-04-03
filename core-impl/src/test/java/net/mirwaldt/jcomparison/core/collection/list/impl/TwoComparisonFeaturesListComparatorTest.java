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
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutablePair;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableIntListFactory;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.Test;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class TwoComparisonFeaturesListComparatorTest {
    
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
    public void test_twoComparisonFeatures_elementsOnlyInLeftList_elementsOnlyInLeftList_elementInLeftList() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST,
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST                                
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(3, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInLeftList.get(leftList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInLeftList.get(leftList.get(3)));

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(2, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInRightList.get(rightList.get(2)));


        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_elementsOnlyInLeftList_similarElements() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST,
                                ListComparator.ComparisonFeature.SIMILAR_ELEMENTS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        

        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(3, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInLeftList.get(leftList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInLeftList.get(leftList.get(3)));
        
        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        
        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_elementsOnlyInLeftList_similarFrequencies() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST,
                                ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));
        
        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        
        
        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(3, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInLeftList.get(leftList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInLeftList.get(leftList.get(3)));

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_elementsOnlyInLeftList_differentElements() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST,
                                ListComparator.ComparisonFeature.DIFFERENT_ELEMENTS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);

        
        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(3, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInLeftList.get(leftList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInLeftList.get(leftList.get(3)));

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair((int)leftList.get(1), (int)rightList.get(1)), differentElements.get(1));

        assertEquals(0, listDifference.getDifferentFrequencies().size());
        
        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_elementsOnlyInLeftList_differentFrequencies() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST,
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);
        

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(3, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInLeftList.get(leftList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInLeftList.get(leftList.get(3)));

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(5, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));

        assertEquals(0, listDifference.getDifferentPositions().size());
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_elementsOnlyInLeftList_similarPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST,
                                ListComparator.ComparisonFeature.SIMILAR_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        final ImmutableIntList immutableIntList = similarPositions.get(leftList.get(0));
        assertEquals(1, immutableIntList.size());
        assertEquals(0, immutableIntList.getInt(0));


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(3, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInLeftList.get(leftList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInLeftList.get(leftList.get(3)));

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());
        
        assertEquals(0, listDifference.getDifferentFrequencies().size());
        
        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_elementsOnlyInLeftList_differentPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST,
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);
        

        assertFalse(comparisonResult.hasSimilarities());
        

        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(3, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInLeftList.get(leftList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInLeftList.get(leftList.get(3)));

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());
        
        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(5, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(3)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(3)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_elementsOnlyInLeftList_compareElementsDeep() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_LEFT_LIST,
                                ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);

        
        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        final Map<Object, ImmutableIntList> elementsOnlyInLeftList = listDifference.getElementsOnlyInLeftList();
        assertEquals(3, elementsOnlyInLeftList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInLeftList.get(leftList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInLeftList.get(leftList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInLeftList.get(leftList.get(3)));

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertNotNull(comparisonResults.get(2));
    }

    @Test
    public void test_twoComparisonFeatures_elementsOnlyInRightList_similarElements() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST,
                                ListComparator.ComparisonFeature.SIMILAR_ELEMENTS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());
        
        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        
        
        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(3, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInRightList.get(rightList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInRightList.get(rightList.get(3)));

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_elementsOnlyInRightList_similarFrequencies() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST,
                                ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));

        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        

        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(3, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInRightList.get(rightList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInRightList.get(rightList.get(3)));

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_elementsOnlyInRightList_differentElements() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST,
                                ListComparator.ComparisonFeature.DIFFERENT_ELEMENTS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);
        

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(3, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(2)), elementsOnlyInRightList.get(rightList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Arrays.asList(3)), elementsOnlyInRightList.get(rightList.get(3)));

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair((int)leftList.get(1), (int)rightList.get(1)), differentElements.get(1));

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_elementsOnlyInRightList_differentFrequencies() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST,
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);
        

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(3, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(2)), elementsOnlyInRightList.get(rightList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(3)), elementsOnlyInRightList.get(rightList.get(3)));

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(5, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        
        assertEquals(0, listDifference.getDifferentPositions().size());
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_elementsOnlyInRightList_similarPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST,
                                ListComparator.ComparisonFeature.SIMILAR_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        final ImmutableIntList immutableIntList = similarPositions.get(leftList.get(0));
        assertEquals(1, immutableIntList.size());
        assertEquals(0, immutableIntList.getInt(0));


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(2, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(2)), elementsOnlyInRightList.get(rightList.get(2)));

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_elementsOnlyInRightList_differentPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST,
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(2, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(2)), elementsOnlyInRightList.get(rightList.get(2)));

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(5, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(3)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(3)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_elementsOnlyInRightList_compareElementsDeep() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc");
        final List<Object> rightList = Arrays.asList(3, 7, "lm", 1);

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.ELEMENTS_ONLY_IN_RIGHT_LIST,
                                ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);
        

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        final Map<Object, ImmutableIntList> elementsOnlyInRightList = listDifference.getElementsOnlyInRightList();
        assertEquals(3, elementsOnlyInRightList.size());
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(1)), elementsOnlyInRightList.get(rightList.get(1)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(2)), elementsOnlyInRightList.get(rightList.get(2)));
        assertEquals(ImmutableIntListFactory.create(Collections.singletonList(3)), elementsOnlyInRightList.get(rightList.get(3)));
        
        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertNotNull(comparisonResults.get(2));
    }
    
    @Test
    public void test_twoComparisonFeatures_similarElements_similarFrequencies() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_ELEMENTS,
                                ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));

        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        

        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_similarElements_differentElements() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_ELEMENTS,
                                ListComparator.ComparisonFeature.DIFFERENT_ELEMENTS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        

        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair((int)leftList.get(1), (int)rightList.get(1)), differentElements.get(1));

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_similarElements_differentFrequencies() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_ELEMENTS,
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        

        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(5, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(3)));

        assertEquals(0, listDifference.getDifferentPositions().size());
        
        
        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_similarElements_similarPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_ELEMENTS,
                                ListComparator.ComparisonFeature.SIMILAR_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        final ImmutableIntList immutableIntList = similarPositions.get(leftList.get(0));
        assertEquals(1, immutableIntList.size());
        assertEquals(0, immutableIntList.getInt(0));


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_similarElements_differentPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_ELEMENTS,
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());
        
        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(5, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(3)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(3)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_similarElements_compareElementsDeep() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_ELEMENTS,
                                ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        final Map<Integer, ValueSimilarity<Object>> similarElements = duplicatesListSimilarities.getSimilarElements();
        assertEquals(1, similarElements.size());
        assertEquals(leftList.get(0), similarElements.get(0).getSimilarValue());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        

        assertFalse(comparisonResult.hasDifferences());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertNotNull(comparisonResults.get(2));
    }

    @Test
    public void test_twoComparisonFeatures_similarFrequencies_differentElements() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES,
                                ListComparator.ComparisonFeature.DIFFERENT_ELEMENTS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair((int)leftList.get(1), (int)rightList.get(1)), differentElements.get(1));

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_similarFrequencies_differentFrequencies() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES,
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));

        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        

        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(5, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(3)));

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_similarFrequencies_similarPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES,
                                ListComparator.ComparisonFeature.SIMILAR_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));

        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        final ImmutableIntList immutableIntList = similarPositions.get(leftList.get(0));
        assertEquals(1, immutableIntList.size());
        assertEquals(0, immutableIntList.getInt(0));


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_similarFrequencies_differentPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES,
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));

        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(5, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(3)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(3)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_similarFrequencies_compareElementsDeep() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_FREQUENCIES,
                                ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        final Map<Object, Integer> similarFrequencies = duplicatesListSimilarities.getSimilarFrequencies();
        assertEquals(1, similarFrequencies.size());
        assertEquals(new Integer(1), similarFrequencies.get(leftList.get(0)));

        assertEquals(0, duplicatesListSimilarities.getSimilarPositions().size());
        

        assertFalse(comparisonResult.hasDifferences());

        
        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertNotNull(comparisonResults.get(2));
    }

    @Test
    public void test_twoComparisonFeatures_differentElements_differentFrequencies() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_ELEMENTS,
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair((int)leftList.get(1), (int)rightList.get(1)), differentElements.get(1));

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(5, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(3)));

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_differentElements_similarPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_ELEMENTS,
                                ListComparator.ComparisonFeature.SIMILAR_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());
        
        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());
        
        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        final ImmutableIntList immutableIntList = similarPositions.get(leftList.get(0));
        assertEquals(1, immutableIntList.size());
        assertEquals(0, immutableIntList.getInt(0));


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair((int)leftList.get(1), (int)rightList.get(1)), differentElements.get(1));

        assertEquals(0, listDifference.getDifferentFrequencies().size());
        
        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_differentElements_differentPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_ELEMENTS,
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());

        
        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair((int)leftList.get(1), (int)rightList.get(1)), differentElements.get(1));

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(5, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(3)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(3)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_differentElements_compareElementsDeep() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_ELEMENTS,
                                ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        final Map<Integer, Pair<Object>> differentElements = listDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        assertEquals(new ImmutableIntPair((int)leftList.get(1), (int)rightList.get(1)), differentElements.get(1));

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        assertEquals(0, listDifference.getDifferentPositions().size());
        

        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertNotNull(comparisonResults.get(2));
    }
    
    @Test
    public void test_twoComparisonFeatures_differentFrequencies_similarPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES,
                                ListComparator.ComparisonFeature.SIMILAR_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        final ImmutableIntList immutableIntList = similarPositions.get(leftList.get(0));
        assertEquals(1, immutableIntList.size());
        assertEquals(0, immutableIntList.getInt(0));


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());
        
        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(5, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(3)));

        assertEquals(0, listDifference.getDifferentPositions().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_differentFrequencies_differentPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES,
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());
        
        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(5, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(3)));

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(5, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(3)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(3)));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeatures_differentFrequencies_compareElementsDeep() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_FREQUENCIES,
                                ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        final Map<Object, Pair<Integer>> differentFrequencies = listDifference.getDifferentFrequencies();
        assertEquals(5, differentFrequencies.size());
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(1)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(1)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(2)));
        assertEquals(new ImmutableIntPair(0, 1), differentFrequencies.get(rightList.get(2)));
        assertEquals(new ImmutableIntPair(1, 0), differentFrequencies.get(leftList.get(3)));

        assertEquals(0, listDifference.getDifferentPositions().size());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertNotNull(comparisonResults.get(2));
    }
    
    @Test
    public void test_twoComparisonFeatures_similarPositions_differentPositions() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_POSITIONS,
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        final ImmutableIntList immutableIntList = similarPositions.get(leftList.get(0));
        assertEquals(1, immutableIntList.size());
        assertEquals(0, immutableIntList.getInt(0));


        assertTrue(comparisonResult.hasDifferences());
        final ListDifference<Object> listDifference = comparisonResult.getDifferences();

        assertEquals(0, listDifference.getElementsOnlyInLeftList().size());

        assertEquals(0, listDifference.getElementsOnlyInRightList().size());

        assertEquals(0, listDifference.getDifferentElements().size());

        assertEquals(0, listDifference.getDifferentFrequencies().size());

        final Map<Object, Pair<ImmutableIntList>> differentPositions = listDifference.getDifferentPositions();
        assertEquals(5, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(3)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(3)));


        assertFalse(comparisonResult.hasComparisonResults());
    }
    
    @Test
    public void test_twoComparisonFeatures_similarPositions_compareElementsDeep() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.SIMILAR_POSITIONS,
                                ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP
                        ))
                        .build();

        final ListComparisonResult<Object> comparisonResult = defaultListComparator.compare(leftList, rightList);


        assertTrue(comparisonResult.hasSimilarities());
        final ListSimilarity<Object> duplicatesListSimilarities = comparisonResult.getSimilarities();

        assertEquals(0, duplicatesListSimilarities.getSimilarElements().size());

        assertEquals(0, duplicatesListSimilarities.getSimilarFrequencies().size());

        final Map<Object, ImmutableIntList> similarPositions = duplicatesListSimilarities.getSimilarPositions();
        assertEquals(1, similarPositions.size());
        final ImmutableIntList immutableIntList = similarPositions.get(leftList.get(0));
        assertEquals(1, immutableIntList.size());
        assertEquals(0, immutableIntList.getInt(0));


        assertFalse(comparisonResult.hasDifferences());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertNotNull(comparisonResults.get(2));
    }
    @Test
    public void test_twoComparisonFeatures_differentPositions_compareElementsDeep() throws ComparisonFailedException {
        final List<Object> leftList = Arrays.asList(3, 5, "abc", 1);
        final List<Object> rightList = Arrays.asList(3, 7, "lm");

        final DefaultListComparator<Object> defaultListComparator =
                DefaultComparators
                        .createDefaultListComparatorBuilder()
                        .useComparator(comparator)
                        .useComparisonFeatures(EnumSet.of(
                                ListComparator.ComparisonFeature.DIFFERENT_POSITIONS,
                                ListComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP
                        ))
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
        assertEquals(5, differentPositions.size());
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(1)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(1))),
                differentPositions.get(rightList.get(1)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(2)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.emptyList()),
                        ImmutableIntListFactory.create(Collections.singletonList(2))),
                differentPositions.get(rightList.get(2)));
        assertEquals(new ImmutablePair<>(
                        ImmutableIntListFactory.create(Collections.singletonList(3)),
                        ImmutableIntListFactory.create(Collections.emptyList())),
                differentPositions.get(leftList.get(3)));


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<Integer, ComparisonResult<?, ?, ?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());
        assertNotNull(comparisonResults.get(2));
    }

}
