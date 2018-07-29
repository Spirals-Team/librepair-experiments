package io.zero.epic;

import io.reactivex.Observable;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.log.Annal;
import io.vertx.zero.eon.Values;
import io.zero.epic.fn.Fn;

import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

class Types {

    private static final Annal LOGGER = Annal.get(Types.class);
    private static final ConcurrentMap<Class<?>, Class<?>> UNBOXES =
            new ConcurrentHashMap<Class<?>, Class<?>>() {
                {
                    this.put(Integer.class, int.class);
                    this.put(Long.class, long.class);
                    this.put(Short.class, short.class);
                    this.put(Boolean.class, boolean.class);
                    this.put(Character.class, char.class);
                    this.put(Double.class, double.class);
                    this.put(Float.class, float.class);
                    this.put(Byte.class, byte.class);
                }
            };

    static <T extends Enum<T>> T toEnum(
            final Class<T> clazz,
            final String input) {
        return Fn.getNull(null, () -> Enum.valueOf(clazz, input), clazz, input);
    }

    static boolean isJArray(final String literal) {
        return Fn.getNull(Boolean.FALSE, () -> {
            try {
                new JsonArray(literal);
                return true;
            } catch (final DecodeException ex) {
                return false;
            }
        }, literal);
    }

    static boolean isJArray(final Object value) {
        return Fn.getSemi(null == value, LOGGER,
                () -> false,
                () -> isJArray(value.getClass()));
    }

    static boolean isJArray(final Class<?> clazz) {
        return JsonArray.class == clazz;
    }

    static boolean isVoid(final Class<?> clazz) {
        return null == clazz || Void.class == clazz || void.class == clazz;
    }

    static boolean isClass(final Object value) {
        return Fn.getSemi(null == value, LOGGER,
                () -> false,
                () -> null != Instance.clazz(value.toString()));
    }

    static boolean isJObject(final String literal) {
        return Fn.getNull(Boolean.FALSE, () -> {
            try {
                new JsonObject(literal);
                return true;
            } catch (final DecodeException ex) {
                return false;
            }
        }, literal);
    }

    static boolean isJObject(final Object value) {
        return Fn.getSemi(null == value, LOGGER,
                () -> false,
                () -> isJObject(value.getClass()));
    }

    static boolean isJObject(final Class<?> clazz) {
        return JsonObject.class == clazz || LinkedHashMap.class == clazz;
    }

    static boolean isInteger(final Object value) {
        return Fn.getSemi(null == value, LOGGER,
                () -> false,
                () -> Numeric.isInteger(value.toString()));
    }

    static boolean isInteger(final Class<?> clazz) {
        return int.class == clazz || Integer.class == clazz
                || long.class == clazz || Long.class == clazz
                || short.class == clazz || Short.class == clazz;
    }

    static boolean isDecimal(final Object value) {
        return Fn.getSemi(null == value, LOGGER,
                () -> false,
                () -> Numeric.isDecimal(value.toString()));
    }

    static boolean isDecimal(final Class<?> clazz) {
        return double.class == clazz || Double.class == clazz
                || float.class == clazz || Float.class == clazz
                || BigDecimal.class == clazz;
    }

    static boolean isBoolean(final Class<?> clazz) {
        return boolean.class == clazz || Boolean.class == clazz;
    }

    static boolean isBoolean(final Object value) {
        return Fn.getSemi(null == value, LOGGER,
                () -> false,
                () -> {
                    boolean logical = false;
                    final String literal = value.toString();
                    // Multi true literal such as "true", "TRUE" or 1
                    if (Values.TRUE.equalsIgnoreCase(literal)
                            || Integer.valueOf(1).toString().equalsIgnoreCase(literal)
                            || Values.FALSE.equalsIgnoreCase(literal)
                            || Integer.valueOf(0).toString().equalsIgnoreCase(literal)) {
                        logical = true;
                    }
                    return logical;
                });
    }

    static boolean isDate(final Object value) {
        return Fn.getSemi(null == value, LOGGER,
                () -> false,
                () -> Period.isValid(value.toString()));
    }

    static boolean isArray(final Object value) {
        if (null == value) {
            return false;
        }
        return (value instanceof Collection ||
                value.getClass().isArray());
    }

    static String toString(final Object reference) {
        return Fn.getNull("null", () -> {
            final String literal;
            if (isJObject(reference)) {
                // Fix issue for serialization
                literal = ((JsonObject) reference).encode();
            } else if (isJArray(reference)) {
                // Fix issue for serialization
                literal = ((JsonArray) reference).encode();
            } else {
                literal = reference.toString();
            }
            return literal;
        }, reference);
    }

    static <T> JsonArray toJArray(final Set<T> set) {
        final JsonArray array = new JsonArray();
        Observable.fromIterable(set)
                .subscribe(array::add).dispose();
        return array;
    }

    static <T> JsonArray toJArray(final List<T> list) {
        final JsonArray array = new JsonArray();
        Observable.fromIterable(list)
                .subscribe(array::add).dispose();
        return array;
    }

    static <T> JsonArray toJArray(final T entity, final int repeat) {
        final JsonArray array = new JsonArray();
        for (int idx = Values.IDX; idx < repeat; idx++) {
            array.add(entity);
        }
        return array;
    }

    static Collection toCollection(final Object value) {
        return Fn.getNull(() -> {
            // Collection
            if (value instanceof Collection) {
                return ((Collection) value);
            }
            // JsonArray
            if (Types.isJArray(value)) {
                return ((JsonArray) value).getList();
            }
            // Object[]
            if (Types.isArray(value)) {
                // Array
                final Object[] values = (Object[]) value;
                return Arrays.asList(values);
            }
            return null;
        }, value);
    }

    /**
     * Check Primary
     *
     * @param source
     * @return
     */
    static boolean isPrimary(final Class<?> source) {
        return UNBOXES.values().contains(source);
    }

    static Class<?> toPrimary(final Class<?> source) {
        return UNBOXES.getOrDefault(source, source);
    }
}
