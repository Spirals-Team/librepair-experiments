package io.vertx.up.exception;

import java.lang.reflect.Method;

public class _500RpcTransitInvokeException extends WebException {

    public _500RpcTransitInvokeException(final Class<?> clazz,
                                         final Method method,
                                         final Throwable ex) {
        super(clazz, ex.getMessage(), method);
    }

    @Override
    public int getCode() {
        return -60036;
    }
}
