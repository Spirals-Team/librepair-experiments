/**
 * Copyright (C) 2014-2016 LinkedIn Corp. (pinot-core@linkedin.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.linkedin.pinot.common.datetime.converter;

import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;


public class DateTimeConverterTest {

  @Test(dataProvider = "testDateTimeConversion")
  public void testDateTimeConversion(String inputFormat, String outputFormat, String outputGranularity,
      Object inputValue, Object expectedValue) {
    DateTimeConverter converter =
        DateTimeConverterFactory.getDateTimeConverterFromFormats(inputFormat, outputFormat, outputGranularity);
    Assert.assertEquals(converter.convert(inputValue), expectedValue);
  }

  @DataProvider(name = "testDateTimeConversion")
  public Object[][] provideTestData() {
    return new Object[][]{
        new Object[]{"1:MILLISECONDS:EPOCH", "1:MILLISECONDS:EPOCH", "15:MINUTES", 1505898300000L, 1505898000000L},
        new Object[]{"1:MILLISECONDS:EPOCH", "1:MILLISECONDS:EPOCH", "1:MILLISECONDS", 1505898000000L, 1505898000000L},
        new Object[]{"1:MILLISECONDS:EPOCH", "1:HOURS:EPOCH", "1:HOURS", 1505902560000L, 418306L},
        new Object[]{"1:MILLISECONDS:EPOCH", "1:DAYS:SIMPLE_DATE_FORMAT:yyyyMMdd", "1:DAYS", 1505898300000L, 20170920L},
        new Object[]{"1:DAYS:SIMPLE_DATE_FORMAT:yyyyMMdd", "1:MILLISECONDS:EPOCH", "1:DAYS", 20170601, 1496275200000L},
        new Object[]{"1:HOURS:SIMPLE_DATE_FORMAT:M/d/yyyy h a", "1:MILLISECONDS:EPOCH", "1:HOURS", "8/7/2017 1 AM", 1502067600000L},
        new Object[]{"1:SECONDS:SIMPLE_DATE_FORMAT:M/d/yyyy h:mm:ss a", "1:MILLISECONDS:EPOCH", "1:HOURS", "12/27/2016 11:20:00 PM", 1482879600000L},
        new Object[]{"1:DAYS:SIMPLE_DATE_FORMAT:M/d/yyyy h:mm:ss a", "1:MILLISECONDS:EPOCH", "1:MILLISECONDS", "8/7/2017 12:45:50 AM", 1502066750000L},
        new Object[]{"5:MINUTES:EPOCH", "1:MILLISECONDS:EPOCH", "1:HOURS", 5019675L, 1505901600000L},
        new Object[]{"5:MINUTES:EPOCH", "1:HOURS:EPOCH", "1:HOURS", 5019661L, 418305L},
        new Object[]{"1:MILLISECONDS:EPOCH", "1:WEEKS:EPOCH", "1:MILLISECONDS", 1505898000000L, 2489L},
        new Object[]{"1:DAYS:SIMPLE_DATE_FORMAT:M/d/yyyy h:mm:ss a", "1:MILLISECONDS:EPOCH", "1:MILLISECONDS", null, 0L}
    };
  }
}
