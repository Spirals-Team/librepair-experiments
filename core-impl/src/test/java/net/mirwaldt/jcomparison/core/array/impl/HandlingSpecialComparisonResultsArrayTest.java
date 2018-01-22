package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.facade.ComparisonResults;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static net.mirwaldt.jcomparison.core.facade.ItemComparators.*;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 10.08.2017.
 */
public class HandlingSpecialComparisonResultsArrayTest {
    private final Object[][] leftArray = new Object[][]{{1, 2}, {"a"}, {}};
    private final Object[][] rightArray = new Object[][]{{1, 3, 4}, {"b"}};

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> stoppingAtStringsComparator = new ItemComparator<Object, ComparisonResult<?,?,?>>() {
        @Override
        public ComparisonResult<?,?,?> compare(Object leftItem, Object rightItem, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
            if (leftItem instanceof String) {
                return STOPPING_COMPARATOR.compare(leftItem, rightItem);
            } else {
                return mutableEqualsComparator().compare(leftItem, rightItem);
            }
        }
    };

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> skippingStringsComparator = new ItemComparator<Object, ComparisonResult<?,?,?>>() {
        @Override
        public ComparisonResult<?,?,?> compare(Object leftItem, Object rightItem, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
            if (leftItem instanceof String) {
                return SKIPPING_COMPARATOR.compare(leftItem, rightItem);
            } else {
                return mutableEqualsComparator().compare(leftItem, rightItem);
            }
        }
    };

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> emptyResultForStringsComparator = new ItemComparator<Object, ComparisonResult<?,?,?>>() {
        @Override
        public ComparisonResult<?,?,?> compare(Object leftItem, Object rightItem, VisitedObjectsTrace visitedObjectsTrace) throws ComparisonFailedException {
            if (leftItem instanceof String) {
                return EMPTY_COMPARATOR.compare(leftItem, rightItem);
            } else {
                return mutableEqualsComparator().compare(leftItem, rightItem);
            }
        }
    };

    @Test
    public void test_stoppingAtStrings() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(stoppingAtStringsComparator).build();

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

        final Map<ImmutableIntList, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_skippingStrings() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(skippingStringsComparator).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarity());

        final Map<ImmutableIntList, ?> similarElements = comparisonResult.getSimilarity();
        assertEquals(1, similarElements.size());

        final ImmutableIntList arrayPosition =  new ImmutableTwoElementsImmutableIntList(0, 0);
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
        final ImmutableIntList arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0,2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_emptyResultForStrings() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(emptyResultForStringsComparator).build();

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

        assertEquals(ComparisonResults.emptyComparisonResult(), comparisonResults.get(arrayPositionOfComparisonResult));
    }
}

