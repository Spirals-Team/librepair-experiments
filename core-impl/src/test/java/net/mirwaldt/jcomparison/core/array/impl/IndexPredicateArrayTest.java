package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.array.api.ArrayComparator;
import net.mirwaldt.jcomparison.core.array.api.ArrayComparisonResult;
import net.mirwaldt.jcomparison.core.array.api.ArrayDifference;
import net.mirwaldt.jcomparison.core.pair.impl.ImmutableIntPair;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.version_001.DefaultComparators;
import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import net.mirwaldt.jcomparison.core.value.api.ValueSimilarity;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertFalse;
import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * Created by Michael on 09.07.2017.
 */
public class IndexPredicateArrayTest {

    @Test
    public void test_skipPosition_oneDimensionalIntArray() throws ComparisonFailedException {
        final int[] leftIntArray = new int[]{1, 2};
        final int[] rightIntArray = new int[]{1, 2};

        final RecordingPredicate skipPredicate = new RecordingPredicate((ints) -> ints[0] < 1);

        final ArrayComparator<int[]> intArrayComparator = DefaultComparators.<int[]>createDefaultArrayComparatorBuilder().skipAtPosition(skipPredicate).build();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftIntArray, rightIntArray);

        final List<int[]> arrayPositions = skipPredicate.getArrayPositions();
        assertEquals(2, arrayPositions.size());
        assertArrayEquals(new int[] {0}, arrayPositions.get(0));
        assertArrayEquals(new int[] {1}, arrayPositions.get(1));


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        assertEquals(leftIntArray[1], similarElements.get(new ImmutableOneElementImmutableIntList(1)).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_skipPosition_twoDimensionalsIntArray() throws ComparisonFailedException {
        final int[][] leftIntArray = new int[][]{{1, 2}, {3, 4}};
        final int[][] rightIntArray = new int[][]{{1, 2}, {5, 6}};

        final RecordingPredicate skipPredicate = new RecordingPredicate((ints) -> {
            if(ints.length == 2) {
                if(ints[0] == 0) {
                    return ints[1] < 1; // skip first
                } else {
                    return 0 < ints[1]; // skip last
                }
            } else { // ints.length == 1
                return false; // do not skip one dimension at all
            }
        });

        final ArrayComparator<int[][]> intArrayComparator = DefaultComparators.<int[][]>createDefaultArrayComparatorBuilder().skipAtPosition(skipPredicate).build();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftIntArray, rightIntArray);

        final List<int[]> arrayPositions = skipPredicate.getArrayPositions();
        assertEquals(6, arrayPositions.size());
        assertArrayEquals(new int[] {0}, arrayPositions.get(0));
        assertArrayEquals(new int[] {0, 0}, arrayPositions.get(1));
        assertArrayEquals(new int[] {0, 1}, arrayPositions.get(2));
        assertArrayEquals(new int[] {1}, arrayPositions.get(3));
        assertArrayEquals(new int[] {1, 0}, arrayPositions.get(4));
        assertArrayEquals(new int[] {1, 1}, arrayPositions.get(5));



        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPositionOfSimilarElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(leftIntArray[0][1], similarElements.get(arrayPositionOfSimilarElement).getSimilarValue());


        assertTrue(comparisonResult.hasDifferences());
        final ArrayDifference arrayDifference = comparisonResult.getDifferences();

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInLeftArray().size());

        final Map<List<Integer>, ?> differentValues = arrayDifference.getDifferentElements();
        assertEquals(1, differentValues.size());
        final ImmutableIntList arrayPositionOfDifferentElements = new ImmutableTwoElementsImmutableIntList(1, 0);
        assertEquals(new ImmutableIntPair(leftIntArray[1][0], rightIntArray[1][0]), differentValues.get(arrayPositionOfDifferentElements));

        assertEquals(0, arrayDifference.getAdditionalItemsOnlyInRightArray().size());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_stopPosition_oneDimensionalIntArray() throws ComparisonFailedException {
        final int[] leftIntArray = new int[]{1, 2};
        final int[] rightIntArray = new int[]{1, 2};

        final RecordingPredicate stopPredicate = new RecordingPredicate((ints) -> 0 < ints[0]);

        final ArrayComparator<int[]> intArrayComparator = DefaultComparators.<int[]>createDefaultArrayComparatorBuilder().stopAtPosition(stopPredicate).build();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftIntArray, rightIntArray);

