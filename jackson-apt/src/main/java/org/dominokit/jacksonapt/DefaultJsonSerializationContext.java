/*
 * Copyright 2013 Nicolas Morel
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.dominokit.jacksonapt;

import com.fasterxml.jackson.annotation.ObjectIdGenerator;
import org.dominokit.jacksonapt.exception.JsonSerializationException;
import org.dominokit.jacksonapt.ser.bean.AbstractBeanJsonSerializer;
import org.dominokit.jacksonapt.ser.bean.ObjectIdSerializer;
import org.dominokit.jacksonapt.stream.JsonWriter;
import org.dominokit.jacksonapt.stream.impl.FastJsonWriter;

import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Context for the serialization process.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class DefaultJsonSerializationContext implements JsonSerializationContext {

    /**
     * Builder for {@link JsonSerializationContext}. To override default settings globally, you can extend this class, modify the
     * default settings inside the constructor and tell the compiler to use your builder instead in your gwt.xml file :
     * <pre>
     * {@code
     *
     * <replace-with class="your.package.YourBuilder">
     *   <when-type-assignable class="org.dominokit.jacksonapt.JsonSerializationContext.Builder" />
     * </replace-with>
     *
     * }
     * </pre>
     */
    public static class Builder {

        protected boolean useEqualityForObjectId = false;

        protected boolean serializeNulls = true;

        protected boolean writeDatesAsTimestamps = true;

        protected boolean writeDateKeysAsTimestamps = false;

        protected boolean indent = false;

        protected boolean wrapRootValue = false;

        protected boolean writeCharArraysAsJsonArrays = false;

        protected boolean writeNullMapValues = true;

        protected boolean writeEmptyJsonArrays = true;

        protected boolean orderMapEntriesByKeys = false;

        protected boolean writeSingleElemArraysUnwrapped = false;

        protected boolean wrapExceptions = true;

        /**
         * @deprecated Use {@link DefaultJsonSerializationContext#builder()} instead. This constructor will be made protected in v1.0.
         */
        @Deprecated
        public Builder() {
        }

        /**
         * Determines whether Object Identity is compared using
         * true JVM-level identity of Object (false); or, <code>equals()</code> method.
         * Latter is sometimes useful when dealing with Database-bound objects with
         * ORM libraries (like Hibernate).
         * <p>
         * Option is disabled by default; meaning that strict identity is used, not
         * <code>equals()</code>
         * </p>
         *
         * @param useEqualityForObjectId true if should useEqualityForObjectId
         * @return the builder
         */
        public Builder useEqualityForObjectId(boolean useEqualityForObjectId) {
            this.useEqualityForObjectId = useEqualityForObjectId;
            return this;
        }

        /**
         * Sets whether object members are serialized when their value is null.
         * This has no impact on array elements. The default is true.
         *
         * @param serializeNulls true if should serializeNulls
         * @return the builder
         */
        public Builder serializeNulls(boolean serializeNulls) {
            this.serializeNulls = serializeNulls;
            return this;
        }

        /**
         * Determines whether {@link java.util.Date} and {@link java.sql.Timestamp} values are to be serialized as numeric timestamps
         * (true; the default), or as textual representation.
         * <p>If textual representation is used, the actual format is
         * Option is enabled by default.
         *
         * @param writeDatesAsTimestamps true if should writeDatesAsTimestamps
         * @return the builder
         */
        public Builder writeDatesAsTimestamps(boolean writeDatesAsTimestamps) {
            this.writeDatesAsTimestamps = writeDatesAsTimestamps;
            return this;
        }

        /**
         * Feature that determines whether {@link java.util.Date}s and {@link java.sql.Timestamp}s used as {@link Map} keys are
         * serialized as timestamps or as textual values.
         * <p>If textual representation is used, the actual format is
         * Option is disabled by default.
         *
         * @param writeDateKeysAsTimestamps true if should writeDateKeysAsTimestamps
         * @return the builder
         */
        public Builder writeDateKeysAsTimestamps(boolean writeDateKeysAsTimestamps) {
            this.writeDateKeysAsTimestamps = writeDateKeysAsTimestamps;
            return this;
        }

        /**
         * Feature that allows enabling (or disabling) indentation
         * for the underlying writer.
         * <p>Feature is disabled by default.</p>
         *
         * @param indent true if should indent
         * @return the builder
         */
        public Builder indent(boolean indent) {
            this.indent = indent;
            return this;
        }

        /**
         * Feature that can be enabled to make root value (usually JSON
         * Object but can be any type) wrapped within a single property
         * JSON object, where key as the "root name", as determined by
         * annotation introspector or fallback (non-qualified
         * class name).
         * <p>Feature is disabled by default.</p>
         *
         * @param wrapRootValue true if should wrapRootValue
         * @return the builder
         */
        public Builder wrapRootValue(boolean wrapRootValue) {
            this.wrapRootValue = wrapRootValue;
            return this;
        }

        /**
         * Feature that determines how type <code>char[]</code> is serialized:
         * when enabled, will be serialized as an explict JSON array (with
         * single-character Strings as values); when disabled, defaults to
         * serializing them as Strings (which is more compact).
         * <p>
         * Feature is disabled by default.
         * </p>
         *
         * @param writeCharArraysAsJsonArrays true if should writeCharArraysAsJsonArrays
         * @return the builder
         */
        public Builder writeCharArraysAsJsonArrays(boolean writeCharArraysAsJsonArrays) {
            this.writeCharArraysAsJsonArrays = writeCharArraysAsJsonArrays;
            return this;
        }

        /**
         * Feature that determines whether Map entries with null values are
         * to be serialized (true) or not (false).
         * <p>
         * Feature is enabled by default.
         * </p>
         *
         * @param writeNullMapValues true if should writeNullMapValues
         * @return the builder
         */
        public Builder writeNullMapValues(boolean writeNullMapValues) {
            this.writeNullMapValues = writeNullMapValues;
            return this;
        }

        /**
         * Feature that determines whether Container properties (POJO properties
         * with declared value of Collection or array; i.e. things that produce JSON
         * arrays) that are empty (have no elements)
         * will be serialized as empty JSON arrays (true), or suppressed from output (false).
         * <p>
         * Note that this does not change behavior of {@link Map}s, or
         * "Collection-like" types.
         * </p>
         * <p>
         * Feature is enabled by default.
         * </p>
         *
         * @param writeEmptyJsonArrays true if should writeEmptyJsonArrays
         * @return the builder
         */
        public Builder writeEmptyJsonArrays(boolean writeEmptyJsonArrays) {
            this.writeEmptyJsonArrays = writeEmptyJsonArrays;
            return this;
        }

        /**
         * Feature that determines whether {@link Map} entries are first
         * sorted by key before serialization or not: if enabled, additional sorting
         * step is performed if necessary (not necessary for {@link java.util.SortedMap}s),
         * if disabled, no additional sorting is needed.
         * <p>
         * Feature is disabled by default.
         * </p>
         *
         * @param orderMapEntriesByKeys true if should orderMapEntriesByKeys
         * @return the builder
         */
        public Builder orderMapEntriesByKeys(boolean orderMapEntriesByKeys) {
            this.orderMapEntriesByKeys = orderMapEntriesByKeys;
            return this;
        }

        /**
         * Feature added for interoperability, to work with oddities of
         * so-called "BadgerFish" convention.
         * Feature determines handling of single element {@link java.util.Collection}s
         * and arrays: if enabled, {@link java.util.Collection}s and arrays that contain exactly
         * one element will be serialized as if that element itself was serialized.
         * <br>
         * <br>
         * When enabled, a POJO with array that normally looks like this:
         * <pre>
         *  { "arrayProperty" : [ 1 ] }
         * </pre>
         * will instead be serialized as
         * <pre>
         *  { "arrayProperty" : 1 }
         * </pre>
         * <p>
         * Note that this feature is counterpart to {@link DefaultJsonDeserializationContext.Builder#acceptSingleValueAsArray(boolean)}
         * (that is, usually both are enabled, or neither is).
         * </p>
         * Feature is disabled by default, so that no special handling is done.
         *
         * @param writeSingleElemArraysUnwrapped true if should writeSingleElemArraysUnwrapped
         * @return the builder
         */
        public Builder writeSingleElemArraysUnwrapped(boolean writeSingleElemArraysUnwrapped) {
            this.writeSingleElemArraysUnwrapped = writeSingleElemArraysUnwrapped;
            return this;
        }

        /**
         * Feature that determines whether gwt-jackson code should catch
         * and wrap {@link RuntimeException}s (but never {@link Error}s!)
         * to add additional information about
         * location (within input) of problem or not. If enabled,
         * exceptions will be caught and re-thrown; this can be
         * convenient both in that all exceptions will be checked and
         * declared, and so there is more contextual information.
         * However, sometimes calling application may just want "raw"
         * unchecked exceptions passed as is.
         * <br>
         * <br>
         * Feature is enabled by default.
         *
         * @param wrapExceptions true if should wrapExceptions
         * @return the builder
         */
        public Builder wrapExceptions(boolean wrapExceptions) {
            this.wrapExceptions = wrapExceptions;
            return this;
        }

        public final JsonSerializationContext build() {
            return new DefaultJsonSerializationContext(useEqualityForObjectId, serializeNulls, writeDatesAsTimestamps,
                    writeDateKeysAsTimestamps, indent, wrapRootValue, writeCharArraysAsJsonArrays, writeNullMapValues,
                    writeEmptyJsonArrays, orderMapEntriesByKeys, writeSingleElemArraysUnwrapped, wrapExceptions);
        }
    }

    public static class DefaultBuilder extends Builder {

        private DefaultBuilder() {
        }

    }

    /**
     * <p>builder</p>
     *
     * @return a {@link org.dominokit.jacksonapt.DefaultJsonSerializationContext.Builder} object.
     */
    public static Builder builder() {
        return new DefaultBuilder();
    }

    private static final Logger logger = Logger.getLogger("JsonSerialization");

    private Map<Object, ObjectIdSerializer<?>> mapObjectId;

    private List<ObjectIdGenerator<?>> generators;

    /*
     * Serialization options
     */
    private final boolean useEqualityForObjectId;

    private final boolean serializeNulls;

    private final boolean writeDatesAsTimestamps;

    private final boolean writeDateKeysAsTimestamps;

    private final boolean indent;

    private final boolean wrapRootValue;

    private final boolean writeCharArraysAsJsonArrays;

    private final boolean writeNullMapValues;

    private final boolean writeEmptyJsonArrays;

    private final boolean orderMapEntriesByKeys;

    private final boolean writeSingleElemArraysUnwrapped;

    private final boolean wrapExceptions;

    private DefaultJsonSerializationContext(boolean useEqualityForObjectId, boolean serializeNulls, boolean writeDatesAsTimestamps, boolean
            writeDateKeysAsTimestamps, boolean indent, boolean wrapRootValue, boolean writeCharArraysAsJsonArrays, boolean
                                                    writeNullMapValues, boolean writeEmptyJsonArrays, boolean orderMapEntriesByKeys, boolean
                                                    writeSingleElemArraysUnwrapped,
                                            boolean wrapExceptions) {
        this.useEqualityForObjectId = useEqualityForObjectId;
        this.serializeNulls = serializeNulls;
        this.writeDatesAsTimestamps = writeDatesAsTimestamps;
        this.writeDateKeysAsTimestamps = writeDateKeysAsTimestamps;
        this.indent = indent;
        this.wrapRootValue = wrapRootValue;
        this.writeCharArraysAsJsonArrays = writeCharArraysAsJsonArrays;
        this.writeNullMapValues = writeNullMapValues;
        this.writeEmptyJsonArrays = writeEmptyJsonArrays;
        this.orderMapEntriesByKeys = orderMapEntriesByKeys;
        this.writeSingleElemArraysUnwrapped = writeSingleElemArraysUnwrapped;
        this.wrapExceptions = wrapExceptions;
    }

    /** {@inheritDoc} */
    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isSerializeNulls</p>
     * @see Builder#serializeNulls(boolean)
     */
    @Override
    public boolean isSerializeNulls() {
        return serializeNulls;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isWriteDatesAsTimestamps</p>
     * @see Builder#writeDatesAsTimestamps(boolean)
     */
    @Override
    public boolean isWriteDatesAsTimestamps() {
        return writeDatesAsTimestamps;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isWriteDateKeysAsTimestamps</p>
     * @see Builder#writeDateKeysAsTimestamps(boolean)
     */
    @Override
    public boolean isWriteDateKeysAsTimestamps() {
        return writeDateKeysAsTimestamps;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isWrapRootValue</p>
     * @see Builder#wrapRootValue(boolean)
     */
    @Override
    public boolean isWrapRootValue() {
        return wrapRootValue;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isWriteCharArraysAsJsonArrays</p>
     * @see Builder#writeCharArraysAsJsonArrays(boolean)
     */
    @Override
    public boolean isWriteCharArraysAsJsonArrays() {
        return writeCharArraysAsJsonArrays;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isWriteNullMapValues</p>
     * @see Builder#writeNullMapValues(boolean)
     */
    @Override
    public boolean isWriteNullMapValues() {
        return writeNullMapValues;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isWriteEmptyJsonArrays</p>
     * @see Builder#writeEmptyJsonArrays(boolean)
     */
    @Override
    public boolean isWriteEmptyJsonArrays() {
        return writeEmptyJsonArrays;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isOrderMapEntriesByKeys</p>
     * @see Builder#orderMapEntriesByKeys(boolean)
     */
    @Override
    public boolean isOrderMapEntriesByKeys() {
        return orderMapEntriesByKeys;
    }

    /**
     * {@inheritDoc}
     *
     * <p>isWriteSingleElemArraysUnwrapped</p>
     * @see Builder#writeSingleElemArraysUnwrapped(boolean)
     */
    @Override
    public boolean isWriteSingleElemArraysUnwrapped() {
        return writeSingleElemArraysUnwrapped;
    }

    /**
     * {@inheritDoc}
     *
     * <p>newJsonWriter</p>
     */
    @Override
    public JsonWriter newJsonWriter() {
        JsonWriter writer = new FastJsonWriter(new StringBuilder());
        writer.setLenient(true);
        if (indent) {
            writer.setIndent("  ");
        }
        return writer;
    }

    /**
     * {@inheritDoc}
     *
     * Trace an error and returns a corresponding exception.
     */
    @Override
    public JsonSerializationException traceError(Object value, String message) {
        getLogger().log(Level.SEVERE, message);
        return new JsonSerializationException(message);
    }

    /**
     * {@inheritDoc}
     *
     * Trace an error with current writer state and returns a corresponding exception.
     */
    @Override
    public JsonSerializationException traceError(Object value, String message, JsonWriter writer) {
        JsonSerializationException exception = traceError(value, message);
        traceWriterInfo(value, writer);
        return exception;
    }

    /**
     * {@inheritDoc}
     *
     * Trace an error and returns a corresponding exception.
     */
    @Override
    public RuntimeException traceError(Object value, RuntimeException cause) {
        getLogger().log(Level.SEVERE, "Error during serialization", cause);
        if (wrapExceptions) {
            return new JsonSerializationException(cause);
        } else {
            return cause;
        }
    }

    /**
     * {@inheritDoc}
     *
     * Trace an error with current writer state and returns a corresponding exception.
     */
    @Override
    public RuntimeException traceError(Object value, RuntimeException cause, JsonWriter writer) {
        RuntimeException exception = traceError(value, cause);
        traceWriterInfo(value, writer);
        return exception;
    }

    /**
     * Trace the current writer state
     *
     * @param value current value
     */
    private void traceWriterInfo(Object value, JsonWriter writer) {
        if (getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Error on value <" + value + ">. Current output : <" + writer.getOutput() + ">");
        }
    }

    /**
     * {@inheritDoc}
     *
     * <p>addObjectId</p>
     */
    @Override
    public void addObjectId(Object object, ObjectIdSerializer<?> id) {
        if (null == mapObjectId) {
            if (useEqualityForObjectId) {
                mapObjectId = new HashMap<Object, ObjectIdSerializer<?>>();
            } else {
                mapObjectId = new IdentityHashMap<Object, ObjectIdSerializer<?>>();
            }
        }
        mapObjectId.put(object, id);
    }

    /**
     * {@inheritDoc}
     *
     * <p>getObjectId</p>
     */
    @Override
    public ObjectIdSerializer<?> getObjectId(Object object) {
        if (null != mapObjectId) {
            return mapObjectId.get(object);
        }
        return null;
    }

    /**
     * {@inheritDoc}
     *
     * Used by generated {@link AbstractBeanJsonSerializer}
     */
    @Override
    @SuppressWarnings("UnusedDeclaration")
    public void addGenerator(ObjectIdGenerator<?> generator) {
        if (null == generators) {
            generators = new ArrayList<ObjectIdGenerator<?>>();
        }
        generators.add(generator);
    }

    /**
     * {@inheritDoc}
     *
     * Used by generated {@link AbstractBeanJsonSerializer}
     */
    @Override
    @SuppressWarnings({"UnusedDeclaration", "unchecked"})
    public <T> ObjectIdGenerator<T> findObjectIdGenerator(ObjectIdGenerator<T> gen) {
        if (null != generators) {
            for (ObjectIdGenerator<?> generator : generators) {
                if (generator.canUseFor(gen)) {
                    return (ObjectIdGenerator<T>) generator;
                }
            }
        }
        return null;
    }

    /** {@inheritDoc} */
    @Override
    public JsonSerializerParameters defaultParameters() {
        return JacksonContextProvider.get().defaultSerializerParameters();
    }
}
