package net.mirwaldt.jcomparison.provider.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.provider.api.ComparatorProvider;

public class ComparatorProviderUsingComparator implements ItemComparator<Object, ComparisonResult<?, ?, ?>> {
    
    private final ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> comparatorProvider;

    public ComparatorProviderUsingComparator(ComparatorProvider<ItemComparator<Object, ? extends ComparisonResult<?, ?, ?>>> comparatorProvider) {
        this.comparatorProvider = comparatorProvider;
    }

    @Override
    public ComparisonResult<?, ?, ?> compare(Object leftItem, 
                                             Object rightItem, 
                                             VisitedObjectsTrace visitedObjectsTrace,
                                             ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        final Class<?> type;
        if(leftItem != null) {
            type = leftItem.getClass();
        } else if(rightItem != null) {
            type = rightItem.getClass();
        } else {
            type = null;
        }
        return comparatorProvider.provideComparator(type, leftItem, rightItem)
                .compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
    }
}
