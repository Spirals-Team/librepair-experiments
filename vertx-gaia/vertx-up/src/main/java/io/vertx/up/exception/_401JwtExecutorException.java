package io.vertx.up.exception;

import io.vertx.core.http.HttpStatusCode;

public class _401JwtExecutorException extends WebException {

    public _401JwtExecutorException(final Class<?> clazz,
                                    final String token) {
        super(clazz, token);
    }

    @Override
    public int getCode() {
        return -60033;
    }

    @Override
    public HttpStatusCode getStatus() {
        return HttpStatusCode.UNAUTHORIZED;
    }
}

