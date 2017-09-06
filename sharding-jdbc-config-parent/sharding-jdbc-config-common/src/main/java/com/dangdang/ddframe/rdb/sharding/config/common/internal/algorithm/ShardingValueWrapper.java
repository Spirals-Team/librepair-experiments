/*
 * Copyright 1999-2015 dangdang.com.
 * <p>
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 * </p>
 */

package com.dangdang.ddframe.rdb.sharding.config.common.internal.algorithm;

import com.google.common.base.Preconditions;
import com.google.common.base.Strings;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Sharding value wrapper.
 * 
 * @author gaohongtao
 */
public class ShardingValueWrapper {
    
    private final Comparable<?> value;
    
    public ShardingValueWrapper(final Comparable<?> value) {
        Preconditions.checkArgument(value instanceof Number || value instanceof Date || value instanceof String, 
                String.format("Value must be type of Number, Data or String, your value type is '%s'", value.getClass().getName()));
        this.value = value;
    }
    
    /**
     * Get long value.
     * 
     * @return long value
     */
    public long longValue() {
        return numberValue().longValue();
    }
    
    /**
     * Get double value.
     * 
     * @return double value
     */
    public double doubleValue() {
        return numberValue().doubleValue();
    }
    
    private Number numberValue() {
        if (value instanceof Number) {
            return (Number) value;
        }
        if (value instanceof Date) {
            return ((Date) value).getTime();
        }
        return new BigDecimal(value.toString());
    }
    
    /**
     * Get date value.
     * 
     * @param format date format
     * @return date value
     * @throws ParseException date format parse exception
     */
    public Date dateValue(final String format) throws ParseException {
        if (value instanceof Number) {
            return new Date(((Number) value).longValue());
        }
        if (value instanceof Date) {
            return (Date) value;
        }
        Preconditions.checkArgument(!Strings.isNullOrEmpty(format));
        return new SimpleDateFormat(format).parse(value.toString());
    }
    
    /**
     * Get date value.
     * 
     * @return date value
     * @throws ParseException date format parse exception
     */
    public Date dateValue() throws ParseException {
        return dateValue(null);
    }
    
    /**
     * Convert date to string.
     * 
     * @param format date format
     * @return string value
     */
    public String toString(final String format) {
        if (value instanceof Date) {
            return new SimpleDateFormat(format).format(((Date) value).getTime());
        }
        if (value instanceof Number) {
            return new SimpleDateFormat(format).format(((Number) value).longValue());
        }
        return toString();
    }
    
    @Override
    public String toString() {
        return value.toString();
    }
}
