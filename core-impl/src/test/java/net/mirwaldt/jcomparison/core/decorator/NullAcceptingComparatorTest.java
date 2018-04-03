package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.value.api.ValueComparisonResult;
import org.junit.jupiter.api.Test;

import java.util.function.Function;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Matchers.any;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.*;

@SuppressWarnings("unchecked")

public class NullAcceptingComparatorTest {

	@Test
	public void test_bothNull() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final NullAcceptingComparator<Integer> comparator = new NullAcceptingComparator<>(delegate, Function.identity());

		final Integer leftInteger = null;
		final Integer rightInteger = null;
		final ComparisonResult<?,?,?> comparisonResult = comparator.compare(leftInteger, rightInteger);

		assertTrue(comparisonResult instanceof ValueComparisonResult);
		final ValueComparisonResult<Integer> valueComparisonResult = (ValueComparisonResult<Integer>) comparisonResult;

		assertTrue(valueComparisonResult.hasSimilarities());
		assertNull(valueComparisonResult.getSimilarities().getSimilarValue());

		assertFalse(valueComparisonResult.hasDifferences());

		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void test_leftNull() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final NullAcceptingComparator<Integer> comparator = new NullAcceptingComparator<>(delegate, Function.identity());

		final Integer leftInteger = null;
		final Integer rightInteger = 1;
		final ComparisonResult<?,?,?> comparisonResult = comparator.compare(leftInteger, rightInteger);

		assertTrue(comparisonResult instanceof ValueComparisonResult);
		final ValueComparisonResult<Integer> valueComparisonResult = (ValueComparisonResult<Integer>) comparisonResult;

		assertFalse(valueComparisonResult.hasSimilarities());

		assertTrue(valueComparisonResult.hasDifferences());
		final Pair<Integer> pair = valueComparisonResult.getDifferences();

		assertNull(pair.getLeft());
		assertSame(rightInteger, pair.getRight());

		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void test_rightNull() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final NullAcceptingComparator<Integer> comparator = new NullAcceptingComparator<>(delegate, Function.identity());

		final Integer leftInteger = 1;
		final Integer rightInteger = null;
		final ComparisonResult<?,?,?> comparisonResult = comparator.compare(leftInteger, rightInteger);

		assertTrue(comparisonResult instanceof ValueComparisonResult);
		final ValueComparisonResult<Integer> valueComparisonResult = (ValueComparisonResult<Integer>) comparisonResult;

		assertFalse(valueComparisonResult.hasSimilarities());

		assertTrue(valueComparisonResult.hasDifferences());
		final Pair<Integer> pair = valueComparisonResult.getDifferences();

		assertSame(leftInteger, pair.getLeft());
		assertNull(pair.getRight());

		verifyNoMoreInteractions(delegate);
	}

	@Test
	public void test_noNull() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final NullAcceptingComparator<Integer> comparator = new NullAcceptingComparator<>(delegate, Function.identity());

		final Integer leftInteger = 1;
		final Integer rightInteger = 2;
		comparator.compare(leftInteger, rightInteger);

		verify(delegate).compare(eq(leftInteger), eq(rightInteger), any(VisitedObjectsTrace.class), any(ComparatorOptions.class));
		verifyNoMoreInteractions(delegate);
	}

}
