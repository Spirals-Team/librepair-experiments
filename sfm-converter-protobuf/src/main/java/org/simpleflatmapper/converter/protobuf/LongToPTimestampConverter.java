package org.simpleflatmapper.converter.protobuf;

import com.google.protobuf.Timestamp;
import org.simpleflatmapper.converter.Converter;

import java.util.Date;
import java.util.concurrent.TimeUnit;

public class LongToPTimestampConverter implements Converter<Long, Timestamp> {
    @Override
    public Timestamp convert(Long in) throws Exception {
        if (in == null) return null;
        long time = in;
        return Timestamp.newBuilder().setSeconds(time /1000).setNanos((int)TimeUnit.MILLISECONDS.toNanos(time%1000)).build();
    }
}
