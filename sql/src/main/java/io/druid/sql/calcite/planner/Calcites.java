/*
 * Licensed to Metamarkets Group Inc. (Metamarkets) under one
 * or more contributor license agreements. See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership. Metamarkets licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied. See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.sql.calcite.planner;

import com.google.common.io.BaseEncoding;
import com.google.common.primitives.Chars;
import io.druid.java.util.common.DateTimes;
import io.druid.java.util.common.IAE;
import io.druid.java.util.common.ISE;
import io.druid.java.util.common.StringUtils;
import io.druid.query.ordering.StringComparator;
import io.druid.query.ordering.StringComparators;
import io.druid.segment.column.ValueType;
import io.druid.server.security.AuthorizerMapper;
import io.druid.sql.calcite.schema.DruidSchema;
import io.druid.sql.calcite.schema.InformationSchema;
import org.apache.calcite.jdbc.CalciteSchema;
import org.apache.calcite.rex.RexLiteral;
import org.apache.calcite.rex.RexNode;
import org.apache.calcite.schema.Schema;
import org.apache.calcite.schema.SchemaPlus;
import org.apache.calcite.sql.SqlKind;
import org.apache.calcite.sql.type.SqlTypeName;
import org.apache.calcite.util.ConversionUtil;
import org.apache.calcite.util.DateString;
import org.apache.calcite.util.TimeString;
import org.apache.calcite.util.TimestampString;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.DateTimeFormatterBuilder;
import org.joda.time.format.ISODateTimeFormat;

import java.nio.charset.Charset;
import java.util.NavigableSet;

/**
 * Utility functions for Calcite.
 */
public class Calcites
{
  private static final DateTimes.UtcFormatter CALCITE_DATE_PARSER = DateTimes.wrapFormatter(ISODateTimeFormat.dateParser());
  private static final DateTimes.UtcFormatter CALCITE_TIMESTAMP_PARSER = DateTimes.wrapFormatter(
      new DateTimeFormatterBuilder()
          .append(ISODateTimeFormat.dateParser())
          .appendLiteral(' ')
          .append(ISODateTimeFormat.timeParser())
          .toFormatter()
  );

  private static final DateTimeFormatter CALCITE_TIME_PRINTER = DateTimeFormat.forPattern("HH:mm:ss.S");
  private static final DateTimeFormatter CALCITE_DATE_PRINTER = DateTimeFormat.forPattern("yyyy-MM-dd");
  private static final DateTimeFormatter CALCITE_TIMESTAMP_PRINTER = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss.S");

  private static final Charset DEFAULT_CHARSET = Charset.forName(ConversionUtil.NATIVE_UTF16_CHARSET_NAME);

  private Calcites()
  {
    // No instantiation.
  }

  public static void setSystemProperties()
  {
    // These properties control the charsets used for SQL literals. I don't see a way to change this except through
    // system properties, so we'll have to set those...

    final String charset = ConversionUtil.NATIVE_UTF16_CHARSET_NAME;
    System.setProperty("saffron.default.charset", Calcites.defaultCharset().name());
    System.setProperty("saffron.default.nationalcharset", Calcites.defaultCharset().name());
    System.setProperty("saffron.default.collation.name", StringUtils.format("%s$en_US", charset));
  }

  public static Charset defaultCharset()
  {
    return DEFAULT_CHARSET;
  }

  public static SchemaPlus createRootSchema(final Schema druidSchema, final AuthorizerMapper authorizerMapper)
  {
    final SchemaPlus rootSchema = CalciteSchema.createRootSchema(false, false).plus();
    rootSchema.add(DruidSchema.NAME, druidSchema);
    rootSchema.add(InformationSchema.NAME, new InformationSchema(rootSchema, authorizerMapper));
    return rootSchema;
  }

