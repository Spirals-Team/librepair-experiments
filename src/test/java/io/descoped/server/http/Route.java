package io.descoped.server.http;

import io.undertow.server.HttpServerExchange;

@FunctionalInterface
public interface Route {

    void dispatch(HttpServerExchange exchange);

    static Route createLoopback() {
        return new LoopbackRoute();
    }
}
