package io.vertx.up.aiki;

import io.vertx.core.Future;
import io.vertx.core.json.JsonArray;
import io.zero.epic.container.RxHod;
import io.zero.epic.fn.Fn;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Supplier;

class Functions {

    @SuppressWarnings("all")
    static BiConsumer<JsonArray, Object> fnCollectJArray() {
        return (collection, item) -> collection.add(item);
    }

    static <E, T> Future<E> fnSupplier(final RxHod container, final E entity, final Supplier<T> supplier) {
        return Fn.getNull(Future.succeededFuture(), () -> {
            if (null == supplier) {
                container.add(entity);
            } else {
                container.add(supplier.get());
            }
            return Future.succeededFuture(entity);
        }, container, entity);
    }

    static <E, T> Future<E> fnConsumer(final RxHod container, final E entity, final Consumer<T> consumer) {
        return Fn.getNull(Future.succeededFuture(), () -> {
            consumer.accept(container.get());
            return Future.succeededFuture(entity);
        }, container, entity, consumer);
    }
}
