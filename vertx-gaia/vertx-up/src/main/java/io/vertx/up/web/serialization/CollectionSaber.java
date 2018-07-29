package io.vertx.up.web.serialization;

import io.vertx.core.json.JsonArray;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

/**
 * Enum
 */
public class CollectionSaber extends BaseSaber {
    @Override
    public <T> Object from(final T input) {
        return Fn.getNull(() -> {
            final String literal = Ut.serialize(input);
            return new JsonArray(literal);
        }, input);
    }

    @Override
    public Object from(final Class<?> paramType,
                       final String literal) {
        // Default direct
        return Ut.deserialize(literal, paramType);
    }
}