  public static String escapeStringLiteral(final String s)
  {
    if (s == null) {
      return "''";
    } else {
      boolean isPlainAscii = true;
      final StringBuilder builder = new StringBuilder("'");
      for (int i = 0; i < s.length(); i++) {
        final char c = s.charAt(i);
        if (Character.isLetterOrDigit(c) || c == ' ') {
          builder.append(c);
          if (c > 127) {
            isPlainAscii = false;
          }
        } else {
          builder.append("\\").append(BaseEncoding.base16().encode(Chars.toByteArray(c)));
          isPlainAscii = false;
        }
      }
      builder.append("'");
      return isPlainAscii ? builder.toString() : "U&" + builder.toString();
    }
  }

  public static ValueType getValueTypeForSqlTypeName(SqlTypeName sqlTypeName)
  {
    if (SqlTypeName.FLOAT == sqlTypeName) {
      return ValueType.FLOAT;
    } else if (SqlTypeName.FRACTIONAL_TYPES.contains(sqlTypeName)) {
      return ValueType.DOUBLE;
    } else if (SqlTypeName.TIMESTAMP == sqlTypeName
               || SqlTypeName.DATE == sqlTypeName
               || SqlTypeName.BOOLEAN == sqlTypeName
               || SqlTypeName.INT_TYPES.contains(sqlTypeName)) {
      return ValueType.LONG;
    } else if (SqlTypeName.CHAR_TYPES.contains(sqlTypeName)) {
      return ValueType.STRING;
    } else if (SqlTypeName.OTHER == sqlTypeName) {
      return ValueType.COMPLEX;
    } else {
      return null;
    }
  }

  public static StringComparator getStringComparatorForSqlTypeName(SqlTypeName sqlTypeName)
  {
    final ValueType valueType = getValueTypeForSqlTypeName(sqlTypeName);
    return getStringComparatorForValueType(valueType);
  }

  public static StringComparator getStringComparatorForValueType(ValueType valueType)
  {
    if (ValueType.isNumeric(valueType)) {
      return StringComparators.NUMERIC;
    } else if (ValueType.STRING == valueType) {
      return StringComparators.LEXICOGRAPHIC;
    } else {
      throw new ISE("Unrecognized valueType[%s]", valueType);
    }
  }

  /**
   * Calcite expects "TIMESTAMP" types to be an instant that has the expected local time fields if printed as UTC.
   *
   * @param dateTime joda timestamp
   * @param timeZone session time zone
   *
   * @return Calcite style millis
   */
  public static long jodaToCalciteTimestamp(final DateTime dateTime, final DateTimeZone timeZone)
  {
    return dateTime.withZone(timeZone).withZoneRetainFields(DateTimeZone.UTC).getMillis();
  }

  /**
   * Calcite expects "DATE" types to be number of days from the epoch to the UTC date matching the local time fields.
   *
   * @param dateTime joda timestamp
   * @param timeZone session time zone
   *
   * @return Calcite style date
   */
  public static int jodaToCalciteDate(final DateTime dateTime, final DateTimeZone timeZone)
  {
    final DateTime date = dateTime.withZone(timeZone).dayOfMonth().roundFloorCopy();
    return Days.daysBetween(DateTimes.EPOCH, date.withZoneRetainFields(DateTimeZone.UTC)).getDays();
  }

  /**
   * Calcite expects TIMESTAMP literals to be represented by TimestampStrings in the local time zone.
   *
   * @param dateTime joda timestamp
   * @param timeZone session time zone
   *
   * @return Calcite style Calendar, appropriate for literals
   */
  public static TimestampString jodaToCalciteTimestampString(final DateTime dateTime, final DateTimeZone timeZone)
  {
    // The replaceAll is because Calcite doesn't like trailing zeroes in its fractional seconds part.
    return new TimestampString(CALCITE_TIMESTAMP_PRINTER.print(dateTime.withZone(timeZone)).replaceAll("\\.?0+$", ""));
  }

  /**
   * Calcite expects TIME literals to be represented by TimeStrings in the local time zone.
   *
   * @param dateTime joda timestamp
   * @param timeZone session time zone
   *
   * @return Calcite style Calendar, appropriate for literals
   */
  public static TimeString jodaToCalciteTimeString(final DateTime dateTime, final DateTimeZone timeZone)
  {
    // The replaceAll is because Calcite doesn't like trailing zeroes in its fractional seconds part.
    return new TimeString(CALCITE_TIME_PRINTER.print(dateTime.withZone(timeZone)).replaceAll("\\.?0+$", ""));
  }

