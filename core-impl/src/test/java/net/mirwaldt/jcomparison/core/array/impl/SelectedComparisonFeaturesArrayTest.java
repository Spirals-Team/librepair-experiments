package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 09.07.2017.
 */
public class SelectedComparisonFeaturesArrayTest {
    private final Object[][] leftArray = new Object[][]{{1, 2}, {"a"}, {}};
    private final Object[][] rightArray = new Object[][]{{1, 3, 4}, {"b"}};

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> elementsComparator = new ItemComparator<Object, ComparisonResult<?,?,?>>() {
        private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> substringComparator = DefaultComparators.createSafeDefaultSubstringComparator(false);

        @Override
        public ComparisonResult<?,?,?> compare(Object leftItem, 
                                               Object rightItem, 
                                               VisitedObjectsTrace visitedObjectsTrace,
                                               ComparatorOptions comparatorOptions) throws ComparisonFailedException {
            if (leftItem instanceof String) {
                return substringComparator.compare(leftItem, rightItem);
            } else {
                return ValueComparators.immutableResultEqualsComparator().compare(leftItem, rightItem);
            }
        }
    };

    // One feature    

    @Test
    public void test_oneComparisonFeatureOnly_additionalElementsInTheLeftArray() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeatureOnly_similarElements() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeatureOnly_additionalElementsInTheRightArray() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeatureOnly_differentElements() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.DIFFERENT_ELEMENTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentElements = arrayDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentElements.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeatureOnly_compareElementsDeep() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertFalse(comparisonResult.hasDifferences());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> compareElementsDeep = comparisonResult.getComparisonResults();
        assertEquals(1, compareElementsDeep.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(compareElementsDeep.containsKey(arrayPosition));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) compareElementsDeep.get(arrayPosition);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());
    }

    // Two features    

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheLeftArray_similarElements() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheLeftArray_additionalElementsInTheRightArray() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0,2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheLeftArray_differentElements() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.DIFFERENT_ELEMENTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        final Map<List<Integer>, ?> differentElements = arrayDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentElements.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheLeftArray_compareElementsDeep() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> compareElementsDeep = comparisonResult.getComparisonResults();
        assertEquals(1, compareElementsDeep.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(compareElementsDeep.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) compareElementsDeep.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_similarElements_additionalElementsInTheRightArray() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS, ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);

        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_similarElements_differentElements() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS, ArrayComparator.ComparisonFeature.DIFFERENT_ELEMENTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentElements = arrayDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        final ImmutableIntList arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentElements.get(arrayPositionOfDifferentElement));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_similarElements_compareElementsDeep() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS, ArrayComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> compareElementsDeep = comparisonResult.getComparisonResults();
        assertEquals(1, compareElementsDeep.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(compareElementsDeep.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) compareElementsDeep.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());

    }

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheRightArray_differentElements() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY, ArrayComparator.ComparisonFeature.DIFFERENT_ELEMENTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);

        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentElements = arrayDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentElements.get(arrayPosition));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheRightArray_compareElementsDeep() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY, ArrayComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentElements().size());

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> compareElementsDeep = comparisonResult.getComparisonResults();
        assertEquals(1, compareElementsDeep.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(compareElementsDeep.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) compareElementsDeep.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_differentElements_compareElementsDeep() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.DIFFERENT_ELEMENTS, ArrayComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentElements = arrayDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentElements.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> compareElementsDeep = comparisonResult.getComparisonResults();
        assertEquals(1, compareElementsDeep.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(compareElementsDeep.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) compareElementsDeep.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());
    }

    @Test
    public void test_allComparisonFeatures() throws ComparisonFailedException {
         final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        final Map<List<Integer>, ?> differentElements = arrayDifference.getDifferentElements();
        assertEquals(1, differentElements.size());
        final ImmutableIntList arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentElements.get(arrayPositionOfDifferentElement));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> compareElementsDeep = comparisonResult.getComparisonResults();
        assertEquals(1, compareElementsDeep.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(compareElementsDeep.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) compareElementsDeep.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());
    }
}
