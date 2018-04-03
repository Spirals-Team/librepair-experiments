package net.mirwaldt.jcomparison.core.array.impl;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.basic.api.VisitedObjectsTrace;
import net.mirwaldt.jcomparison.core.basic.impl.ComparatorOptions;
import net.mirwaldt.jcomparison.core.exception.ComparisonFailedException;

/**
 * Created by Michael on 10.09.2017.
 */
public class ElementsComparator implements ItemComparator<Object, ComparisonResult<?,?,?>> {

    private ItemComparator<Object, ComparisonResult<?,?,?>> arrayComparator;
    private ItemComparator<Object, ComparisonResult<?,?,?>> nonArrayComparator;

    public void setArrayComparator(ItemComparator<Object, ComparisonResult<?,?,?>> arrayComparator) {
        this.arrayComparator = arrayComparator;
    }

    public void setNonArrayComparator(ItemComparator<Object, ComparisonResult<?,?,?>> nonArrayComparator) {
        this.nonArrayComparator = nonArrayComparator;
    }

    @Override
    public ComparisonResult<?,?,?> compare(Object leftItem, 
                                           Object rightItem, 
                                           VisitedObjectsTrace visitedObjectsTrace,
                                           ComparatorOptions comparatorOptions) throws ComparisonFailedException {
        if(leftItem != null && rightItem != null && leftItem.getClass().isArray() && rightItem.getClass().isArray()) {
            return arrayComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
        } else {
            return nonArrayComparator.compare(leftItem, rightItem, visitedObjectsTrace, comparatorOptions);
        }
    }
}
