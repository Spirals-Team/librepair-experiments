package io.vertx.up.aiki;

import io.reactivex.Observable;
import io.vertx.core.AsyncResult;
import io.vertx.core.CompositeFuture;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.exception.WebException;
import io.vertx.up.exception._500InternalServerException;
import io.zero.epic.Ut;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.*;

@SuppressWarnings("unchecked")
class Fluctuate {
    /**
     * Source -> List<F> -> List<Future<S>>
     */
    static <F, S, T> Future<List<T>> thenParallel(
            final Future<List<F>> source,
            final Function<F, Future<S>> generateFun,
            final BiFunction<F, S, T> mergeFun
    ) {
        return source.compose(first -> {
            final List<Future> secondFutures = new ArrayList<>();
            Observable.fromIterable(first)
                    .map(generateFun::apply)
                    .filter(Objects::nonNull)
                    .subscribe(secondFutures::add);
            final Future<List<T>> result = Future.future();
            CompositeFuture.all(secondFutures).setHandler(res -> {
                final List<S> secondary = res.result().list();
                // Zipper Operation, the base list is first.
                final List<T> completed = Ut.zipperList(first, secondary, mergeFun);
                result.complete(completed);
            });
            return result;
        });
    }

    static Future<JsonArray> thenScatterJson(
            final Future<JsonArray> source,
            final Function<JsonObject, Future<JsonArray>> generateFun,
            final BiFunction<JsonObject, JsonArray, JsonObject> mergeFun
    ) {
        return source.compose(first -> {
            final List secondFutures = new ArrayList<>();
            Observable.fromIterable(first)
                    .map(item -> (JsonObject) item)
                    .map(generateFun::apply)
                    .subscribe(secondFutures::add);
            final Future<JsonArray> result = Future.future();
            CompositeFuture.all(secondFutures).setHandler(res -> {
                final List<JsonArray> secondary = res.result().list();
                // Zipper Operation, the base list is first
                final List<JsonObject> completed = Ut.zipperList(first.getList(), secondary, mergeFun);
                result.complete(new JsonArray(completed));
            });
            return result;
        });
    }

    static Future<JsonArray> thenParallelArray(
            final Future<JsonArray> source,
            final Function<JsonObject, Future<JsonObject>> generateFun,
            final BinaryOperator<JsonObject> operatorFun
    ) {
        return source.compose(first -> {
            final List secondFutures = new ArrayList<>();
            Observable.fromIterable(first)
                    .map(item -> (JsonObject) item)
                    .map(generateFun::apply)
                    .subscribe(secondFutures::add);
            final Future<JsonArray> result = Future.future();
            CompositeFuture.all(secondFutures).setHandler(res -> {
                final List<JsonObject> secondary = res.result().list();
                // Zipper Operation, the base list is first
                final List<JsonObject> completed = Ut.zipperList(first.getList(), secondary, operatorFun);
                result.complete(new JsonArray(completed));
            });
            return result;
        });
    }

    static Future<JsonObject> thenParallelArray(
            final Future<JsonArray>... futures
    ) {
        final Future<JsonObject> result = Future.future();
        CompositeFuture.all(Arrays.asList(futures)).setHandler(res -> {
            final JsonObject resultMap = new JsonObject();
            Ut.itList(res.result().list(), (item, index) -> resultMap.put(index.toString(), item));
            result.complete(resultMap);
        });
        return result;
    }

    static Future<JsonObject> thenParallelJson(
            final Future<JsonObject>... futures
    ) {
        final Future<JsonObject> result = Future.future();
        // thenResponse
        CompositeFuture.all(Arrays.asList(futures)).setHandler(thenResponse(result, (finished) -> {
            final JsonObject resultMap = new JsonObject();
            if (null != finished) {
                Ut.itList(finished.list(), (item, index) -> resultMap.put(index.toString(), item));
            }
            return resultMap;
        }));
        return result;
    }

    static Future<JsonObject> thenParallelJson(
            final Future<JsonObject> source,
            final Function<JsonObject, List<Future>> generateFun,
            final BiConsumer<JsonObject, JsonObject>... operatorFun
    ) {
        return source.compose(first -> {
            final List<Future> secondFutures = generateFun.apply(first);
            final Future<JsonObject> result = Future.future();
            // thenResponse
            CompositeFuture.all(secondFutures).setHandler(thenResponse(result, (finished) -> {
                if (null != finished) {
                    final List<JsonObject> secondary = finished.list();
                    // Zipper Operation, the base list is first
                    Ut.itList(secondary, (item, index) -> operatorFun[index].accept(first, item));
                }
                return first;
            }));
            return result;
        });
    }

