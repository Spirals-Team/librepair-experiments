/*
 * Copyright 2005-2017 Dozer Project
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

package org.dozer.converters;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Optional;

/**
 * Internal converter to {@link LocalDateTime} type. Only intended for internal use.
 * <pre>
 * Supported conversions:
 *  Any child of TemporalAccessor &#45;&gt; LocalDateTime ()
 *  String &#45;&gt; LocalDateTime
 *  Long &#45;&gt; LocalDateTime
 *  Date &#45;&gt; LocalDateTime
 * </pre>
 */
public final class LocalDateTimeConverter
        extends AbstractJava8DateTimeConverter {

    public LocalDateTimeConverter(DateTimeFormatter formatter) {
        super(formatter);
    }

    @Override
    public Object convert(Class destClass, Object srcObject) {

        Optional<LocalDateTime> opt = convertToLocalDateTime(srcObject);

        if (!opt.isPresent()) {
            return super.convert(destClass, srcObject);
        }

        return opt
                .map(res -> {
                    if (LocalTime.class.isAssignableFrom(destClass)) {
                        return res.toLocalTime();
                    } else if (LocalDate.class.isAssignableFrom(destClass)) {
                        return res.toLocalDate();
                    }
                    return res;
                });
    }

    private Optional<LocalDateTime> convertToLocalDateTime(Object srcObject) {
        Class<?> srcObjectClass = srcObject.getClass();

        if (Date.class.isAssignableFrom(srcObjectClass)) {

            Instant instant = ((Date) srcObject).toInstant();
            return Optional.of(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));

        } else if (Instant.class.isAssignableFrom(srcObjectClass)) {

            return Optional.of(LocalDateTime.ofInstant(((Instant) srcObject), ZoneId.systemDefault()));

        } else if (Long.class.isAssignableFrom(srcObjectClass)) {

            Instant instant = Instant.ofEpochMilli((Long) srcObject);
            return Optional.of(LocalDateTime.ofInstant(instant, ZoneId.systemDefault()));

        } else {

            return Optional.empty();
        }
    }
}
