package io.vertx.up.web.serialization;

import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.util.function.Function;

/**
 * Double, Float, BigDecimal
 */
public abstract class DecimalSaber extends BaseSaber {

    @Override
    public Object from(final Class<?> paramType,
                       final String literal) {
        return Fn.getNull(() ->
                        Fn.getSemi(this.isValid(paramType), this.getLogger(),
                                () -> {
                                    this.verifyInput(!Ut.isDecimal(literal), paramType, literal);
                                    return this.getFun().apply(literal);
                                }, () -> 0.0),
                paramType, literal);
    }

    protected abstract boolean isValid(final Class<?> paramType);

    protected abstract <T> Function<String, T> getFun();
}
