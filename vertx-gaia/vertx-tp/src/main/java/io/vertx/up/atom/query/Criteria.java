package io.vertx.up.atom.query;

import io.vertx.core.json.JsonObject;
import io.vertx.up.exception._400OpUnsupportException;
import io.vertx.up.exception._500QueryMetaNullException;
import io.vertx.up.log.Annal;
import io.vertx.zero.eon.Strings;
import io.zero.epic.container.KeyPair;
import io.zero.epic.fn.Fn;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Criteria for condition set, the connector is and
 * Advanced criteria will use tree mode, the flat mode is "AND"
 */
public class Criteria implements Serializable {

    private static final Annal LOGGER = Annal.get(Criteria.class);
    private final List<KeyPair<String, KeyPair<String, Object>>> conditions = new ArrayList<>();

    private Criteria(final JsonObject data) {
        Fn.outWeb(null == data, LOGGER,
                _500QueryMetaNullException.class, this.getClass());
        for (final String field : data.fieldNames()) {
            // Add
            this.add(field, data.getValue(field));
        }
    }

    public static Criteria create(final JsonObject data) {
        return new Criteria(data);
    }

    public List<KeyPair<String, KeyPair<String, Object>>> getConditions() {
        return this.conditions;
    }

    public boolean isValid() {
        return !this.conditions.isEmpty();
    }

    public Criteria add(final String field, final Object value) {
        // Field add
        final String filterField;
        final String op;
        if (field.contains(Strings.COMMA)) {
            filterField = field.split(Strings.COMMA)[0];
            op = field.split(Strings.COMMA)[1];
        } else {
            filterField = field;
            op = Inquiry.Op.EQ;
        }
        Fn.outWeb(!Inquiry.Op.VALUES.contains(op), LOGGER,
                _400OpUnsupportException.class, this.getClass(), op);
        final KeyPair<String, Object> condition = KeyPair.create(op, value);
        final KeyPair<String, KeyPair<String, Object>> item = KeyPair.create(filterField, condition);
        // At the same time.
        this.conditions.add(item);
        return this;
    }

    public JsonObject toJson() {
        final JsonObject json = new JsonObject();
        for (final KeyPair<String, KeyPair<String, Object>> item : this.conditions) {
            final String field = item.getKey();
            final KeyPair<String, Object> value = item.getValue();
            final String op = value.getKey();
            final Object hitted = value.getValue();
            json.put(field + Strings.COMMA + op, hitted);
        }
        return json;
    }
}
