package io.vertx.zero.exception;

import io.zero.epic.Ut;

import java.util.Set;

public class WallDuplicatedException extends UpException {

    public WallDuplicatedException(final Class<?> classes,
                                   final Set<String> classNames) {
        super(classes, Ut.fromJoin(classNames));
    }

    @Override
    public int getCode() {
        return -40038;
    }
}
