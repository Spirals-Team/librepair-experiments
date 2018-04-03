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

public class NullRejectingComparatorTest {

	@Test
	public void test_bothNull() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final NullRejectingComparator<Integer> comparator = new NullRejectingComparator<>(delegate);

		final Integer leftInteger = null;
		final Integer rightInteger = null;
		assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftInteger, rightInteger));
	}
	
	@Test
	public void test_leftNull() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final NullRejectingComparator<Integer> comparator = new NullRejectingComparator<>(delegate);

		final Integer leftInteger = null;
		final Integer rightInteger = 1;
		assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftInteger, rightInteger));
	}

	@Test
	public void test_rightNull() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final NullRejectingComparator<Integer> comparator = new NullRejectingComparator<>(delegate);

		final Integer leftInteger = 1;
		final Integer rightInteger = null;
		assertThrows(ComparisonFailedException.class, () -> comparator.compare(leftInteger, rightInteger));
	}
	
	@Test
	public void test_noNull() throws ComparisonFailedException {
		final ItemComparator<Integer, ComparisonResult<?,?,?>> delegate = mock(ItemComparator.class);
		final NullRejectingComparator<Integer> comparator = new NullRejectingComparator<>(delegate);

		final Integer leftInteger = 1;
		final Integer rightInteger = 2;
		comparator.compare(leftInteger, rightInteger);

		verify(delegate).compare(eq(leftInteger), eq(rightInteger), any(VisitedObjectsTrace.class), any(ComparatorOptions.class));
		verifyNoMoreInteractions(delegate);
	}
}
