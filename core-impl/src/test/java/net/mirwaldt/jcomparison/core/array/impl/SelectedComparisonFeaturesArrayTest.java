package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Map;

import static net.mirwaldt.jcomparison.core.facade.ItemComparators.mutableEqualsComparator;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 09.07.2017.
 */
public class SelectedComparisonFeaturesArrayTest {
    private final Object[][] leftArray = new Object[][]{{1, 2}, {"a"}, {}};
    private final Object[][] rightArray = new Object[][]{{1, 3, 4}, {"b"}};

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> elementsComparator = new ItemComparator<Object, ComparisonResult<?,?,?>>() {
        private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> substringComparator = DefaultComparators.createSafeDefaultLongestSubstringComparator(false);

        @Override
        public ComparisonResult<?,?,?> compare(Object leftItem, Object rightItem, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
            if (leftItem instanceof String) {
                return substringComparator.compare(leftItem, rightItem);
            } else {
                return mutableEqualsComparator().compare(leftItem, rightItem);
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


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftArray[2], additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

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


        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition));


        assertFalse(comparisonResult.hasDifference());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeatureOnly_additionalElementsInTheRightArray() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeatureOnly_differentValues() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.DIFFERENT_VALUES);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_oneComparisonFeatureOnly_comparisonResults() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.COMPARISON_RESULTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarity());


        assertFalse(comparisonResult.hasDifference());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPosition));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPosition);
        assertFalse(substringComparisonResult.hasSimilarity());
        assertTrue(substringComparisonResult.hasDifference());
    }

    // Two features    

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheLeftArray_similarElements() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition));


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftArray[2], additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

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


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftArray[2], additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0,2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheLeftArray_differentValues() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.DIFFERENT_VALUES);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftArray[2], additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheLeftArray_comparisonResults() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.COMPARISON_RESULTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftArray[2], additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentValues().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarity());
        assertTrue(substringComparisonResult.hasDifference());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_similarElements_additionalElementsInTheRightArray() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS, ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);

        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition));


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_similarElements_differentValues() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS, ArrayComparator.ComparisonFeature.DIFFERENT_VALUES);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition));


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPositionOfDifferentElement));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_similarElements_comparisonResults() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.SIMILAR_ELEMENTS, ArrayComparator.ComparisonFeature.COMPARISON_RESULTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition));


        assertFalse(comparisonResult.hasDifference());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarity());
        assertTrue(substringComparisonResult.hasDifference());

    }

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheRightArray_differentValues() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY, ArrayComparator.ComparisonFeature.DIFFERENT_VALUES);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);

        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPosition));

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_additionalElementsInTheRightArray_comparisonResults() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_RIGHT_ARRAY, ArrayComparator.ComparisonFeature.COMPARISON_RESULTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        assertEquals(0, arrayDifference.getDifferentValues().size());

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarity());
        assertTrue(substringComparisonResult.hasDifference());
    }

    @Test
    public void test_twoComparisonFeaturesOnly_differentValues_comparisonResults() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.DIFFERENT_VALUES, ArrayComparator.ComparisonFeature.COMPARISON_RESULTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPosition));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarity());
        assertTrue(substringComparisonResult.hasDifference());
    }

    @Test
    public void test_allComparisonFeatures() throws ComparisonFailedException {
         final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(elementsComparator).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition));


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftArray[2], additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPositionOfDifferentElement));

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarity());
        assertTrue(substringComparisonResult.hasDifference());
    }
}
