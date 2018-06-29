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

import com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey;
import org.dominokit.jacksonapt.exception.JsonDeserializationException;
import org.dominokit.jacksonapt.stream.JsonReader;
import org.dominokit.jacksonapt.stream.impl.NonBufferedJsonReader;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Context for the deserialization process.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public class DefaultJsonDeserializationContext implements JsonDeserializationContext {

    /**
     * Builder for {@link JsonDeserializationContext}. To override default settings globally, you can extend this class, modify the
     * default settings inside the constructor and tell the compiler to use your builder instead in your gwt.xml file :
     * <pre>
     * {@code
     *
     * <replace-with class="your.package.YourBuilder">
     *   <when-type-assignable class="org.dominokit.jacksonapt.JsonDeserializationContext.Builder" />
     * </replace-with>
     *
     * }
     * </pre>
     */
    public static class Builder {

        protected boolean failOnUnknownProperties = true;

        protected boolean unwrapRootValue = false;

        protected boolean acceptSingleValueAsArray = false;

        protected boolean wrapExceptions = true;

        protected boolean useSafeEval = true;

        protected boolean readUnknownEnumValuesAsNull = false;

        protected boolean useBrowserTimezone = false;

        /**
         * @deprecated Use {@link DefaultJsonDeserializationContext#builder()} instead. This constructor will be made protected in v1.0.
         */
        @Deprecated
        public Builder() {
        }

        /**
         * Determines whether encountering of unknown
         * properties (ones that do not map to a property, and there is
         * no "any setter" or handler that can handle it)
         * should result in a failure (by throwing a
         * {@link JsonDeserializationException}) or not.
         * This setting only takes effect after all other handling
         * methods for unknown properties have been tried, and
         * property remains unhandled.
         * <p>
         * Feature is enabled by default (meaning that a
         * {@link JsonDeserializationException} will be thrown if an unknown property
         * is encountered).
         * </p>
         *
         * @param failOnUnknownProperties true if should fail on unknown properties
         * @return the builder
         */
        public Builder failOnUnknownProperties(boolean failOnUnknownProperties) {
            this.failOnUnknownProperties = failOnUnknownProperties;
            return this;
        }

        /**
         * Feature to allow "unwrapping" root-level JSON value, to match setting of
         * {@link DefaultJsonSerializationContext.Builder#wrapRootValue(boolean)} used for serialization.
         * Will verify that the root JSON value is a JSON Object, and that it has
         * a single property with expected root name. If not, a
         * {@link JsonDeserializationException} is thrown; otherwise value of the wrapped property
         * will be deserialized as if it was the root value.
         * <p>
         * Feature is disabled by default.
         * </p>
         *
         * @param unwrapRootValue true if should unwrapRootValue
         * @return the builder
         */
        public Builder unwrapRootValue(boolean unwrapRootValue) {
            this.unwrapRootValue = unwrapRootValue;
            return this;
        }

        /**
         * Feature that determines whether it is acceptable to coerce non-array
         * (in JSON) values to work with Java collection (arrays, java.util.Collection)
         * types. If enabled, collection deserializers will try to handle non-array
         * values as if they had "implicit" surrounding JSON array.
         * This feature is meant to be used for compatibility/interoperability reasons,
         * to work with packages (such as XML-to-JSON converters) that leave out JSON
         * array in cases where there is just a single element in array.
         * <p>
         * Feature is disabled by default.
         * </p>
         *
         * @param acceptSingleValueAsArray true if should acceptSingleValueAsArray
         * @return the builder
         */
        public Builder acceptSingleValueAsArray(boolean acceptSingleValueAsArray) {
            this.acceptSingleValueAsArray = acceptSingleValueAsArray;
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

        /**
         * to deserialize JsType
         * <br>
         * <br>
         *
         * @param useSafeEval true if should useSafeEval
         * @return the builder
         */
        public Builder useSafeEval(boolean useSafeEval) {
            this.useSafeEval = useSafeEval;
            return this;
        }

        /**
         * Feature that determines whether gwt-jackson should return null for unknown enum values.
         * Default is false which will throw {@link IllegalArgumentException} when unknown enum value is found.
         *
         * @param readUnknownEnumValuesAsNull true if should readUnknownEnumValuesAsNull
         * @return the builder
         */
        public Builder readUnknownEnumValuesAsNull(boolean readUnknownEnumValuesAsNull) {
            this.readUnknownEnumValuesAsNull = readUnknownEnumValuesAsNull;
            return this;
        }

        /**
         * Feature that specifies whether dates that doesn't contain timezone information
         * are interpreted using the browser timezone or being relative to UTC (the default).
         *
         * @param useBrowserTimezone true if should use browser timezone
         * @return the builder
         */
        public Builder useBrowserTimezone(boolean useBrowserTimezone) {
            this.useBrowserTimezone = useBrowserTimezone;
            return this;
        }

        public final JsonDeserializationContext build() {
            return new DefaultJsonDeserializationContext(failOnUnknownProperties, unwrapRootValue, acceptSingleValueAsArray, wrapExceptions,
                    useSafeEval, readUnknownEnumValuesAsNull, useBrowserTimezone);
        }
    }

    public static class DefaultBuilder extends Builder {

        private DefaultBuilder() {
        }

    }

    /**
     * <p>builder</p>
     *
     * @return a {@link DefaultJsonDeserializationContext.Builder} object.
     */
    public static Builder builder() {
        return new DefaultBuilder();
    }

    private static final Logger logger = Logger.getLogger("JsonDeserialization");

    private Map<IdKey, Object> idToObject;

    /*
     * Deserialization options
     */
    private final boolean failOnUnknownProperties;

    private final boolean unwrapRootValue;

    private final boolean acceptSingleValueAsArray;

    private final boolean wrapExceptions;

    private final boolean useSafeEval;

    private final boolean readUnknownEnumValuesAsNull;

    private final boolean useBrowserTimezone;

    private DefaultJsonDeserializationContext(boolean failOnUnknownProperties, boolean unwrapRootValue, boolean acceptSingleValueAsArray,
                                              boolean wrapExceptions, boolean useSafeEval, boolean readUnknownEnumValuesAsNull,
                                              boolean useBrowserTimezone) {
        this.failOnUnknownProperties = failOnUnknownProperties;
        this.unwrapRootValue = unwrapRootValue;
        this.acceptSingleValueAsArray = acceptSingleValueAsArray;
        this.wrapExceptions = wrapExceptions;
        this.useSafeEval = useSafeEval;
        this.readUnknownEnumValuesAsNull = readUnknownEnumValuesAsNull;
        this.useBrowserTimezone = useBrowserTimezone;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Logger getLogger() {
        return logger;
    }

    /**
     * <p>isFailOnUnknownProperties</p>
     *
     * @return a boolean.
     * @see Builder#failOnUnknownProperties(boolean)
     */
    @Override
    public boolean isFailOnUnknownProperties() {
        return failOnUnknownProperties;
    }

    /**
     * <p>isUnwrapRootValue</p>
     *
     * @return a boolean.
     * @see Builder#unwrapRootValue(boolean)
     */
    @Override
    public boolean isUnwrapRootValue() {
        return unwrapRootValue;
    }

    /**
     * <p>isAcceptSingleValueAsArray</p>
     *
     * @return a boolean.
     * @see Builder#acceptSingleValueAsArray(boolean)
     */
    @Override
    public boolean isAcceptSingleValueAsArray() {
        return acceptSingleValueAsArray;
    }

    /**
     * <p>isUseSafeEval</p>
     *
     * @return a boolean.
     * @see Builder#useSafeEval(boolean)
     */
    @Override
    public boolean isUseSafeEval() {
        return useSafeEval;
    }

    /**
     * <p>isReadUnknownEnumValuesAsNull</p>
     *
     * @return a boolean.
     * @see Builder#readUnknownEnumValuesAsNull(boolean)
     */
    @Override
    public boolean isReadUnknownEnumValuesAsNull() {
        return readUnknownEnumValuesAsNull;
    }

    /**
     * <p>isUseBrowserTimezone</p>
     *
     * @return a boolean.
     * @see Builder#isUseBrowserTimezone()
     */
    @Override
    public boolean isUseBrowserTimezone() {
        return useBrowserTimezone;
    }

    /**
     * <p>newJsonReader</p>
     *
     * @param input a {@link String} object.
     * @return a {@link org.dominokit.jacksonapt.stream.JsonReader} object.
     */
    @Override
    public JsonReader newJsonReader(String input) {
        JsonReader reader = new NonBufferedJsonReader(input);
        reader.setLenient(true);
        return reader;
    }

    /**
     * Trace an error with current reader state and returns a corresponding exception.
     *
     * @param message error message
     * @return a {@link JsonDeserializationException} with the given message
     */
    @Override
    public JsonDeserializationException traceError(String message) {
        return traceError(message, null);
    }

    /**
     * Trace an error with current reader state and returns a corresponding exception.
     *
     * @param message error message
     * @param reader  current reader
     * @return a {@link JsonDeserializationException} with the given message
     */
    @Override
    public JsonDeserializationException traceError(String message, JsonReader reader) {
        getLogger().log(Level.SEVERE, message);
        traceReaderInfo(reader);
        return new JsonDeserializationException(message);
    }

    /**
     * Trace an error and returns a corresponding exception.
     *
     * @param cause cause of the error
     * @return a {@link JsonDeserializationException} if we wrap the exceptions, the cause otherwise
     */
    @Override
    public RuntimeException traceError(RuntimeException cause) {
        getLogger().log(Level.SEVERE, "Error during deserialization", cause);
        if (wrapExceptions) {
            return new JsonDeserializationException(cause);
        } else {
            return cause;
        }
    }

    /**
     * Trace an error with current reader state and returns a corresponding exception.
     *
     * @param cause  cause of the error
     * @param reader current reader
     * @return a {@link JsonDeserializationException} if we wrap the exceptions, the cause otherwise
     */
    @Override
    public RuntimeException traceError(RuntimeException cause, JsonReader reader) {
        RuntimeException exception = traceError(cause);
        traceReaderInfo(reader);
        return exception;
    }

    /**
     * Trace the current reader state
     */
    private void traceReaderInfo(JsonReader reader) {
        if (null != reader && getLogger().isLoggable(Level.INFO)) {
            getLogger().log(Level.INFO, "Error at line " + reader.getLineNumber() + " and column " + reader
                    .getColumnNumber() + " of input <" + reader.getInput() + ">");
        }
    }

    /**
     * <p>addObjectId</p>
     *
     * @param id       a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey} object.
     * @param instance a {@link Object} object.
     */
    @Override
    public void addObjectId(IdKey id, Object instance) {
        if (null == idToObject) {
            idToObject = new HashMap<IdKey, Object>();
        }
        idToObject.put(id, instance);
    }

    /**
     * <p>getObjectWithId</p>
     *
     * @param id a {@link com.fasterxml.jackson.annotation.ObjectIdGenerator.IdKey} object.
     * @return a {@link Object} object.
     */
    @Override
    public Object getObjectWithId(IdKey id) {
        if (null != idToObject) {
            return idToObject.get(id);
        }
        return null;
    }

    @Override
    public JsonDeserializerParameters defaultParameters() {
        return JacksonContextProvider.get().defaultDeserializerParameters();
    }
}
