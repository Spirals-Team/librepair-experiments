package com.github.mostroverkhov.r2.autoconfigure.server;

import com.github.mostroverkhov.r2.autoconfigure.client.ApiRequesterFactory;
import com.github.mostroverkhov.r2.core.ConnectionContext;

import java.util.function.BiFunction;

public interface ServerHandlersProvider<T> extends BiFunction<ConnectionContext, ApiRequesterFactory, T> {
}
