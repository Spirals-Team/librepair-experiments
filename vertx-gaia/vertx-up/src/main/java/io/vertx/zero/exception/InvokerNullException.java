package io.vertx.zero.exception;

public class InvokerNullException extends UpException {

    public InvokerNullException(final Class<?> target,
                                final Class<?> returnType,
                                final Class<?> paramType) {
        super(target, returnType, paramType);
    }

    @Override
    public int getCode() {
        return -40047;
    }
}
