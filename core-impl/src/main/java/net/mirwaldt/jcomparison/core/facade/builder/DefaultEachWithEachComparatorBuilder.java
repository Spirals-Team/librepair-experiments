package net.mirwaldt.jcomparison.core.facade.builder;

import net.mirwaldt.jcomparison.core.basic.api.ComparisonResult;
import net.mirwaldt.jcomparison.core.basic.api.ItemComparator;
import net.mirwaldt.jcomparison.core.facade.ComparatorDecorators;
import net.mirwaldt.jcomparison.core.facade.ComparisonFailedExceptionHandlers;
import net.mirwaldt.jcomparison.core.facade.ValueComparators;
import net.mirwaldt.jcomparison.core.util.CopyIfNonEmptyFunction;
import net.mirwaldt.jcomparison.core.pair.api.Pair;
import net.mirwaldt.jcomparison.core.decorator.NullAcceptingComparator;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;
import net.mirwaldt.jcomparison.core.iterable.api.EachWithEachComparisonResult;
import net.mirwaldt.jcomparison.core.iterable.impl.DefaultEachWithEachComparator;
import net.mirwaldt.jcomparison.core.iterable.impl.ImmutableEachWithEachComparisonResultFunction;
import net.mirwaldt.jcomparison.core.iterable.impl.IntermediateEachWithEachComparisonResult;
import net.mirwaldt.jcomparison.core.value.api.ValueComparator;

import java.util.HashMap;
import java.util.Map;
import java.util.function.BiPredicate;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class DefaultEachWithEachComparatorBuilder {
    private Supplier<Map> createMapSupplier;
    private Function<Map, Map> copyMapFunction;

    private Function<IntermediateEachWithEachComparisonResult, Map<Pair<Object>, ComparisonResult<?,?,?>>> accessComparisonResults;

    private Function<IntermediateEachWithEachComparisonResult, EachWithEachComparisonResult> resultFunction;

    private ItemComparator<Object, ? extends ComparisonResult<?,?,?>> comparator;
    private ComparisonFailedExceptionHandler exceptionHandler;

    private Predicate<IntermediateEachWithEachComparisonResult> stopPredicate;
    private BiPredicate<Object, Object> pairFilter;

    public DefaultEachWithEachComparatorBuilder copyIntermediateResultsForMinCapacity() {
        accessComparisonResults = IntermediateEachWithEachComparisonResult::copyComparisonResults;

        return this;
    }

    public DefaultEachWithEachComparatorBuilder readIntermediateResultsForFinalResult() {
        accessComparisonResults = IntermediateEachWithEachComparisonResult::readComparisonResults;

        return this;
    }

    public DefaultEachWithEachComparatorBuilder useComparator(ItemComparator<Object, ? extends ComparisonResult<?,?,?>> comparator) {
        this.comparator = comparator;
        return this;
    }

    public DefaultEachWithEachComparatorBuilder useDefaultComparator() {
        this.comparator = ComparatorDecorators.nullAccepting(ValueComparators.switchingEqualsComparator());
        return this;
    }

    public DefaultEachWithEachComparatorBuilder useExceptionHandler(ComparisonFailedExceptionHandler exceptionHandler) {
        this.exceptionHandler = exceptionHandler;
        return this;
    }

    public DefaultEachWithEachComparatorBuilder useDefaultExceptionHandler() {
        this.exceptionHandler = ComparisonFailedExceptionHandlers.getRethrowingHandler();
        return this;
    }


    public DefaultEachWithEachComparatorBuilder findAllResults() {
        this.stopPredicate = (intermediateComparisonResult) -> false;
        return this;
    }

    public DefaultEachWithEachComparatorBuilder findFirstResultOnly() {
        this.stopPredicate = (intermediateComparisonResult) ->
                !intermediateComparisonResult.readComparisonResults().isEmpty();
        return this;
    }

    public DefaultEachWithEachComparatorBuilder findMaxNumberOfResults(int maxNumberOfResults) {
        this.stopPredicate = (intermediateComparisonResult) ->
                maxNumberOfResults <=
                        intermediateComparisonResult.readComparisonResults().size();

        return this;
    }

    public DefaultEachWithEachComparatorBuilder useCreateMapSupplier(Supplier<Map> createMapSupplier) {
        this.createMapSupplier = createMapSupplier;
        return this;
    }

    public DefaultEachWithEachComparatorBuilder useCopyMapFunction(Function<Map, Map> copyMapFunction) {
        this.copyMapFunction = copyMapFunction;
        return this;
    }

    public DefaultEachWithEachComparatorBuilder useDefaultCreateMapSupplier() {
        this.createMapSupplier = HashMap::new;
        return this;
    }

    public DefaultEachWithEachComparatorBuilder useDefaultCopyMapFunction() {
        this.copyMapFunction = new CopyIfNonEmptyFunction<Map>(Map::isEmpty, HashMap::new);
        return this;
    }

    public DefaultEachWithEachComparatorBuilder useDefaultImmutableResultFunction() {
        this.resultFunction = null;
        return this;
    }

    public DefaultEachWithEachComparatorBuilder allowAllPairs() {
        this.pairFilter = (left, right) -> true;
        return this;
    }

    public DefaultEachWithEachComparatorBuilder filterPairs(BiPredicate<Object, Object> pairFilter) {
        this.pairFilter = pairFilter;
        return this;
    }

    public DefaultEachWithEachComparator build() {
        final Supplier<IntermediateEachWithEachComparisonResult> intermediateResultField = () -> new IntermediateEachWithEachComparisonResult(createMapSupplier, copyMapFunction);

        final Function<IntermediateEachWithEachComparisonResult, EachWithEachComparisonResult> usedResultFunction;
        if (resultFunction == null) {
            usedResultFunction = new ImmutableEachWithEachComparisonResultFunction(accessComparisonResults);
        } else {
            usedResultFunction = resultFunction;
        }

        return new DefaultEachWithEachComparator(
                intermediateResultField,
                usedResultFunction,
                comparator,
                exceptionHandler,
                stopPredicate,
                pairFilter
        );
    }
}