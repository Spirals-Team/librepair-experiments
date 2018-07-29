package io.vertx.zero.exception;

public class DynamicConfigTypeException extends UpException {

    public DynamicConfigTypeException(final Class<?> clazz,
                                      final String key,
                                      final Class<?> current) {
        super(clazz, key, current);
    }

    @Override
    public int getCode() {
        return -10007;
    }
}
