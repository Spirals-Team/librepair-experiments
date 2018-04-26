package com.github.mostroverkhov.r2.autoconfigure.client;

import java.util.function.Function;

public interface ClientHandlersProvider<T> extends Function<ApiRequesterFactory, T> {
}
