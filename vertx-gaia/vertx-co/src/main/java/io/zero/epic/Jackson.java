package io.zero.epic;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.ZeroModule;
import io.reactivex.Observable;
import io.vertx.core.json.DecodeException;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.up.log.Annal;
import io.vertx.zero.eon.Strings;
import io.vertx.zero.eon.Values;
import io.zero.epic.fn.Fn;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

/**
 * Lookup the json tree data
 */
@SuppressWarnings({"unchecked"})
final class Jackson {

    private static final Annal LOGGER = Annal.get(Jackson.class);
    private static final ObjectMapper MAPPER = new ObjectMapper();

    static {
        // Ignore null value
        Jackson.MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        // Non-standard JSON but we allow C style comments in our JSON
        Jackson.MAPPER.configure(JsonParser.Feature.ALLOW_COMMENTS, true);
        // Jackson.MAPPER.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);
        Jackson.MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        Jackson.MAPPER.configure(DeserializationFeature.USE_BIG_DECIMAL_FOR_FLOATS, true);

        final ZeroModule module = new ZeroModule();
        Jackson.MAPPER.registerModule(module);
    }

    private Jackson() {
    }

    static ObjectMapper getMapper() {
        return MAPPER;
    }

    static JsonObject visitJObject(
            final JsonObject item,
            final String... keys
    ) {

        Ensurer.gtLength(Jackson.class, 0, (Object[]) keys);
        return Jackson.searchData(item, JsonObject.class, keys);
    }

    static JsonArray visitJArray(
            final JsonObject item,
            final String... keys
    ) {
        Ensurer.gtLength(Jackson.class, 0, (Object[]) keys);
        return Jackson.searchData(item, JsonArray.class, keys);
    }

    static Integer visitInt(
            final JsonObject item,
            final String... keys
    ) {
        Ensurer.gtLength(Jackson.class, 0, (Object[]) keys);
        return Jackson.searchData(item, Integer.class, keys);
    }

    static String visitString(
            final JsonObject item,
            final String... keys
    ) {
        Ensurer.gtLength(Jackson.class, 0, (Object[]) keys);
        return Jackson.searchData(item, String.class, keys);
    }

    private static <T> T searchData(final JsonObject data,
                                    final Class<T> clazz,
                                    final String... pathes) {
        if (null == data || Values.ZERO == pathes.length) {
            return null;
        }
        /* 1. Get current node  **/
        final JsonObject current = data.copy();
        /* 2. Extract current input key **/
        final String path = pathes[Values.IDX];
        /* 3. Continue searching if key existing, otherwise terminal. **/
        return Fn.getSemi(current.containsKey(path) && null != current.getValue(path),
                Jackson.LOGGER,
                () -> {
                    final Object curVal = current.getValue(path);
                    T result = null;
                    if (Values.ONE == pathes.length) {
                        /* 3.1. Get the end node. **/
                        if (clazz == curVal.getClass()) {
                            result = (T) curVal;
                        }
                    } else {
                        /* 3.2. Address the middle search **/
                        if (Types.isJObject(curVal)) {
                            final JsonObject continueNode = current.getJsonObject(path);
                            /* 4.Extract new key **/
                            final String[] continueKeys =
                                    Arrays.copyOfRange(pathes,
                                            Values.ONE,
                                            pathes.length);
                            result = Jackson.searchData(continueNode,
                                    clazz,
                                    continueKeys);
                        }
                    }
                    return result;
                },
                Fn::nil);
    }

    static JsonArray mergeZip(final JsonArray source, final JsonArray target,
                              final String sourceKey, final String targetKey) {
        final JsonArray result = new JsonArray();
        Fn.safeJvm(() -> Observable.fromIterable(source)
                .filter(Objects::nonNull)
                .map(item -> (JsonObject) item)
                .map(item -> item.mergeIn(Jackson.findByKey(target, targetKey, item.getValue(sourceKey))))
                .subscribe(result::add).dispose(), Jackson.LOGGER);
        return result;
    }

    private static JsonObject findByKey(final JsonArray source,
                                        final String key,
                                        final Object value) {
        return Fn.getJvm(() -> Observable.fromIterable(source)
                .filter(Objects::nonNull)
                .map(item -> (JsonObject) item)
                .filter(item -> null != item.getValue(key))
                .filter(item -> value == item.getValue(key) || item.getValue(key).equals(value))
                .first(new JsonObject()).blockingGet(), source, key);
    }

    static JsonObject validJObject(final Supplier<JsonObject> supplier) {
        JsonObject result;
        try {
            result = supplier.get();
        } catch (final DecodeException ex) {
            result = new JsonObject();
        }
        return result;
    }

    static JsonArray validJArray(final Supplier<JsonArray> supplier) {
        JsonArray result;
        try {
            result = supplier.get();
        } catch (final DecodeException ex) {
            result = new JsonArray();
        }
        return result;
    }

    static JsonArray toJArray(final Object value) {
        final JsonArray result = new JsonArray();
        Fn.safeNull(() -> {
            if (Types.isJArray(value)) {
                result.addAll((JsonArray) value);
            } else {
                result.add(value.toString());
            }
        }, value);
        return result;
    }

    static <T, R extends Iterable> R serializeJson(final T t) {
        final String content = Jackson.serialize(t);
        return Fn.getJvm(null,
                () -> Fn.getSemi(content.trim().startsWith(Strings.LEFT_BRACES), Jackson.LOGGER,
                        () -> (R) new JsonObject(content),
                        () -> (R) new JsonArray(content)), content);
    }

    static <T> String serialize(final T t) {
        return Fn.getNull(null, () -> Fn.getJvm(() -> Jackson.MAPPER.writeValueAsString(t), t), t);
    }

    static <T> T deserialize(final JsonObject value, final Class<T> type) {
        return Fn.getNull(null,
                () -> Jackson.deserialize(value.encode(), type), value);
    }

    static <T> T deserialize(final JsonArray value, final Class<T> type) {
        return Fn.getNull(null,
                () -> Jackson.deserialize(value.encode(), type), value);
    }

    static <T> List<T> deserialize(final JsonArray value, final TypeReference<List<T>> type) {
        return Fn.getNull(new ArrayList<>(),
                () -> Jackson.deserialize(value.encode(), type), value);
    }

    static <T> T deserialize(final String value, final Class<T> type) {
        return Fn.getNull(null,
                () -> Fn.getJvm(() -> Jackson.MAPPER.readValue(value, type)), value);
    }

    static <T> T deserialize(final String value, final TypeReference<T> type) {
        return Fn.getNull(null,
                () -> Fn.getJvm(() -> Jackson.MAPPER.readValue(value, type)), value);
    }

    static <T> List<T> convert(final List<JsonObject> result, final Class<T> clazz) {
        final List<T> entities = new ArrayList<>();
        result.forEach(item -> entities.add(Jackson.deserialize(item.encode(), clazz)));
        return entities;
    }


    static Object readJson(final Object value, final JsonObject data, final String key) {
        return Fn.getNull(value, () -> {
            final Object result = data.getValue(key);
            return Fn.getNull(value, () -> result, result);
        }, data, key);
    }

    static Integer readInt(final Integer value, final JsonObject data, final String key) {
        return Fn.getNull(value, () -> {
            final Object result = data.getValue(key);
            return Types.isInteger(result) ? To.toInteger(result) : value;
        }, data, key);
    }
}
