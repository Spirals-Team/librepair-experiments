package io.vertx.zero.exception;

public class JooqArgumentException extends UpException {

    public JooqArgumentException(final Class<?> clazz,
                                 final Class<?> type) {
        super(clazz, type);
    }

    @Override
    public int getCode() {
        return -40055;
    }
}
