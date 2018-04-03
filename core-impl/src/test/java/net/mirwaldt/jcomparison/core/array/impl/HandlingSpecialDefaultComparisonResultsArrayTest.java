package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Created by Michael on 10.08.2017.
 */
public class HandlingSpecialDefaultComparisonResultsArrayTest {
    private final Object[][] leftArray = new Object[][]{{1, 2}, {"a"}, {}};
    private final Object[][] rightArray = new Object[][]{{1, 3, 4}, {"b"}};

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> stoppingAtStringsComparator = (ItemComparator<Object, ComparisonResult<?, ?, ?>>) (leftItem, rightItem, visitedObjectsTrace, comparatorOptions) -> {
        if (leftItem instanceof String) {
            return BasicComparisonResults.stopComparisonResult();
        } else {
            return ValueComparators.immutableResultEqualsComparator().compare(leftItem, rightItem);
        }
    };

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> skippingStringsComparator = (ItemComparator<Object, ComparisonResult<?, ?, ?>>) (leftItem, rightItem, visitedObjectsTrace, comparatorOptions) -> {
        if (leftItem instanceof String) {
            return BasicComparisonResults.skipComparisonResult();
        } else {
            return ValueComparators.immutableResultEqualsComparator().compare(leftItem, rightItem);
        }
    };

    private final ItemComparator<Object, ? extends ComparisonResult<?,?,?>> emptyResultForStringsComparator = (ItemComparator<Object, ComparisonResult<?, ?, ?>>) (leftItem, rightItem, visitedObjectsTrace, comparatorOptions) -> {
        if (leftItem instanceof String) {
            return BasicComparisonResults.emptyNonValueComparisonResult();
        } else {
            return ValueComparators.immutableResultEqualsComparator().compare(leftItem, rightItem);
        }
    };

    @Test
    public void test_stoppingAtStrings() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(stoppingAtStringsComparator).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final List<Integer> arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final List<Integer> arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPositionOfDifferentElement));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final List<Integer> arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_skippingStrings() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(skippingStringsComparator).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final List<Integer> arrayPosition =  new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final List<Integer> arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final List<Integer> arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPositionOfDifferentElement));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final List<Integer> arrayPositionOfadditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0,2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfadditionalElementOnlyInRightArray));


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_emptyResultForStrings() throws ComparisonFailedException {
        final ArrayComparator<Object[][]> arrayComparator =
                DefaultComparators.<Object[][]>createDefaultArrayComparatorBuilder().
                        useElementsComparator(emptyResultForStringsComparator).build();

        final ArrayComparisonResult comparisonResult = arrayComparator.compare(leftArray, rightArray);


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());

        final List<Integer> arrayPosition = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftArray[0][0], similarElements.get(arrayPosition).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        final Map<List<Integer>, ?> additionalItemsOnlyInLeftArray = arrayDifference.getAdditionalItemsOnlyInLeftArray();
        assertEquals(1, additionalItemsOnlyInLeftArray.size());
        final List<Integer> arrayPositionOfAdditionalElementOnlyInLeftArray = new ImmutableOneElementImmutableIntList(2);
        assertEquals(new DefaultArrayWrapper(leftArray[2]), additionalItemsOnlyInLeftArray.get(arrayPositionOfAdditionalElementOnlyInLeftArray));

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final List<Integer> arrayPositionOfDifferentElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(new ImmutableIntPair((int)leftArray[0][1],(int)rightArray[0][1]), differentValues.get(arrayPositionOfDifferentElement));

        final Map<List<Integer>, ?> additionalItemsOnlyInRightArray = arrayDifference.getAdditionalItemsOnlyInRightArray();
        assertEquals(1, additionalItemsOnlyInRightArray.size());
        final List<Integer> arrayPositionOfAdditionalElementOnlyInRightArray = new ImmutableTwoElementsImmutableIntList(0, 2);
        assertEquals(rightArray[0][2], additionalItemsOnlyInRightArray.get(arrayPositionOfAdditionalElementOnlyInRightArray));


        assertTrue(comparisonResult.hasComparisonResults());
        final Map<?, ComparisonResult<?,?,?>> comparisonResults = comparisonResult.getComparisonResults();
        assertEquals(1, comparisonResults.size());

        final List<Integer> arrayPositionOfComparisonResult = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertTrue(comparisonResults.containsKey(arrayPositionOfComparisonResult));

        assertEquals(BasicComparisonResults.emptyNonValueComparisonResult(), comparisonResults.get(arrayPositionOfComparisonResult));
    }
}

