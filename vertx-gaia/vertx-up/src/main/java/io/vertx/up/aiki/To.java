package io.vertx.up.aiki;

import io.reactivex.Observable;
import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.atom.Envelop;
import io.vertx.up.exception.WebException;
import io.vertx.up.exception._500InternalServerException;
import io.vertx.zero.atom.Mirror;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

class To {

    @SuppressWarnings("unchecked")
    static <T> Future<T> toFuture(final T entity) {
        return Fn.getNull(Future.future(),
                () -> Fn.getSemi(entity instanceof Throwable, null,
                        () -> Future.failedFuture((Throwable) entity),
                        () -> Future.succeededFuture(entity)),
                entity);
    }

    static JsonObject toFilters(final String[] columns, final Supplier<Object>... suppliers) {
        final JsonObject filters = new JsonObject();
        final int size = Math.min(columns.length, suppliers.length);
        for (int idx = 0; idx < size; idx++) {
            final String column = columns[idx];
            final Supplier<Object> supplier = suppliers[idx];
            if (!Ut.isNil(column) && Objects.nonNull(supplier)) {
                filters.put(column, supplier.get());
            }
        }
        return filters;
    }

    static <T> JsonObject toJson(
            final T entity,
            final String pojo) {
        return Fn.getNull(new JsonObject(),
                () -> Fn.getSemi(Ut.isNil(pojo), null,
                        () -> Ut.serializeJson(entity),
                        () -> Mirror.create(To.class)
                                .mount(pojo)
                                .connect(Ut.serializeJson(entity))
                                .to().result()),
                entity);
    }

    static <T> JsonObject toJson(
            final T entity,
            final Function<JsonObject, JsonObject> convert
    ) {
        return Fn.getSemi(null == convert, null,
                () -> toJson(entity, ""),
                () -> convert.apply(toJson(entity, "")));
    }

    static <T> JsonArray toArray(
            final List<T> list,
            final Function<JsonObject, JsonObject> convert
    ) {
        final JsonArray array = new JsonArray();
        Observable.fromIterable(list)
                .filter(Objects::nonNull)
                .map(item -> toJson(item, convert))
                .subscribe(array::add);
        return array;
    }

    static <T> JsonArray toArray(
            final List<T> list,
            final String pojo
    ) {
        final JsonArray array = new JsonArray();
        Observable.fromIterable(list)
                .filter(Objects::nonNull)
                .map(item -> toJson(item, pojo))
                .subscribe(array::add);
        return array;
    }

    @SuppressWarnings("all")
    static <T> Envelop toEnvelop(
            final T entity
    ) {
        return Fn.getNull(Envelop.ok(),
                () -> Fn.getSemi(entity instanceof WebException, null,
                        () -> Envelop.failure((WebException) entity),
                        () -> {
                            if (Envelop.class == entity.getClass()) {
                                return (Envelop) entity;
                            } else {
                                return Envelop.success(entity);
                            }
                        }),
                entity);
    }

    static <T> Function<T, List<Future<T>>> toFutureList(final Function<T, Future<T>>... functions) {
        final List<Future<T>> futures = new ArrayList<>();
        return (entity) -> {
            Observable.fromArray(functions)
                    .map(function -> function.apply(entity))
                    .subscribe(futures::add);
            return futures;
        };
    }

    static <T> Envelop toEnvelop(
            final T entity,
            final WebException error
    ) {
        return Fn.getNull(Envelop.failure(error),
                () -> Envelop.success(entity), entity);
    }

    static WebException toError(
            final Class<? extends WebException> clazz,
            final Object... args
    ) {
        if (null == clazz || null == args) {
            // Fix Cast WebException error.
            return new _500InternalServerException(To.class, "clazz arg is null");
        } else {
            return Ut.instance(clazz, args);
        }
    }

    @SuppressWarnings("all")
    static WebException toError(
            final Class<?> clazz,
            final Throwable error
    ) {
        return Fn.getSemi(error instanceof WebException, null,
                () -> (WebException) error,
                () -> new _500InternalServerException(clazz, error.getMessage()));
    }

    static Envelop toEnvelop(
            final Class<? extends WebException> clazz,
            final Object... args
    ) {
        return Envelop.failure(toError(clazz, args));
    }

    static JsonObject toUnique(
            final JsonArray array,
            final String pojo
    ) {
        return Fn.getSemi(null == array || array.isEmpty(), null,
                () -> toJson(null, pojo),
                () -> toJson(array.getValue(0), pojo));
    }
}
