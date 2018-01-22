package net.mirwaldt.jcomparison.core.decorator.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.decorator.checking.InstanceOfSameTypeStrictCheckingComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class InstanceOfSameTypeStrictCheckingComparatorTest {

	@Test
	public void test_leftInvalid() throws ComparisonFailedException {
		final ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final InstanceOfSameTypeStrictCheckingComparator<Object> comparator = new InstanceOfSameTypeStrictCheckingComparator<>(
				Number.class, delegate);

		final Object leftValue = "aString";
		final Object rightValue = 1;
		assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftValue, rightValue));
	}

	@Test
	public void test_rightInvalid() throws ComparisonFailedException {
		final ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final InstanceOfSameTypeStrictCheckingComparator<Object> comparator = new InstanceOfSameTypeStrictCheckingComparator<>(
				Number.class, delegate);

		final Object leftValue = 1;
		final Object rightValue = "aString";
		assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftValue, rightValue));
	}

	@Test
	public void test_bothInvalid() throws ComparisonFailedException {
		final ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final InstanceOfSameTypeStrictCheckingComparator<Object> comparator = new InstanceOfSameTypeStrictCheckingComparator<>(
				Number.class, delegate);

		final Object leftValue = "aString";
		final Object rightValue = "anotherString";
		assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftValue, rightValue));
	}

	@Test
	public void test_bothIntegers() throws ComparisonFailedException {
		final ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final InstanceOfSameTypeStrictCheckingComparator<Object> comparator = new InstanceOfSameTypeStrictCheckingComparator<>(
				Number.class, delegate);

		final Integer leftInteger = 1;
		final Integer rightInteger = 2;
		comparator.compare(leftInteger, rightInteger);

		verify(delegate).compare(eq(leftInteger), eq(rightInteger), any(VisitedObjectsTrace.class));
		verifyNoMoreInteractions(delegate);
	}
	
	@Test
	public void test_bothNumbers() throws ComparisonFailedException {
		final ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final InstanceOfSameTypeStrictCheckingComparator<Object> comparator = new InstanceOfSameTypeStrictCheckingComparator<>(
				Number.class, delegate);

		final Double leftValue = 1.0d;
		final Integer rightValue = 2;
		comparator.compare(leftValue, rightValue);

		verify(delegate).compare(eq(leftValue), eq(rightValue), any(VisitedObjectsTrace.class));
		verifyNoMoreInteractions(delegate);
	}
}
