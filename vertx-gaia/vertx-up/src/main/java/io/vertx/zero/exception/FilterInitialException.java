package io.vertx.zero.exception;

public class FilterInitialException extends UpException {

    public FilterInitialException(final Class<?> clazz,
                                  final Class<?> filterCls) {
        super(clazz, filterCls);
    }

    @Override
    public int getCode() {
        return -40054;
    }
}
