package io.vertx.up.aiki;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.atom.query.Inquiry;
import io.vertx.up.log.Annal;
import io.vertx.zero.atom.Mirror;
import io.vertx.zero.atom.Mojo;
import io.vertx.zero.eon.Strings;
import io.zero.epic.Ut;
import io.zero.epic.fn.Fn;

import java.util.concurrent.ConcurrentMap;

class Query {

    private static final Annal LOGGER = Annal.get(Query.class);

    static Inquiry getInquiry(final JsonObject envelop, final String pojo) {
        return Fn.getNull(Inquiry.create(new JsonObject()), () -> {
            final JsonObject data = envelop.copy();
            if (Ut.isNil(pojo)) {
                return Inquiry.create(data);
            } else {
                // Projection Process
                final Mojo mojo = Mirror.create(Query.class).mount(pojo).mojo();
                if (data.containsKey("projection")) {
                    data.put("projection", projection(data.getJsonArray("projection"), mojo));
                }
                if (data.containsKey("sorter")) {
                    data.put("sorter", sorter(data.getJsonArray("sorter"), mojo));
                }
                if (data.containsKey("criteria")) {
                    data.put("criteria", criteria(data.getJsonObject("criteria"), mojo));
                }
                LOGGER.info(Info.INQUIRY_MESSAGE, data.encode());
                return Inquiry.create(data);
            }
        }, envelop);
    }

    private static JsonArray sorter(final JsonArray sorter, final Mojo mojo) {
        final JsonArray sorters = new JsonArray();
        final ConcurrentMap<String, String> mapping = mojo.getColumns();
        Ut.itJArray(sorter, String.class, (item, index) -> {
            final String key = item.contains(Strings.COMMA) ? item.split(Strings.COMMA)[0] : item;
            if (mapping.containsKey(key)) {
                final String targetField = mapping.get(key);
                if (item.contains(Strings.COMMA)) {
                    sorters.add(targetField + Strings.COMMA + item.split(Strings.COMMA)[1]);
                } else {
                    sorters.add(targetField + Strings.COMMA + "ASC");
                }
            } else {
                sorters.add(item);
            }
        });
        return sorters;
    }

    static JsonObject criteria(final JsonObject criteria, final Mojo mojo) {
        final JsonObject criterias = new JsonObject();
        final ConcurrentMap<String, String> mapping = mojo.getColumns();
        for (final String field : criteria.fieldNames()) {
            // Filter processed
            final String key = field.contains(Strings.COMMA) ? field.split(Strings.COMMA)[0] : field;
            String targetField = "";
            if (mapping.containsKey(key)) {
                if (field.contains(Strings.COMMA)) {
                    targetField = mapping.get(key) + Strings.COMMA + field.split(Strings.COMMA)[1];
                } else {
                    targetField = mapping.get(key);
                }
                // Ignore non-existing field in mapping here to avoid SQL errors.
                criterias.put(targetField, criteria.getValue(field));
            }
        }
        return criterias;
    }

    private static JsonArray projection(final JsonArray projections, final Mojo mojo) {
        final JsonArray result = new JsonArray();
        final ConcurrentMap<String, String> mapping = mojo.getRevert();
        Ut.itJArray(projections, String.class, (item, index) ->
                result.add(null == mapping.get(item) ? item : mapping.get(item)));
        return result;
    }
}
