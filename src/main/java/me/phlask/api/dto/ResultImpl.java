package me.phlask.api.dto;

import io.vertx.core.http.HttpServerResponse;
import io.vertx.core.json.Json;

public class ResultImpl<T> implements Result<T> {

    private String message;
    private int httpCode;
    private HttpServerResponse response;

    @Override
    public Result<T> addHttpCode(int code) {
        httpCode = code;
        response.setStatusCode(code);
        return this;
    }

    @Override
    public Result<T> addResponse(T message) {
        this.message = Json.encode(message);
        return this;
    }

    @Override
    public Result<T> addResponse(Result<T> message) {
        this.message = message.getMessage();
        this.httpCode = message.getHttpCode();
        return this;
    }

    @Override
    public Result<T> addResponse(String message) {
        this.message = message;
        return this;
    }

    @Override
    public Result<T> addResponseFuture(HttpServerResponse response) {
        this.response = response;
        return this;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public int getHttpCode() {
        return httpCode;
    }

    @Override
    public void commit() {
        response.end(message);
    }

    public String toString() {
        return message;
    }
}
