package me.phlask.api.dto;

import io.vertx.core.http.HttpServerResponse;

public interface Result<T> {

    Result<T> addHttpCode(int code);

    Result<T> addResponse(T message);

    Result<T> addResponse(Result<T> message);

    Result<T> addResponse(String message);

    Result<T> addResponseFuture(HttpServerResponse response);

    String getMessage();

    int getHttpCode();

    void commit();

    static <T> Result<T> of(T message) {
        return new ResultImpl<T>().addResponse(message).addHttpCode(200);
    }
    static <T> Result<T> withFuture(HttpServerResponse response) {
        return new ResultImpl<T>().addResponseFuture(response);
    }
}
