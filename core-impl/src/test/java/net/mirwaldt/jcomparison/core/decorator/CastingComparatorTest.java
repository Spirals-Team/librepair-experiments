package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class CastingComparatorTest {

    @Test
    public void test_leftInvalid() throws ComparisonFailedException {
        ItemComparator<Number, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
        final CastingComparator<Number> comparator = new CastingComparator<>(delegate, Number.class);

        final Object leftValue = "aString";
        final Object rightValue = 1;
        assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftValue, rightValue));
    }

    @Test
    public void test_rightInvalid() throws ComparisonFailedException {
        ItemComparator<Number, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
        final CastingComparator<Number> comparator = new CastingComparator<>(delegate, Number.class);

        final Object leftValue = 1;
        final Object rightValue = "aString";
        assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftValue, rightValue));
    }

    @Test
    public void test_bothInvalid() throws ComparisonFailedException {
        ItemComparator<Number, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
        final CastingComparator<Number> comparator = new CastingComparator<>(delegate, Number.class);

        final Object leftValue = "aString";
        final Object rightValue = "anotherString";
        assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftValue, rightValue));
    }

    @Test
    public void test_bothIntegers() throws ComparisonFailedException {
        ItemComparator<Number, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
        final CastingComparator<Number> comparator = new CastingComparator<>(delegate, Number.class);

        final Integer leftInteger = 1;
        final Integer rightInteger = 2;
        comparator.compare(leftInteger, rightInteger);

        verify(delegate).compare(eq(leftInteger), eq(rightInteger), any(VisitedObjectsTrace.class), any(ComparatorOptions.class));
        verifyNoMoreInteractions(delegate);
    }

    @Test
    public void test_bothNumbers() throws ComparisonFailedException {
        ItemComparator<Number, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
        final CastingComparator<Number> comparator = new CastingComparator<>(delegate, Number.class);

        final Double leftValue = 1.0d;
        final Integer rightValue = 2;
        comparator.compare(leftValue, rightValue);

        verify(delegate).compare(eq(leftValue), eq(rightValue), any(VisitedObjectsTrace.class), any(ComparatorOptions.class));
        verifyNoMoreInteractions(delegate);
    }
}
