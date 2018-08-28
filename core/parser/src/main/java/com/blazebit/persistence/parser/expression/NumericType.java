/*
 * Copyright 2014 - 2018 Blazebit.
 *
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
 */

package com.blazebit.persistence.parser.expression;

import java.math.BigDecimal;
import java.math.BigInteger;

/**
 *
 * @author Moritz Becker
 * @since 1.2.0
 */
public enum NumericType {

    // NOTE: order matters!

    INTEGER(Integer.class),
    LONG(Long.class),
    BIG_INTEGER(BigInteger.class),
    FLOAT(Float.class),
    DOUBLE(Double.class),
    BIG_DECIMAL(BigDecimal.class);

    private Class<?> javaType;

    NumericType(Class<?> javaType) {
        this.javaType = javaType;
    }

    public Class<?> getJavaType() {
        return javaType;
    }
}
