package org.simpleflatmapper.converter.protobuf;

import com.google.protobuf.Timestamp;
import org.simpleflatmapper.converter.Converter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class DateToPTimestampConverter implements Converter<Date, Timestamp> {
    @Override
    public Timestamp convert(Date in) throws Exception {
        if (in == null) return null;
        long time = in.getTime();
        return Timestamp.newBuilder().setSeconds(time /1000).setNanos((int)TimeUnit.MILLISECONDS.toNanos(time%1000)).build();
    }
}
