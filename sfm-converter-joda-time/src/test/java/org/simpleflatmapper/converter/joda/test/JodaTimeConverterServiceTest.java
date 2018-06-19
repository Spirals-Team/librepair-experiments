package org.simpleflatmapper.converter.joda.test;

import org.joda.time.*;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.junit.Test;
import org.simpleflatmapper.converter.Converter;
import org.simpleflatmapper.converter.ConverterService;



import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.fail;
import static org.simpleflatmapper.converter.test.ConverterServiceTestHelper.testConverter;

public class JodaTimeConverterServiceTest {


    @Test
    public void testDateToJodaDateTime() throws Exception {
        DateTime dateTime = DateTime.now();
        testConverter(dateTime.toDate(), dateTime);
    }
    @Test
    public void testDateToJodaInstant() throws Exception {
        Instant dateTime = Instant.now();
        testConverter(dateTime.toDate(), dateTime);
    }
    @Test
    public void testDateToJodaLocalDate() throws Exception {
        LocalDate dateTime = LocalDate.now();
        testConverter(dateTime.toDate(), dateTime);
    }
    @Test
    public void testDateToJodaLocalDateTime() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        testConverter(dateTime.toDate(), dateTime);
    }

    @Test
    public void testDateToJodaLocalTime() throws Exception {
        LocalTime dateTime = LocalTime.now();
        testConverter(dateTime.toDateTimeToday().toDate(), dateTime);
    }


    @Test
    public void testJodaDateTimeToDate() throws Exception {
        DateTime dateTime = DateTime.now();
        testConverter(dateTime, dateTime.toDate());
    }
    @Test
    public void testJodaInstantToDate() throws Exception {
        Instant dateTime = Instant.now();
        testConverter(dateTime, dateTime.toDate());
    }
    @Test
    public void testJodaLocalDateToDate() throws Exception {
        LocalDate dateTime = LocalDate.now();
        testConverter(dateTime, dateTime.toDate());
    }
    @Test
    public void testJodaLocalDateTimeToDate() throws Exception {
        LocalDateTime dateTime = LocalDateTime.now();
        testConverter(dateTime, dateTime.toDate());
    }

    @Test
    public void testJodaLocalTimeToDate() throws Exception {
        LocalTime dateTime = LocalTime.now();
        testConverter(dateTime, dateTime.toDateTimeToday().toDate());
    }

    @Test
    public void testToStringConverter() throws Exception {
        Converter<? super LocalDate, ? extends String> converterLocalDate = ConverterService.getInstance().findConverter(LocalDate.class, String.class, DateTimeFormat.fullDate());
        LocalDate localDate = LocalDate.now();
        assertEquals(DateTimeFormat.fullDate().print(localDate), converterLocalDate.convert(localDate));

        Converter<? super DateTime, ? extends String> converterDateTime = ConverterService.getInstance().findConverter(DateTime.class, String.class, DateTimeFormat.fullDateTime());
        DateTime dateTime = DateTime.now();
        assertEquals(DateTimeFormat.fullDateTime().print(dateTime), converterDateTime.convert(dateTime));

    }

    @Test
    public void testToStringConverterNoFormat() throws Exception {
        Converter<? super LocalDate, ? extends String> converterLocalDate =
                ConverterService.getInstance().findConverter(LocalDate.class, String.class);
        LocalDate localDate = LocalDate.now();
        assertEquals(localDate.toString(), converterLocalDate.convert(localDate));

        Converter<? super DateTime, ? extends String> converterDateTime = ConverterService.getInstance().findConverter(DateTime.class, String.class);
        DateTime dateTime = DateTime.now();
        assertEquals(dateTime.toString(), converterDateTime.convert(dateTime));

    }


    @Test
    public void testCharacterToTime() throws Exception {
        testConvertFromCharSequence(Instant.now(), DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss.SSSS").withZone(DateTimeZone.getDefault()));
        testConvertFromCharSequence(LocalDate.now(), DateTimeFormat.fullDate());
        testConvertFromCharSequence(LocalDateTime.now(),DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss.SSSS").withZone(DateTimeZone.getDefault()));
        testConvertFromCharSequence(LocalTime.now(),DateTimeFormat.forPattern("HH:mm:ss.SSSS").withZone(DateTimeZone.getDefault()));
        testConvertFromCharSequence(DateTime.now(), DateTimeFormat.forPattern("yyyyMMdd HH:mm:ss.SSSS Z"));
    }

    @SuppressWarnings("unchecked")
    public void testConvertFromCharSequence(ReadableInstant date, DateTimeFormatter dateTimeFormatter) throws Exception {
        Converter<? super CharSequence, ? extends ReadableInstant> converter =
                ConverterService.getInstance().<CharSequence, ReadableInstant>findConverter(CharSequence.class, date.getClass(), dateTimeFormatter);
        assertEquals(date, converter.convert(dateTimeFormatter.print(date)));

        assertNull(converter.convert(""));
        assertNull(converter.convert(null));

        DateTimeFormatter failing = DateTimeFormat.forPattern("yyyy////dd");
        Converter<? super CharSequence, ? extends ReadableInstant> multiConverter =
                ConverterService.getInstance().<CharSequence, ReadableInstant>findConverter(CharSequence.class, date.getClass(), failing, dateTimeFormatter);
        assertEquals(date, multiConverter.convert(dateTimeFormatter.print(date)));

        try {
            multiConverter.convert("a");
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

    public void testConvertFromCharSequence(ReadablePartial date, DateTimeFormatter dateTimeFormatter) throws Exception {
        Converter<? super CharSequence, ? extends ReadablePartial> converter =
                ConverterService.getInstance().<CharSequence, ReadablePartial>findConverter(CharSequence.class, date.getClass(), dateTimeFormatter);
        assertEquals(date, converter.convert(dateTimeFormatter.print(date)));

        assertNull(converter.convert(""));
        assertNull(converter.convert(null));

        DateTimeFormatter failing = DateTimeFormat.forPattern("yyyy////dd");
        Converter<? super CharSequence, ? extends ReadablePartial> multiConverter =
                ConverterService.getInstance().<CharSequence, ReadablePartial>findConverter(CharSequence.class, date.getClass(), failing, dateTimeFormatter);
        assertEquals(date, multiConverter.convert(dateTimeFormatter.print(date)));

        try {
            multiConverter.convert("a");
            fail();
        } catch (IllegalArgumentException e) {

        }
    }

}