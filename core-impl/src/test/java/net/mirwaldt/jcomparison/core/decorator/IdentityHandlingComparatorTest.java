package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;


public class IdentityHandlingComparatorTest {

	@Test
	public void test_identical() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final IdentityHandlingComparator<Integer> identityHandlingComparator = new IdentityHandlingComparator<>(
				delegate);

		final Integer integer = 1234;

		final ValueComparisonResult<?> valueComparisonResult = (ValueComparisonResult<?>) identityHandlingComparator
				.compare(integer, integer);

		assertTrue(valueComparisonResult.hasSimilarities());
		assertSame(integer, valueComparisonResult.getSimilarities().getSimilarValue());
		
		assertFalse(valueComparisonResult.hasDifferences());
		
		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void test_notIdentical() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final IdentityHandlingComparator<Integer> identityHandlingComparator = new IdentityHandlingComparator<>(
				delegate);

		final Integer leftInteger = 1234;
		final Integer rightInteger = 5678;

		identityHandlingComparator.compare(leftInteger, rightInteger);

		verify(delegate).compare(eq(leftInteger), eq(rightInteger), any(VisitedObjectsTrace.class), any(ComparatorOptions.class));
		verifyNoMoreInteractions(delegate);
	}

}
