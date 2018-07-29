package io.zero.epic.fn;

import io.vertx.zero.exception.ZeroException;

@FunctionalInterface
public interface ZeroSupplier<T> {

    T get() throws ZeroException;
}
