package io.vertx.up.web.serialization;

import io.vertx.zero.eon.Strings;
import io.zero.epic.fn.Fn;

/**
 * String
 */
public class StringSaber extends BaseSaber {

    @Override
    public Object from(final Class<?> paramType,
                       final String literal) {
        return Fn.getNull(() ->
                        Fn.getSemi(String.class == paramType, this.getLogger(),
                                () -> literal.toString(), () -> Strings.EMPTY),
                paramType, literal);
    }
}
