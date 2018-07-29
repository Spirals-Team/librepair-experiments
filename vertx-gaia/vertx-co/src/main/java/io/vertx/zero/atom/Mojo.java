package io.vertx.zero.atom;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.ClassDeserializer;
import com.fasterxml.jackson.databind.ClassSerializer;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import io.vertx.up.log.Annal;
import io.zero.epic.fn.Fn;

import java.io.Serializable;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Pojo metadata container
 * Meta of Java Object
 */
public class Mojo implements Serializable {

    private static final Annal LOGGER = Annal.get(Mojo.class);
    private static final String TYPE = "type";
    private static final String MAPPING = "mapping";
    private static final String COLUMNS = "columns";

    @JsonProperty(TYPE)
    @JsonSerialize(using = ClassSerializer.class)
    @JsonDeserialize(using = ClassDeserializer.class)
    private Class<?> type;

    @JsonProperty(MAPPING)
    private ConcurrentMap<String, String> config = new ConcurrentHashMap<>();

    @JsonProperty(COLUMNS)
    private ConcurrentMap<String, String> columns = new ConcurrentHashMap<>();

    public Class<?> getType() {
        return this.type;
    }

    public void setType(final Class<?> type) {
        this.type = type;
    }

    ConcurrentMap<String, String> getMapper() {
        // Fix no mapping issue for empty mapping conversion.
        Fn.safeSemi(null == this.config, LOGGER, () -> this.config = new ConcurrentHashMap<>());
        return this.config;
    }

    public ConcurrentMap<String, String> getRevert() {
        Fn.safeSemi(this.config.keySet().size() != this.config.values().size(), LOGGER,
                () -> LOGGER.warn(Info.VALUE_SAME,
                        this.config.keySet().size(), this.config.values().size()));
        final ConcurrentMap<String, String> mapper =
                new ConcurrentHashMap<>();
        this.config.forEach((key, value) -> mapper.put(value, key));
        return mapper;
    }

    public ConcurrentMap<String, String> getColumns() {
        Fn.safeSemi(null == this.columns, LOGGER, () -> this.columns = new ConcurrentHashMap<>());
        return this.columns;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof Mojo)) {
            return false;
        }
        final Mojo mojo = (Mojo) o;
        return Objects.equals(this.type, mojo.type);
    }

    @Override
    public int hashCode() {

        return Objects.hash(this.type);
    }
}
