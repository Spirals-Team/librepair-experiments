package net.mirwaldt.jcomparison.core.decorator.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import net.mirwaldt.jcomparison.core.decorator.checking.ExactTypeNiceCheckingComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.ItemComparators;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

public class ExactTypeNiceCheckingComparatorTest {

	@Test
	public void test_leftInvalid() throws ComparisonFailedException {
		ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final ExactTypeNiceCheckingComparator<Object> comparator = new ExactTypeNiceCheckingComparator<>(delegate, ItemComparators.EQUALS_COMPARATOR);

		final Object leftValue = "aString";
		final Object rightValue = 1;

		final ComparisonResult<?,?,?> comparisonResult = comparator.compare(leftValue, rightValue);
		assertNotNull(comparisonResult);
		assertTrue(comparisonResult instanceof ValueComparisonResult, "comparison result is not of type '" + ValueComparisonResult.class.getName() + "' but is of type '"
				+ comparisonResult.getClass().getName() + "'.");
		final ValueComparisonResult<?> valueComparisonResult = (ValueComparisonResult<?>)  comparisonResult;
		
		assertFalse(valueComparisonResult.hasSimilarity());
		assertTrue(valueComparisonResult.hasDifference());
		final Pair<?> pair = valueComparisonResult.getDifference();
		assertEquals(leftValue, pair.getLeft());
		assertEquals(rightValue, pair.getRight());
		
		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void test_rightInvalid() throws ComparisonFailedException {
		ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final ExactTypeNiceCheckingComparator<Object> comparator = new ExactTypeNiceCheckingComparator<>(delegate, ItemComparators.EQUALS_COMPARATOR);

		final Object leftValue = 1;
		final Object rightValue = "aString";
		
		final ComparisonResult<?,?,?> comparisonResult = comparator.compare(leftValue, rightValue);
		assertNotNull(comparisonResult);
		assertTrue(comparisonResult instanceof ValueComparisonResult, "comparison result is not of type '" + ValueComparisonResult.class.getName() + "' but is of type '"
				+ comparisonResult.getClass().getName() + "'.");
		final ValueComparisonResult<?> valueComparisonResult = (ValueComparisonResult<?>)  comparisonResult;
		
		assertFalse(valueComparisonResult.hasSimilarity());
		assertTrue(valueComparisonResult.hasDifference());
		final Pair<?> pair = valueComparisonResult.getDifference();
		assertEquals(leftValue, pair.getLeft());
		assertEquals(rightValue, pair.getRight());

		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void test_bothValid() throws ComparisonFailedException {
		ItemComparator<Object, ComparisonResult<?, ?, ?>> delegate = mock(ItemComparator.class);
		final ExactTypeNiceCheckingComparator<Object> comparator = new ExactTypeNiceCheckingComparator<>(delegate, ItemComparators.EQUALS_COMPARATOR);

		final Object leftValue = "aString";
		final Object rightValue = "anotherString";
		comparator.compare(leftValue, rightValue);

		verify(delegate).compare(eq(leftValue), eq(rightValue), any(VisitedObjectsTrace.class));
		verifyNoMoreInteractions(delegate);
	}
}
