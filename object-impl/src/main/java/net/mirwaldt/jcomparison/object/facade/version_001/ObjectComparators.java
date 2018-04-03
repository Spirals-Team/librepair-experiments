package net.mirwaldt.jcomparison.object.facade.version_001;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.SameTypeNiceCheckingComparator;
import net.mirwaldt.jcomparison.core.decorator.checking.SameTypeStrictCheckingComparator;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.object.impl.DefaultObjectComparator;
import net.mirwaldt.jcomparison.object.facade.builder.DefaultObjectComparatorBuilder;

public class ObjectComparators {

    public static <ObjectType> DefaultObjectComparatorBuilder<ObjectType> createDefaultObjectComparatorBuilder() {
        return new DefaultObjectComparatorBuilder<ObjectType>().
                useNoDeduplicator().
                useDefaultCopyMapFunction().
                useDefaultFieldValueAccessor().
                useDefaultExceptionHandler().
                readIntermediateResultsForFinalResult().
                findSimilaritiesAndDifferencesAndComparedObjects().
                findAllResults().
                allowAllNonStaticANdNonFinalFields().
                useDefaultComparatorProvider();
    }

    public static <ObjectType> DefaultObjectComparator<ObjectType> createDefaultObjectComparator() {
        return ObjectComparators.<ObjectType>createDefaultObjectComparatorBuilder().build();
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultObjectComparator(DefaultObjectComparatorBuilder<Object> defaultObjectComparatorBuilder, boolean isStrict) {
        return createTypeSafeDefaultObjectComparator(defaultObjectComparatorBuilder.build(), isStrict);
    }

    public static ItemComparator<Object, ComparisonResult<?,?,?>> createTypeSafeDefaultObjectComparator(DefaultObjectComparator<Object> defaultObjectComparator, boolean isStrict) {
        if(isStrict) {
            return new SameTypeStrictCheckingComparator<>(defaultObjectComparator);
        } else {
            return new SameTypeNiceCheckingComparator<>(defaultObjectComparator, ValueComparators.immutableResultEqualsComparator());
        }
    }
}