    /**
     * Source ->
     * Target1
     * Target2
     */
    static <F, S, T> Future<T> thenComposite(
            final Future<F> source,
            final BiFunction<F, List<S>, T> mergeFun,
            final Supplier<Future<S>>... suppliers) {
        return source.compose(first -> {
            final List<Future> secondFutures = new ArrayList<>();
            Observable.fromArray(suppliers)
                    .map(Supplier::get)
                    .subscribe(secondFutures::add);
            // thenResponse
            return thenResponse(secondFutures, first, mergeFun);
        });
    }

    static Future<JsonArray> thenComposite(
            final List<Future<JsonObject>> futures
    ) {
        final Future<JsonArray> result = Future.future();
        final List<Future> converted = new ArrayList<>(futures);
        // thenResponse
        CompositeFuture.all(converted).setHandler(thenResponse(result, (finished) ->
                null == finished ? new JsonArray() : new JsonArray(finished.list())
        ));
        return result;
    }

    static <F, S, T> Future<T> thenComposite(
            final Future<F> source,
            final BiFunction<F, List<S>, T> mergeFun,
            final Function<F, Future<S>>... functions) {
        return source.compose(first -> {
            final List<Future> secondFutures = new ArrayList<>();
            Observable.fromArray(functions)
                    .map(item -> item.apply(first))
                    .subscribe(secondFutures::add);
            // thenResponse
            return thenResponse(secondFutures, first, mergeFun);
        });
    }

    static <T, F, R> Future<R> thenOtherwise(
            final Future<Boolean> conditionFuture,
            final Supplier<Future<T>> supplierTrue, final Function<T, R> trueFun,
            final Supplier<Future<F>> supplierFalse, final Function<F, R> falseFun
    ) {
        final Future<R> future = Future.future();
        conditionFuture.setHandler(handler -> {
            if (handler.succeeded() && handler.result()) {
                // Success & Boolean
                final Future<T> trueFuture = supplierTrue.get();
                trueFuture.setHandler(trueRes -> future.complete(trueFun.apply(trueRes.result())));
            } else {
                // Failed & Boolean = false;
                final Future<F> falseFuture = supplierFalse.get();
                falseFuture.setHandler(falseRes -> future.complete(falseFun.apply(falseRes.result())));
            }
        });
        return future;
    }

    static <T, R> Future<R> thenOtherwise(
            final Future<Boolean> conditionFuture,
            final Supplier<Future<T>> supplierTrue, final Function<T, R> trueFun,
            final Class<? extends WebException> clazz, final Object... args
    ) {
        final Future<R> future = Future.future();
        conditionFuture.setHandler(handler -> {
            if (handler.succeeded() && handler.result()) {
                // Success & Boolean
                final Future<T> trueFuture = supplierTrue.get();
                trueFuture.setHandler(trueRes -> future.complete(trueFun.apply(trueRes.result())));
            } else {
                // Failed & Boolean = false;
                if (null == clazz) {
                    future.complete();
                } else {
                    // Error existing
                    final WebException error = Ut.instance(clazz, args);
                    future.fail(error);
                }
            }
        });
        return future;
    }

    static <T> Future<T> thenError(
            final Class<? extends WebException> clazz,
            final Object... args
    ) {
        final WebException error = To.toError(clazz, args);
        return Future.failedFuture(error);
    }

    private static <F, S, T> Future<T> thenResponse(
            final List<Future> secondFutures,
            final F first,
            final BiFunction<F, List<S>, T> mergeFun) {
        final Future<T> result = Future.future();
        CompositeFuture.all(secondFutures).setHandler(
                thenResponse(result, (finished) -> null == finished ? null : mergeFun.apply(first, finished.list())));
        return result;
    }


    private static <T> Handler<AsyncResult<CompositeFuture>> thenResponse(
            final Future<T> future,
            final Function<CompositeFuture, T> fun) {
        return res -> {
            if (res.succeeded()) {
                final T callback = fun.apply(res.result());
                future.complete(callback);
            } else {
                if (null != res.cause()) {
                    res.cause().printStackTrace();
                    future.fail(res.cause());
                } else {
                    future.fail(new _500InternalServerException(Fluctuate.class, null));
                }
            }
        };
    }
}
