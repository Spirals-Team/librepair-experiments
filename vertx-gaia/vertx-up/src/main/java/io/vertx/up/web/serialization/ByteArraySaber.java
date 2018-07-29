package io.vertx.up.web.serialization;

import io.vertx.zero.eon.Values;
import io.zero.epic.fn.Fn;

public class ByteArraySaber extends BaseSaber {

    @Override
    public Object from(final Class<?> paramType,
                       final String literal) {
        return Fn.getNull(() -> Fn.getSemi(Byte[].class == paramType ||
                        byte[].class == paramType, this.getLogger(),
                () -> literal.getBytes(Values.CHARSET), () -> new byte[0]),
                paramType, literal);
    }
}
