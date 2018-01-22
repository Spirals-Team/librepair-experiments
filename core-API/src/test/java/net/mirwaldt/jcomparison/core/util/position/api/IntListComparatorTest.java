package net.mirwaldt.jcomparison.core.util.position.api;

import net.mirwaldt.jcomparison.core.util.position.api.ImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableOneElementImmutableIntList;
import net.mirwaldt.jcomparison.core.util.position.impl.ImmutableTwoElementsImmutableIntList;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * Created by Michael on 06.07.2017.
 */
public class IntListComparatorTest {

    private final ImmutableIntList.IntListComparator multiDimensionalArrayPositionComparator = new ImmutableIntList.IntListComparator();

    @Test
    public void test_bothNull() {
        assertEquals(0, multiDimensionalArrayPositionComparator.compare(null, null));
    }

    @Test
    public void test_leftNull() {
        final ImmutableIntList rightArrayPosition = new ImmutableOneElementImmutableIntList(0);

        assertEquals(-1, multiDimensionalArrayPositionComparator.compare(null, rightArrayPosition));
    }

    @Test
    public void test_rightNull() {
        final ImmutableIntList leftArrayPosition = new ImmutableOneElementImmutableIntList(0);

        assertEquals(1, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, null));
    }

    @Test
    public void test_bothEmpty() {
        final ImmutableIntList leftArrayPosition = new ImmutableOneElementImmutableIntList(0);
        final ImmutableIntList rightArrayPosition = new ImmutableOneElementImmutableIntList(0);

        assertEquals(0, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_leftArraySmaller() {
        final ImmutableIntList leftArrayPosition = new ImmutableOneElementImmutableIntList(0);
        final ImmutableIntList rightArrayPosition = new ImmutableOneElementImmutableIntList(1);

        assertEquals(-1, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_rightArraySmaller() {
        final ImmutableIntList leftArrayPosition = new ImmutableOneElementImmutableIntList(1);
        final ImmutableIntList rightArrayPosition = new ImmutableOneElementImmutableIntList(0);

        assertEquals(+1, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_oneDimension_lowerLeftIndex() {
        final ImmutableIntList leftArrayPosition = new ImmutableOneElementImmutableIntList(1);
        final ImmutableIntList rightArrayPosition = new ImmutableOneElementImmutableIntList(2);

        assertEquals(-1, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_oneDimension_lowerRightIndex() {
        final ImmutableIntList leftArrayPosition = new ImmutableOneElementImmutableIntList(2);
        final ImmutableIntList rightArrayPosition = new ImmutableOneElementImmutableIntList(1);

        assertEquals(+1, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_oneDimension_sameIndex() {
        final ImmutableIntList leftArrayPosition = new ImmutableOneElementImmutableIntList(1);
        final ImmutableIntList rightArrayPosition = new ImmutableOneElementImmutableIntList(1);

        assertEquals(0, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_twoDimensions_lowerLeftSecondIndex() {
        final ImmutableIntList leftArrayPosition = new ImmutableTwoElementsImmutableIntList(1, 2);
        final ImmutableIntList rightArrayPosition = new ImmutableTwoElementsImmutableIntList(1, 3);

        assertEquals(-1, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_twoDimensions_lowerRightSecondIndex() {
        final ImmutableIntList leftArrayPosition = new ImmutableTwoElementsImmutableIntList(1, 3);
        final ImmutableIntList rightArrayPosition = new ImmutableTwoElementsImmutableIntList(1, 2);

        assertEquals(+1, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_twoDimensions_sameIndex() {
        final ImmutableIntList leftArrayPosition = new ImmutableTwoElementsImmutableIntList(1, 2);
        final ImmutableIntList rightArrayPosition = new ImmutableTwoElementsImmutableIntList(1, 2);

        assertEquals(0, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_twoDimensions_lowerLeftFirstIndex_lowerRightSecondIndex() {
        final ImmutableIntList leftArrayPosition = new ImmutableTwoElementsImmutableIntList(1, 4);
        final ImmutableIntList rightArrayPosition = new ImmutableTwoElementsImmutableIntList(2, 3);

        assertEquals(-1, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }

    @Test
    public void test_twoDimensions_lowerRightFirstIndex_lowerLeftSecondIndex() {
        final ImmutableIntList leftArrayPosition = new ImmutableTwoElementsImmutableIntList(2, 4);
        final ImmutableIntList rightArrayPosition = new ImmutableTwoElementsImmutableIntList(1, 3);

        assertEquals(+1, multiDimensionalArrayPositionComparator.compare(leftArrayPosition, rightArrayPosition));
    }
}
