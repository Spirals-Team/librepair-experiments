package io.vertx.up.web.serialization;

import io.vertx.core.json.JsonObject;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

public class CommonSaber extends BaseSaber {
    @Override
    public Object from(final Class<?> paramType,
                       final String literal) {
        return Fn.getNull(() ->
                        Fn.getSemi(!SaberTypes.isSupport(paramType), this.getLogger(),
                                () -> Ut.deserialize(literal, paramType),
                                Fn::nil),
                paramType, literal);
    }

    @Override
    public <T> Object from(final T input) {
        return Fn.getNull(() -> {
            Object reference = null;
            if (!SaberTypes.isSupport(input.getClass())) {
                final String literal = Ut.serialize(input);
                reference = new JsonObject(literal);
            }
            return reference;
        }, input);
    }
}
