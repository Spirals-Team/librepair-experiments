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

package org.dominokit.jacksonapt.ser;

import org.dominokit.jacksonapt.JsonSerializationContext;
import org.dominokit.jacksonapt.JsonSerializer;
import org.dominokit.jacksonapt.JsonSerializerParameters;
import org.dominokit.jacksonapt.stream.JsonWriter;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 * Base implementation of {@link JsonSerializer} for {@link Number}.
 *
 * @author Nicolas Morel
 * @version $Id: $
 */
public abstract class BaseNumberJsonSerializer<N extends Number> extends JsonSerializer<N> {

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link BigDecimal}
     */
    public static final class BigDecimalJsonSerializer extends BaseNumberJsonSerializer<BigDecimal> {

        private static final BigDecimalJsonSerializer INSTANCE = new BigDecimalJsonSerializer();

        /**
         * @return an instance of {@link BigDecimalJsonSerializer}
         */
        public static BigDecimalJsonSerializer getInstance() {
            return INSTANCE;
        }

        private BigDecimalJsonSerializer() {
        }

        @Override
        protected boolean isDefault(BigDecimal value) {
            return null == value || BigDecimal.ZERO.compareTo(value) == 0;
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link BigInteger}
     */
    public static final class BigIntegerJsonSerializer extends BaseNumberJsonSerializer<BigInteger> {

        private static final BigIntegerJsonSerializer INSTANCE = new BigIntegerJsonSerializer();

        /**
         * @return an instance of {@link BigIntegerJsonSerializer}
         */
        public static BigIntegerJsonSerializer getInstance() {
            return INSTANCE;
        }

        private BigIntegerJsonSerializer() {
        }

        @Override
        protected boolean isDefault(BigInteger value) {
            return null == value || BigInteger.ZERO.compareTo(value) == 0;
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Byte}
     */
    public static final class ByteJsonSerializer extends BaseNumberJsonSerializer<Byte> {

        private static final ByteJsonSerializer INSTANCE = new ByteJsonSerializer();

        /**
         * @return an instance of {@link ByteJsonSerializer}
         */
        public static ByteJsonSerializer getInstance() {
            return INSTANCE;
        }

        private static byte defaultValue;

        private ByteJsonSerializer() {
        }

        @Override
        protected boolean isDefault(Byte value) {
            return null == value || defaultValue == value;
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Double}
     */
    public static final class DoubleJsonSerializer extends BaseNumberJsonSerializer<Double> {

        private static final DoubleJsonSerializer INSTANCE = new DoubleJsonSerializer();

        /**
         * @return an instance of {@link DoubleJsonSerializer}
         */
        public static DoubleJsonSerializer getInstance() {
            return INSTANCE;
        }

        private DoubleJsonSerializer() {
        }

        @Override
        protected boolean isDefault(Double value) {
            return null == value || value == 0d;
        }

        @Override
        public void doSerialize(JsonWriter writer, Double value, JsonSerializationContext ctx, JsonSerializerParameters params) {
            // writer has a special method to write double, let's use instead of default Number method.
            writer.value(value.doubleValue());
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Float}
     */
    public static final class FloatJsonSerializer extends BaseNumberJsonSerializer<Float> {

        private static final FloatJsonSerializer INSTANCE = new FloatJsonSerializer();

        /**
         * @return an instance of {@link FloatJsonSerializer}
         */
        public static FloatJsonSerializer getInstance() {
            return INSTANCE;
        }

        private FloatJsonSerializer() {
        }

        @Override
        protected boolean isDefault(Float value) {
            return null == value || value == 0f;
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Integer}
     */
    public static final class IntegerJsonSerializer extends BaseNumberJsonSerializer<Integer> {

        private static final IntegerJsonSerializer INSTANCE = new IntegerJsonSerializer();

        /**
         * @return an instance of {@link IntegerJsonSerializer}
         */
        public static IntegerJsonSerializer getInstance() {
            return INSTANCE;
        }

        private IntegerJsonSerializer() {
        }

        @Override
        protected boolean isDefault(Integer value) {
            return null == value || value == 0;
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Long}
     */
    public static final class LongJsonSerializer extends BaseNumberJsonSerializer<Long> {

        private static final LongJsonSerializer INSTANCE = new LongJsonSerializer();

        /**
         * @return an instance of {@link LongJsonSerializer}
         */
        public static LongJsonSerializer getInstance() {
            return INSTANCE;
        }

        private LongJsonSerializer() {
        }

        @Override
        protected boolean isDefault(Long value) {
            return null == value || value == 0l;
        }

        @Override
        public void doSerialize(JsonWriter writer, Long value, JsonSerializationContext ctx, JsonSerializerParameters params) {
            // writer has a special method to write long, let's use instead of default Number method.
            writer.value(value.longValue());
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Short}
     */
    public static final class ShortJsonSerializer extends BaseNumberJsonSerializer<Short> {

        private static final ShortJsonSerializer INSTANCE = new ShortJsonSerializer();

        /**
         * @return an instance of {@link ShortJsonSerializer}
         */
        public static ShortJsonSerializer getInstance() {
            return INSTANCE;
        }

        private static short defaultValue;

        private ShortJsonSerializer() {
        }

        @Override
        protected boolean isDefault(Short value) {
            return null == value || defaultValue == value;
        }
    }

    /**
     * Default implementation of {@link BaseNumberJsonSerializer} for {@link Number}
     */
    public static final class NumberJsonSerializer extends BaseNumberJsonSerializer<Number> {

        private static final NumberJsonSerializer INSTANCE = new NumberJsonSerializer();

        /**
         * @return an instance of {@link NumberJsonSerializer}
         */
        public static NumberJsonSerializer getInstance() {
            return INSTANCE;
        }

        private NumberJsonSerializer() {
        }

        @Override
        protected boolean isDefault(Number value) {
            return null == value || value.intValue() == 0;
        }
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void doSerialize(JsonWriter writer, N value, JsonSerializationContext ctx, JsonSerializerParameters params) {
        writer.value(value);
    }
}