  /**
   * Calcite expects DATE literals to be represented by DateStrings in the local time zone.
   *
   * @param dateTime joda timestamp
   * @param timeZone session time zone
   *
   * @return Calcite style Calendar, appropriate for literals
   */
  public static DateString jodaToCalciteDateString(final DateTime dateTime, final DateTimeZone timeZone)
  {
    return new DateString(CALCITE_DATE_PRINTER.print(dateTime.withZone(timeZone)));
  }

  /**
   * Translates "literal" (a TIMESTAMP or DATE literal) to milliseconds since the epoch using the provided
   * session time zone.
   *
   * @param literal  TIMESTAMP or DATE literal
   * @param timeZone session time zone
   *
   * @return milliseconds time
   */
  public static DateTime calciteDateTimeLiteralToJoda(final RexNode literal, final DateTimeZone timeZone)
  {
    final SqlTypeName typeName = literal.getType().getSqlTypeName();
    if (literal.getKind() != SqlKind.LITERAL || (typeName != SqlTypeName.TIMESTAMP && typeName != SqlTypeName.DATE)) {
      throw new IAE("Expected literal but got[%s]", literal.getKind());
    }

    if (typeName == SqlTypeName.TIMESTAMP) {
      final TimestampString timestampString = (TimestampString) RexLiteral.value(literal);
      return CALCITE_TIMESTAMP_PARSER.parse(timestampString.toString()).withZoneRetainFields(timeZone);
    } else if (typeName == SqlTypeName.DATE) {
      final DateString dateString = (DateString) RexLiteral.value(literal);
      return CALCITE_DATE_PARSER.parse(dateString.toString()).withZoneRetainFields(timeZone);
    } else {
      throw new IAE("Expected TIMESTAMP or DATE but got[%s]", typeName);
    }
  }

  /**
   * The inverse of {@link #jodaToCalciteTimestamp(DateTime, DateTimeZone)}.
   *
   * @param timestamp Calcite style timestamp
   * @param timeZone  session time zone
   *
   * @return joda timestamp, with time zone set to the session time zone
   */
  public static DateTime calciteTimestampToJoda(final long timestamp, final DateTimeZone timeZone)
  {
    return new DateTime(timestamp, DateTimeZone.UTC).withZoneRetainFields(timeZone);
  }

  /**
   * The inverse of {@link #jodaToCalciteDate(DateTime, DateTimeZone)}.
   *
   * @param date     Calcite style date
   * @param timeZone session time zone
   *
   * @return joda timestamp, with time zone set to the session time zone
   */
  public static DateTime calciteDateToJoda(final int date, final DateTimeZone timeZone)
  {
    return DateTimes.EPOCH.plusDays(date).withZoneRetainFields(timeZone);
  }

  /**
   * Checks if a RexNode is a literal int or not. If this returns true, then {@code RexLiteral.intValue(literal)} can be
   * used to get the value of the literal.
   *
   * @param rexNode the node
   *
   * @return true if this is an int
   */
  public static boolean isIntLiteral(final RexNode rexNode)
  {
    return rexNode instanceof RexLiteral && SqlTypeName.INT_TYPES.contains(rexNode.getType().getSqlTypeName());
  }

  public static String findOutputNamePrefix(final String basePrefix, final NavigableSet<String> strings)
  {
    String prefix = basePrefix;

    while (!isUsablePrefix(strings, prefix)) {
      prefix = "_" + prefix;
    }

    return prefix;
  }

  private static boolean isUsablePrefix(final NavigableSet<String> strings, final String prefix)
  {
    // ":" is one character after "9"
    final NavigableSet<String> subSet = strings.subSet(prefix + "0", true, prefix + ":", false);
    return subSet.isEmpty();
  }
}
