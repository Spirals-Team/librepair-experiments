package io.vertx.up.exception;

import io.vertx.core.http.HttpStatusCode;

public class _401JwtIssuerException extends WebException {

    public _401JwtIssuerException(final Class<?> clazz,
                                  final String issuer) {
        super(clazz, issuer);
    }

    @Override
    public int getCode() {
        return -60031;
    }

    @Override
    public HttpStatusCode getStatus() {
        return HttpStatusCode.UNAUTHORIZED;
    }
}
