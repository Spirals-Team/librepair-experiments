package io.vertx.up.exception;

public class _400QueryKeyTypeException extends WebException {

    public _400QueryKeyTypeException(final Class<?> clazz,
                                     final String key,
                                     final Class<?> expectedCls,
                                     final Class<?> currentCls) {
        super(clazz, key, expectedCls, currentCls);
    }

    @Override
    public int getCode() {
        return -60022;
    }
}
