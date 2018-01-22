package net.mirwaldt.jcomparison.core.array.impl;

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
import org.junit.jupiter.api.Test;

import java.util.EnumSet;
import java.util.Map;
import java.util.Objects;

import static net.mirwaldt.jcomparison.core.facade.ItemComparators.mutableEqualsComparator;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 09.07.2017.
 */
public class ElementsFilterArrayTest {
    private final Object[][] leftArray = new Object[][]{{1, 2}, {"a"}, {}, null};
    private final Object[][] rightArray = new Object[][]{{1, 3, 4}, {"b"}};

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> elementsComparator = new ItemComparator<Object, ComparisonResult<?,?,?>>() {
        private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> substringComparator = DefaultComparators.createSafeDefaultLongestSubstringComparator(false);

        @Override
        public ComparisonResult<?,?,?> compare(Object leftItem, Object rightItem, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
            if (leftItem instanceof String) {
                return substringComparator.compare(leftItem, rightItem, visitedObjectsTrace);
            } else {
                return mutableEqualsComparator().compare(leftItem, rightItem, visitedObjectsTrace);
            }
        }
    };

    @Test
    public void test_filterAllInts() throws ComparisonFailedException {
        final EnumSet<ArrayComparator.ComparisonFeature> comparisonFeatures = EnumSet.of(ArrayComparator.ComparisonFeature.ADDITIONAL_ELEMENTS_IN_THE_LEFT_ARRAY, ArrayComparator.ComparisonFeature.COMPARISON_RESULTS);
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        filterElements((element) -> !(element instanceof Integer))
                        .useElementsComparator(elementsComparator)
                        .comparisonFeatures(comparisonFeatures).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertFalse(comparisonResult.hasSimilarity());


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(2, additionalItemsOnlyInLeftArray.size());

        final ImmutableIntList firstArrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftArray[2], additionalItemsOnlyInLeftArray.get(firstArrayPositionOfAdditionalElementOnlyInLeftArray));

        final ImmutableIntList secondArrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(3);
        assertEquals(leftArray[3], additionalItemsOnlyInLeftArray.get(secondArrayPositionOfAdditionalElementOnlyInLeftArray));

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


        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition));


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(2, additionalItemsOnlyInLeftArray.size());

        final ImmutableIntList firstArrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(leftArray[2], additionalItemsOnlyInLeftArray.get(firstArrayPositionOfAdditionalElementOnlyInLeftArray));

        final ImmutableIntList secondArrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(3);
        assertEquals(leftArray[3], additionalItemsOnlyInLeftArray.get(secondArrayPositionOfAdditionalElementOnlyInLeftArray));

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
    public void test_filterNull() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        filterElements(Objects::nonNull).
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


        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition));


        assertTrue(comparisonResult.hasDifference());
        final ArrayDifference arrayDifference = comparisonResult.getDifference();

        final Map<ImmutableIntList, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final ImmutableIntList arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(3);
        assertEquals(leftArray[3], additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        final Map<ImmutableIntList, ?> differentValues = arrayDifference.getDifferentValues();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPositionOfDifferentElement));

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0,2);
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