        final List<int[]> arrayPositions = stopPredicate.getArrayPositions();
        assertEquals(2, arrayPositions.size());
        assertArrayEquals(new int[] {0}, arrayPositions.get(0));
        assertArrayEquals(new int[] {1}, arrayPositions.get(1));


        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        assertEquals(leftIntArray[0], similarElements.get(new ImmutableOneElementImmutableIntList(0)).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_stopPosition_twoDimensionalsIntArray() throws ComparisonFailedException {
        final int[][] leftIntArray = new int[][]{{1, 2}, {3, 4}};
        final int[][] rightIntArray = new int[][]{{1, 2}, {5, 6}};

        final RecordingPredicate stopPredicate = new RecordingPredicate((ints) -> {
            if(ints.length == 2) {
                return ints[0] == 0 && ints[1] == 1;
            } else { // ints.length == 1
                return false; // do not stop
            }
        });

        final ArrayComparator<int[][]> intArrayComparator = DefaultComparators.<int[][]>createDefaultArrayComparatorBuilder().stopAtPosition(stopPredicate).build();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftIntArray, rightIntArray);

        final List<int[]> arrayPositions = stopPredicate.getArrayPositions();
        assertEquals(3, arrayPositions.size());
        assertArrayEquals(new int[] {0}, arrayPositions.get(0));
        assertArrayEquals(new int[] {0, 0}, arrayPositions.get(1));
        assertArrayEquals(new int[] {0, 1}, arrayPositions.get(2));



        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPositionOfSimilarElement = new ImmutableTwoElementsImmutableIntList(0, 0);
        assertEquals(leftIntArray[0][0], similarElements.get(arrayPositionOfSimilarElement).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_skipPositionAndStopPosition_oneDimensionalIntArray() throws ComparisonFailedException {
        final int[] leftIntArray = new int[]{1, 2, 3};
        final int[] rightIntArray = new int[]{1, 2, 4};

        final RecordingPredicate skipPredicate = new RecordingPredicate((ints) -> ints[0] < 1);
        final RecordingPredicate stopPredicate = new RecordingPredicate((ints) -> 1 < ints[0]);

        final ArrayComparator<int[]> intArrayComparator = DefaultComparators.<int[]>createDefaultArrayComparatorBuilder().skipAtPosition(skipPredicate).stopAtPosition(stopPredicate).build();
        final ArrayComparisonResult comparisonResult = intArrayComparator.compare(leftIntArray, rightIntArray);

        final List<int[]> skippedArrayPositions = skipPredicate.getArrayPositions();
        assertEquals(2, skippedArrayPositions.size());
        assertArrayEquals(new int[] {0}, skippedArrayPositions.get(0));
        assertArrayEquals(new int[] {1}, skippedArrayPositions.get(1));

        final List<int[]> arrayPositions = stopPredicate.getArrayPositions();
        assertEquals(3, arrayPositions.size());
        assertArrayEquals(new int[] {0}, arrayPositions.get(0));
        assertArrayEquals(new int[] {1}, arrayPositions.get(1));
        assertArrayEquals(new int[] {2}, arrayPositions.get(2));



        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        assertEquals(leftIntArray[1], similarElements.get(new ImmutableOneElementImmutableIntList(1)).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    @Test
    public void test_skipPositionAndStopPosition_twoDimensionalsIntArray() throws ComparisonFailedException {
        final int[][] leftIntArray = new int[][]{{1, 2}, {3, 4}};
        final int[][] rightIntArray = new int[][]{{1, 2}, {5, 6}};

        final RecordingPredicate skipPredicate = new RecordingPredicate((ints) -> {
            if(ints.length == 2) {
                if(ints[0] == 0) {
                    return ints[1] < 1; // skip first
                } else {
                    return 0 < ints[1]; // skip last
                }
            } else { // ints.length == 1
                return false; // do not skip one dimension at all
            }
        });

        final RecordingPredicate stopPredicate = new RecordingPredicate((ints) -> {
            if(ints.length == 2) {
                return ints[0] == 1 && ints[1] == 0;
            } else { // ints.length == 1
                return false; // do not stop
            }
        });

        final ArrayComparator<int[][]> intArrayComparator = DefaultComparators.<int[][]>createDefaultArrayComparatorBuilder().skipAtPosition(skipPredicate).stopAtPosition(stopPredicate).build();
        final ArrayComparisonResult comparisonResult = (ArrayComparisonResult) intArrayComparator.compare(leftIntArray, rightIntArray);

        final List<int[]> skippedArrayPositions = skipPredicate.getArrayPositions();
        assertEquals(4, skippedArrayPositions.size());
        assertArrayEquals(new int[] {0}, skippedArrayPositions.get(0));
        assertArrayEquals(new int[] {0, 0}, skippedArrayPositions.get(1));
        assertArrayEquals(new int[] {0, 1}, skippedArrayPositions.get(2));
        assertArrayEquals(new int[] {1}, skippedArrayPositions.get(3));

        final List<int[]> arrayPositions = stopPredicate.getArrayPositions();
        assertEquals(5, arrayPositions.size());
        assertArrayEquals(new int[] {0}, arrayPositions.get(0));
        assertArrayEquals(new int[] {0, 0}, arrayPositions.get(1));
        assertArrayEquals(new int[] {0, 1}, arrayPositions.get(2));
        assertArrayEquals(new int[] {1}, arrayPositions.get(3));
        assertArrayEquals(new int[] {1, 0}, arrayPositions.get(4));



        assertTrue(comparisonResult.hasSimilarities());

        final Map<List<Integer>, ValueSimilarity<Object>> similarElements = comparisonResult.getSimilarities();
        assertEquals(1, similarElements.size());
        final ImmutableIntList arrayPositionOfSimilarElement = new ImmutableTwoElementsImmutableIntList(0, 1);
        assertEquals(leftIntArray[0][1], similarElements.get(arrayPositionOfSimilarElement).getSimilarValue());


        assertFalse(comparisonResult.hasDifferences());


        assertFalse(comparisonResult.hasComparisonResults());
    }

    class RecordingPredicate implements Predicate<int[]> {
        private final List<int[]> arrayPositions = new ArrayList<>();
        private final Predicate<int[]> delegate;

        public RecordingPredicate(Predicate<int[]> delegate) {
            this.delegate = delegate;
        }

        @Override
        public boolean test(int[] arrayPosition) {
            arrayPositions.add(Arrays.copyOf(arrayPosition, arrayPosition.length));
            return delegate.test(arrayPosition);
        }

        public List<int[]> getArrayPositions() {
            return arrayPositions;
        }
    }
}
