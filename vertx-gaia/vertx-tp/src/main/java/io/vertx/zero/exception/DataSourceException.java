package io.vertx.zero.exception;

public class DataSourceException extends UpException {

    public DataSourceException(final Class<?> clazz,
                               final Throwable ex,
                               final String jdbcUrl) {
        super(clazz, jdbcUrl, ex.getMessage());
    }

    @Override
    public int getCode() {
        return -40056;
    }
}

