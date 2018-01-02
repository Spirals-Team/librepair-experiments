/*
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
package com.facebook.presto.type;

import com.facebook.presto.operator.scalar.AbstractTestFunctions;
import com.facebook.presto.spi.type.SqlTime;
import com.facebook.presto.spi.type.SqlTimeWithTimeZone;
import com.facebook.presto.spi.type.SqlTimestamp;
import com.facebook.presto.spi.type.SqlTimestampWithTimeZone;
import com.facebook.presto.spi.type.TimeZoneKey;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.testng.annotations.Test;

import static com.facebook.presto.spi.type.BooleanType.BOOLEAN;
import static com.facebook.presto.spi.type.TimeType.TIME;
import static com.facebook.presto.spi.type.TimeWithTimeZoneType.TIME_WITH_TIME_ZONE;
import static com.facebook.presto.spi.type.TimeZoneKey.getTimeZoneKey;
import static com.facebook.presto.spi.type.TimestampType.TIMESTAMP;
import static com.facebook.presto.spi.type.TimestampWithTimeZoneType.TIMESTAMP_WITH_TIME_ZONE;
import static com.facebook.presto.spi.type.VarcharType.VARCHAR;
import static com.facebook.presto.testing.TestingSession.testSessionBuilder;
import static com.facebook.presto.type.IntervalDayTimeType.INTERVAL_DAY_TIME;
import static com.facebook.presto.util.DateTimeZoneIndex.getDateTimeZone;

public abstract class TestTimeBase
        extends AbstractTestFunctions
{
    protected static final TimeZoneKey TIME_ZONE_KEY = getTimeZoneKey("Europe/Berlin");
    protected static final DateTimeZone DATE_TIME_ZONE = getDateTimeZone(TIME_ZONE_KEY);

    public TestTimeBase(boolean legacyTimestamp)
    {
        super(testSessionBuilder()
                .setSystemProperty("legacy_timestamp", String.valueOf(legacyTimestamp))
                .setTimeZoneKey(TIME_ZONE_KEY)
                .build());
    }

    @Test
    public void testLiteral()
            throws Exception
    {
        assertFunction("TIME '03:04:05.321'", TIME, new SqlTime(new DateTime(1970, 1, 1, 3, 4, 5, 321, DATE_TIME_ZONE).getMillis(), TIME_ZONE_KEY));
        assertFunction("TIME '03:04:05'", TIME, new SqlTime(new DateTime(1970, 1, 1, 3, 4, 5, 0, DATE_TIME_ZONE).getMillis(), TIME_ZONE_KEY));
        assertFunction("TIME '03:04'", TIME, new SqlTime(new DateTime(1970, 1, 1, 3, 4, 0, 0, DATE_TIME_ZONE).getMillis(), TIME_ZONE_KEY));
    }

    @Test
    public void testSubstract()
            throws Exception
    {
        functionAssertions.assertFunctionString("TIME '14:15:16.432' - TIME '03:04:05.321'", INTERVAL_DAY_TIME, "0 11:11:11.111");

        functionAssertions.assertFunctionString("TIME '03:04:05.321' - TIME '14:15:16.432'", INTERVAL_DAY_TIME, "-0 11:11:11.111");
    }

    @Test
    public void testEqual()
            throws Exception
    {
        assertFunction("TIME '03:04:05.321' = TIME '03:04:05.321'", BOOLEAN, true);

        assertFunction("TIME '03:04:05.321' = TIME '03:04:05.333'", BOOLEAN, false);
    }

    @Test
    public void testNotEqual()
            throws Exception
    {
        assertFunction("TIME '03:04:05.321' <> TIME '03:04:05.333'", BOOLEAN, true);

        assertFunction("TIME '03:04:05.321' <> TIME '03:04:05.321'", BOOLEAN, false);
    }

    @Test
    public void testLessThan()
            throws Exception
    {
        assertFunction("TIME '03:04:05.321' < TIME '03:04:05.333'", BOOLEAN, true);

        assertFunction("TIME '03:04:05.321' < TIME '03:04:05.321'", BOOLEAN, false);
        assertFunction("TIME '03:04:05.321' < TIME '03:04:05'", BOOLEAN, false);
    }

    @Test
    public void testLessThanOrEqual()
            throws Exception
    {
        assertFunction("TIME '03:04:05.321' <= TIME '03:04:05.333'", BOOLEAN, true);
        assertFunction("TIME '03:04:05.321' <= TIME '03:04:05.321'", BOOLEAN, true);

        assertFunction("TIME '03:04:05.321' <= TIME '03:04:05'", BOOLEAN, false);
    }

    @Test
    public void testGreaterThan()
            throws Exception
    {
        assertFunction("TIME '03:04:05.321' > TIME '03:04:05.111'", BOOLEAN, true);

        assertFunction("TIME '03:04:05.321' > TIME '03:04:05.321'", BOOLEAN, false);
        assertFunction("TIME '03:04:05.321' > TIME '03:04:05.333'", BOOLEAN, false);
    }

    @Test
    public void testGreaterThanOrEqual()
            throws Exception
    {
        assertFunction("TIME '03:04:05.321' >= TIME '03:04:05.111'", BOOLEAN, true);
        assertFunction("TIME '03:04:05.321' >= TIME '03:04:05.321'", BOOLEAN, true);

        assertFunction("TIME '03:04:05.321' >= TIME '03:04:05.333'", BOOLEAN, false);
    }

    @Test
    public void testBetween()
            throws Exception
    {
        assertFunction("TIME '03:04:05.321' between TIME '03:04:05.111' and TIME '03:04:05.333'", BOOLEAN, true);
        assertFunction("TIME '03:04:05.321' between TIME '03:04:05.321' and TIME '03:04:05.333'", BOOLEAN, true);
        assertFunction("TIME '03:04:05.321' between TIME '03:04:05.111' and TIME '03:04:05.321'", BOOLEAN, true);
        assertFunction("TIME '03:04:05.321' between TIME '03:04:05.321' and TIME '03:04:05.321'", BOOLEAN, true);

        assertFunction("TIME '03:04:05.321' between TIME '03:04:05.322' and TIME '03:04:05.333'", BOOLEAN, false);
        assertFunction("TIME '03:04:05.321' between TIME '03:04:05.311' and TIME '03:04:05.312'", BOOLEAN, false);
        assertFunction("TIME '03:04:05.321' between TIME '03:04:05.333' and TIME '03:04:05.111'", BOOLEAN, false);
    }

    @Test
    public void testCastToTimeWithTimeZone()
    {
        assertFunction("cast(TIME '03:04:05.321' as time with time zone)",
                TIME_WITH_TIME_ZONE,
                new SqlTimeWithTimeZone(new DateTime(1970, 1, 1, 3, 4, 5, 321, DATE_TIME_ZONE).getMillis(), DATE_TIME_ZONE.toTimeZone()));
    }

    @Test
    public void testCastToTimestamp()
            throws Exception
    {
        assertFunction("cast(TIME '03:04:05.321' as timestamp)",
                TIMESTAMP,
                new SqlTimestamp(new DateTime(1970, 1, 1, 3, 4, 5, 321, DATE_TIME_ZONE).getMillis(), TIME_ZONE_KEY));
    }

    @Test
    public void testCastToTimestampWithTimeZone()
            throws Exception
    {
        assertFunction("cast(TIME '03:04:05.321' as timestamp with time zone)",
                TIMESTAMP_WITH_TIME_ZONE,
                new SqlTimestampWithTimeZone(new DateTime(1970, 1, 1, 3, 4, 5, 321, DATE_TIME_ZONE).getMillis(), TIME_ZONE_KEY));
    }

    @Test
    public void testCastToSlice()
            throws Exception
    {
        assertFunction("cast(TIME '03:04:05.321' as varchar)", VARCHAR, "03:04:05.321");
        assertFunction("cast(TIME '03:04:05' as varchar)", VARCHAR, "03:04:05.000");
        assertFunction("cast(TIME '03:04' as varchar)", VARCHAR, "03:04:00.000");
    }

    @Test
    public void testCastFromSlice()
            throws Exception
    {
        assertFunction("cast('03:04:05.321' as time) = TIME '03:04:05.321'", BOOLEAN, true);
        assertFunction("cast('03:04:05' as time) = TIME '03:04:05.000'", BOOLEAN, true);
        assertFunction("cast('03:04' as time) = TIME '03:04:00.000'", BOOLEAN, true);
    }
}
