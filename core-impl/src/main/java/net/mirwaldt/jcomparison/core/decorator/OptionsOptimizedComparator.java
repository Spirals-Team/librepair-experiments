package net.mirwaldt.jcomparison.core.decorator;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;
import net.mirwaldt.jcomparison.core.facade.BasicComparisonResults;

public class OptionsOptimizedComparator <ObjectType> extends DecoratingComparator<ObjectType> {
    
    private final boolean isValueComparator;

    public OptionsOptimizedComparator(ItemComparator<ObjectType, ? extends ComparisonResult<?, ?, ?>> delegate, boolean isValueComparator) {
        super(delegate);
        this.isValueComparator = isValueComparator;
    }

    @Override
    public ComparisonResult<?,?,?> compare(ObjectType leftObject,
                                           ObjectType rightObject,
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        if(isValueComparator && !comparatorOptions.doValueComparison()) {
            return BasicComparisonResults.emptyValueComparisonResult();
        } else if(!isValueComparator && !comparatorOptions.doNonValueComparison()) {
            return BasicComparisonResults.emptyNonValueComparisonResult();
        } else {
            return super.compare(leftObject, rightObject, visitedObjectsTrace, comparatorOptions);
        }
    }
}
