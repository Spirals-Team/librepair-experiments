package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.string.api.SubstringComparisonResult;
import net.mirwaldt.jcomparison.core.util.ArrayAccessor;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 09.07.2017.
 */
public class ElementsFilterArrayTest {
    private final Object[][] leftArray = new Object[][]{{1, 2}, {"a"}, {}, null};
    private final Object[][] rightArray = new Object[][]{{1, 3, 4}, {"b"}};

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> elementsComparator = new ItemComparator<Object, ComparisonResult<?,?,?>>() {
        private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> substringComparator = DefaultComparators.createSafeDefaultSubstringComparator(false);

        @Override
        public ComparisonResult<?,?,?> compare(Object leftItem, 
                                               Object rightItem, 
                                               VisitedObjectsTrace visitedObjectsTrace,
                                               ComparatorOptions comparatorOptions) throws ComparisonFailedException {
            if (leftItem instanceof String) {
                return substringComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
            } else {
                return ValueComparators.immutableResultEqualsComparator().compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
            }
        }
    };

    @Test
    public void test_filterAllInts() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.COMPARE_ELEMENTS_DEEP);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        filterElements((element) -> !(element instanceof Integer))
                        .useElementsComparator(elementsComparator)
                        .comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarities());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(2, additionalItemsOnlyInLeftArray.size());

        final ImmutableIntList firstArrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(firstArrayPositionOfAdditionalElementOnlyInLeftArray));

        final ImmutableIntList secondArrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(3);
        assertEquals(leftArray[3], additionalItemsOnlyInLeftArray.get(secondArrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());
    }

    @Test
    public void test_filterEvenInts() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        filterElements((element)->{
                            if(element instanceof Integer) {
                                return (Integer) element % 2 != 0;
                            } else {
                                return true;
                            }
                        }).
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
        assertEquals(2, additionalItemsOnlyInLeftArray.size());

        final ImmutableIntList firstArrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(firstArrayPositionOfAdditionalElementOnlyInLeftArray));

        final ImmutableIntList secondArrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(3);
        assertEquals(leftArray[3], additionalItemsOnlyInLeftArray.get(secondArrayPositionOfAdditionalElementOnlyInLeftArray));

        assertEquals(0, arrayDifference.getDifferentElements().size());

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());
    }


    @Test
    public void test_filterNull() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        filterElements(Objects::nonNull).
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

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPositionOfDifferentElement));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());
    }

    @Test
    public void test_filterEmptyArrays() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        filterElements((element)->{
                            if(element!= null && element.getClass().isArray()) {
                                return 0 < ArrayAccessor.getLength(element);
                            } else {
                                return true;
                            }
                        }).
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
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(3);
        assertEquals(leftArray[3], additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPositionOfDifferentElement));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0,2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final ImmutableIntList arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        SubstringComparisonResult substringComparisonResult = (SubstringComparisonResult) comparisonResults.get(arrayPositionOfComparisonResult);
        assertFalse(substringComparisonResult.hasSimilarities());
        assertTrue(substringComparisonResult.hasDifferences());
    }
}
