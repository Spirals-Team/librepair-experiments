package org.simpleflatmapper.converter.joda;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.joda.time.LocalDate;
import org.joda.time.LocalDateTime;
import org.joda.time.LocalTime;
import org.joda.time.ReadableInstant;
import org.joda.time.ReadablePartial;
import org.joda.time.format.DateTimeFormatter;
import org.simpleflatmapper.converter.AbstractConverterFactory;
import org.simpleflatmapper.converter.AbstractConverterFactoryProducer;
import org.simpleflatmapper.converter.Converter;
import org.simpleflatmapper.converter.ConverterFactory;
import org.simpleflatmapper.converter.ConvertingTypes;
import org.simpleflatmapper.converter.ToStringConverter;
import org.simpleflatmapper.converter.joda.impl.AbstractMultiFormatConverterFactory;
import org.simpleflatmapper.converter.joda.impl.CharSequenceToJodaDateTimeConverter;
import org.simpleflatmapper.converter.joda.impl.CharSequenceToJodaInstantConverter;
import org.simpleflatmapper.converter.joda.impl.CharSequenceToJodaLocalDateConverter;
import org.simpleflatmapper.converter.joda.impl.CharSequenceToJodaLocalDateTimeConverter;
import org.simpleflatmapper.converter.joda.impl.CharSequenceToJodaLocalTimeConverter;
import org.simpleflatmapper.converter.joda.impl.DateToJodaDateTimeConverter;
import org.simpleflatmapper.converter.joda.impl.DateToJodaInstantConverter;
import org.simpleflatmapper.converter.joda.impl.DateToJodaLocalDateConverter;
import org.simpleflatmapper.converter.joda.impl.DateToJodaLocalDateTimeConverter;
import org.simpleflatmapper.converter.joda.impl.DateToJodaLocalTimeConverter;
import org.simpleflatmapper.converter.joda.impl.JodaDateTimeTojuDateConverter;
import org.simpleflatmapper.converter.joda.impl.JodaInstantTojuDateConverter;
import org.simpleflatmapper.converter.joda.impl.JodaLocalDateTimeTojuDateConverter;
import org.simpleflatmapper.converter.joda.impl.JodaLocalDateTojuDateConverter;
import org.simpleflatmapper.converter.joda.impl.JodaLocalTimeTojuDateConverter;
import org.simpleflatmapper.converter.joda.impl.JodaReadableInstantToStringConverter;
import org.simpleflatmapper.converter.joda.impl.JodaReadablePartialToStringConverter;
import org.simpleflatmapper.converter.joda.impl.JodaTimeHelper;
import org.simpleflatmapper.util.Consumer;

import java.util.Date;

public class JodaTimeConverterFactoryProducer extends AbstractConverterFactoryProducer {

    @Override
    public void produce(Consumer<? super ConverterFactory<?, ?>> consumer) {
        // Date to joda time
        factoryConverter(consumer, new AbstractConverterFactory<Date, DateTime>(Date.class, DateTime.class) {
            @Override
            public Converter<Date, DateTime> newConverter(ConvertingTypes targetedTypes, Object... params) {
                return new DateToJodaDateTimeConverter(JodaTimeHelper.getDateTimeZoneOrDefault(params));
            }
        });
        constantConverter(consumer, Date.class, Instant.class, new DateToJodaInstantConverter());
        constantConverter(consumer, Date.class, LocalDate.class, new DateToJodaLocalDateConverter());
        constantConverter(consumer, Date.class, LocalDateTime.class, new DateToJodaLocalDateTimeConverter());
        constantConverter(consumer, Date.class, LocalTime.class, new DateToJodaLocalTimeConverter());

        // joda time to date
        constantConverter(consumer, DateTime.class, Date.class, new JodaDateTimeTojuDateConverter());
        constantConverter(consumer, Instant.class, Date.class, new JodaInstantTojuDateConverter());
        constantConverter(consumer, LocalDate.class, Date.class, new JodaLocalDateTojuDateConverter());
        factoryConverter(consumer, new AbstractConverterFactory<LocalDateTime, Date>(LocalDateTime.class, Date.class) {
            @Override
            public Converter<LocalDateTime, Date> newConverter(ConvertingTypes targetedTypes, Object... params) {
                return new JodaLocalDateTimeTojuDateConverter(JodaTimeHelper.getDateTimeZoneOrDefault(params));
            }
        });
        factoryConverter(consumer, new AbstractConverterFactory<LocalTime, Date>(LocalTime.class, Date.class) {
            @Override
            public Converter<LocalTime, Date> newConverter(ConvertingTypes targetedTypes, Object... params) {
                return new JodaLocalTimeTojuDateConverter(JodaTimeHelper.getDateTimeZoneOrDefault(params));
            }
        });

        // char sequence to joda time
        factoryConverter(consumer, new AbstractMultiFormatConverterFactory<CharSequence, DateTime>(CharSequence.class, DateTime.class) {
            @Override
            protected Converter<CharSequence, DateTime> newConverter(DateTimeFormatter formatter) {
                return new CharSequenceToJodaDateTimeConverter(formatter);
            }
        });
        factoryConverter(consumer, new AbstractMultiFormatConverterFactory<CharSequence, Instant>(CharSequence.class, Instant.class) {
            @Override
            protected Converter<CharSequence, Instant> newConverter(DateTimeFormatter formatter) {
                return new CharSequenceToJodaInstantConverter(formatter);
            }
        });
        factoryConverter(consumer, new AbstractMultiFormatConverterFactory<CharSequence, LocalDate>(CharSequence.class, LocalDate.class) {
            @Override
            protected Converter<CharSequence, LocalDate> newConverter(DateTimeFormatter formatter) {
                return new CharSequenceToJodaLocalDateConverter(formatter);
            }
        });
        factoryConverter(consumer, new AbstractMultiFormatConverterFactory<CharSequence, LocalDateTime>(CharSequence.class, LocalDateTime.class) {
            @Override
            protected Converter<CharSequence, LocalDateTime> newConverter(DateTimeFormatter formatter) {
                return new CharSequenceToJodaLocalDateTimeConverter(formatter);
            }
        });
        factoryConverter(consumer, new AbstractMultiFormatConverterFactory<CharSequence, LocalTime>(CharSequence.class, LocalTime.class) {
            @Override
            protected Converter<CharSequence, LocalTime> newConverter(DateTimeFormatter formatter) {
                return new CharSequenceToJodaLocalTimeConverter(formatter);
            }
        });


        factoryConverter(consumer, new AbstractConverterFactory<ReadableInstant, String>(ReadableInstant.class, String.class) {
            @Override
            public Converter<? super ReadableInstant, String> newConverter(ConvertingTypes targetedTypes, Object... params) {
                DateTimeFormatter dateTimeFormatter  = JodaTimeHelper.getDateTimeFormatter(params);
                if (dateTimeFormatter != null) {
                    return new JodaReadableInstantToStringConverter(dateTimeFormatter);
                } else {
                    return ToStringConverter.INSTANCE;
                }
            }
        });
        factoryConverter(consumer, new AbstractConverterFactory<ReadablePartial, String>(ReadablePartial.class, String.class) {
            @Override
            public Converter<? super ReadablePartial, String> newConverter(ConvertingTypes targetedTypes, Object... params) {
                DateTimeFormatter dateTimeFormatter  = JodaTimeHelper.getDateTimeFormatter(params);
                if (dateTimeFormatter != null) {
                    return new JodaReadablePartialToStringConverter(dateTimeFormatter);
                } else {
                    return ToStringConverter.INSTANCE;
                }
            }
        });

    }
}
