package io.descoped.client.http;

import io.descoped.client.http.internal.httpRequest.HttpRequestExchange;

public interface Exchange<T> {

    Response<T> response();

    static <T> Exchange<T> createHttpRequestExchange(Request consumer, ResponseBodyHandler<T> responseBodyHandler) {
        return new HttpRequestExchange<>(consumer, responseBodyHandler);
    }

}
