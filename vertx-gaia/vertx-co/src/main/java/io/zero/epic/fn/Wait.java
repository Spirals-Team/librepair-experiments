package io.zero.epic.fn;


import io.vertx.core.AsyncResult;
import io.vertx.core.Future;
import io.zero.epic.fn.wait.Case;

import java.util.function.Consumer;
import java.util.function.Supplier;

class Wait {

    @SuppressWarnings("all")
    static <T> Future<T> then(final Object asyncResult,
                              final Future<T> future,
                              final Throwable error) {
        final AsyncResult<T> result = (AsyncResult<T>) asyncResult;
        if (result.succeeded()) {
            future.complete(result.result());
        } else {
            future.fail(error);
        }
        return future;
    }

    static <T> Future<T> then(final Consumer<Future<T>> consumer) {
        final Future<T> future = Future.future();
        consumer.accept(future);
        return future;
    }

    static <T> Case<T> branch(
            final Supplier<Future<T>> caseLine) {
        return Case.item(caseLine);
    }

    static <T> Case<T> branch(
            final Actuator executor,
            final Supplier<Future<T>> caseLine
    ) {
        if (null != executor) {
            executor.execute();
        }
        return Case.item(caseLine);
    }

    static <T> Case<T> branch(
            final boolean condition,
            final Supplier<Future<T>> caseLine
    ) {
        return Case.item(() -> condition, caseLine);
    }

    static <T> Case<T> branch(
            final boolean condition,
            final Actuator executor,
            final Supplier<Future<T>> caseLine) {
        if (condition) {
            if (null != executor) {
                executor.execute();
            }
        }
        return branch(condition, caseLine);
    }

    static <T> Case<T> match(
            final Supplier<Case.DefaultCase<T>> defaultSupplier,
            final Case<T>... matchers
    ) {
        for (final Case<T> each : matchers) {
            if (each.first.get()) {
                return each;
            }
        }
        return defaultSupplier.get();
    }
}
