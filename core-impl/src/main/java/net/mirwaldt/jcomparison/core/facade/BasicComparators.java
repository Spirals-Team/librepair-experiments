package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

public class BasicComparators {

    private final static ItemComparator<Object, ? extends ComparisonResult<?,?,?>> NO_COMPARATOR = 
            (leftItem, rightItem, trace, options) -> { throw new ComparisonFailedException("NO_COMPARATOR cannot compare.", leftItem, rightItem); };

    private final static ItemComparator<Object, ? extends ComparisonResult<?,?,?>> EMPTY_NON_VALUE_COMPARATOR = 
            (leftItem, rightItem, trace, options) -> BasicComparisonResults.emptyNonValueComparisonResult();

    private final static ItemComparator<Object, ? extends ComparisonResult<?,?,?>> SKIPPING_COMPARATOR = 
            (leftItem, rightItem, trace, options) -> BasicComparisonResults.skipComparisonResult();

    private final static ItemComparator<Object, ? extends ComparisonResult<?,?,?>> STOPPING_COMPARATOR = 
            (leftItem, rightItem, trace, options) -> BasicComparisonResults.stopComparisonResult();

    public static ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> getNoComparator() {
        return NO_COMPARATOR;
    }

    public static ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> getEmptyNonValueComparator() {
        return EMPTY_NON_VALUE_COMPARATOR;
    }

    public static ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> getSkippingComparator() {
        return SKIPPING_COMPARATOR;
    }

    public static ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>> getStoppingComparator() {
        return STOPPING_COMPARATOR;
    }
}
