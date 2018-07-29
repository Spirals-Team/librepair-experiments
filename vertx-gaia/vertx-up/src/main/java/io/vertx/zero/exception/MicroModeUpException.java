package io.vertx.zero.exception;

public class MicroModeUpException extends UpException {

    public MicroModeUpException(final Class<?> clazz,
                                final String message) {
        super(clazz, message);
    }

    @Override
    public int getCode() {
        return -40050;
    }
}
