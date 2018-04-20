package org.sql2o.converters;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import lombok.extern.slf4j.Slf4j;

/**
 * @author Ivyxjc
 * @since 4/8/2018
 */
@Slf4j
public class Jdk8LocalDateTimeConverter implements Converter<LocalDateTime> {

    @Override
    public LocalDateTime convert(Object val) {
        log.debug("val:{} ", val);
        if (val instanceof Timestamp) {
            long time = ((Timestamp) val).getTime();
            LocalDateTime localDateTime =
                LocalDateTime.ofInstant(Instant.ofEpochMilli(time), ZoneId.systemDefault());
            return localDateTime;
        }
        return null;
    }

    @Override
    public Object toDatabaseParam(LocalDateTime val) {
        return Timestamp.valueOf(val);
    }
}