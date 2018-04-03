package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.decorator.checking.SameTypeStrictCheckingComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class SameTypeStrictCheckingComparatorTest {

	@Test
	public void test_leftInvalid() throws ComparisonFailedException {
		ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final SameTypeStrictCheckingComparator<Object> comparator = new SameTypeStrictCheckingComparator<>(delegate);

		final Object leftValue = "aString";
		final Object rightValue = 1;
		assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftValue, rightValue));
	}

	@Test
	public void test_rightInvalid() throws ComparisonFailedException {
		ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final SameTypeStrictCheckingComparator<Object> comparator = new SameTypeStrictCheckingComparator<>(delegate);

		final Object leftValue = 1;
		final Object rightValue = "aString";
		assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftValue, rightValue));
	}

	@Test
	public void test_bothValid() throws ComparisonFailedException {
		final ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final SameTypeStrictCheckingComparator<Object> comparator = new SameTypeStrictCheckingComparator<>(delegate);

		final Object leftValue = "aString";
		final Object rightValue = "anotherString";
		comparator.compare(leftValue, rightValue);

		verify(delegate).compare(eq(leftValue), eq(rightValue), any(VisitedObjectsTrace.class), any(ComparatorOptions.class));
		verifyNoMoreInteractions(delegate);
	}
}
