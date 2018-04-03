package net.mirwaldt.jcomparison.core.facade;

import net.mirwaldt.jcomparison.core.exception.ResultsCollectingComparisonFailedException;
import net.mirwaldt.jcomparison.core.exception.handler.api.ComparisonFailedExceptionHandler;

public class ComparisonFailedExceptionHandlers {

    private final static ComparisonFailedExceptionHandler RETHROWING_HANDLER = (e) -> { throw e; };

    private final static ComparisonFailedExceptionHandler RESULTS_COLLECTING_HANDLER = (e) -> {
        if(e instanceof ResultsCollectingComparisonFailedException) {
            throw e;
        } else {
            throw new ResultsCollectingComparisonFailedException(e, BasicComparisonResults.emptyNonValueComparisonResult());
        }
    };

    private final static ComparisonFailedExceptionHandler EMPTY_HANDLER = (e) -> BasicComparisonResults.emptyNonValueComparisonResult();

    private final static ComparisonFailedExceptionHandler SKIPPING_HANDLER = (e) -> BasicComparisonResults.skipComparisonResult();

    private final static ComparisonFailedExceptionHandler STOPPING_HANDLER = (e) -> BasicComparisonResults.stopComparisonResult();

    public static ComparisonFailedExceptionHandler getRethrowingHandler() {
        return RETHROWING_HANDLER;
    }

    public static ComparisonFailedExceptionHandler getResultsCollectingHandler() {
        return RESULTS_COLLECTING_HANDLER;
    }

    public static ComparisonFailedExceptionHandler getEmptyHandler() {
        return EMPTY_HANDLER;
    }

    public static ComparisonFailedExceptionHandler getSkippingHandler() {
        return SKIPPING_HANDLER;
    }

    public static ComparisonFailedExceptionHandler getStoppingHandler() {
        return STOPPING_HANDLER;
    }
}
