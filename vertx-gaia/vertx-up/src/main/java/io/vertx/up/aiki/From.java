package io.vertx.up.aiki;

import io.vertx.core.json.JsonObject;
import io.vertx.zero.atom.Mirror;
import io.vertx.zero.atom.Mojo;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

class From {

    static <T> T fromJson(final JsonObject data, final Class<T> clazz, final String pojo) {
        return Fn.getNull(Ut.instance(clazz), () -> Fn.getSemi(Ut.isNil(pojo), null,
                () -> Ut.deserialize(data, clazz),
                () -> Mirror.create(From.class)
                        .mount(pojo)
                        .connect(data)
                        .from()
                        .get()), pojo);
    }

    static JsonObject fromJson(final JsonObject criteria, final String pojo) {
        final Mojo mojo = Mirror.create(From.class).mount(pojo).mojo();
        return Query.criteria(criteria, mojo);
    }
}

