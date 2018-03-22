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

import com.linkedin.pinot.common.data.DateTimeFieldSpec.TimeFormat;
import com.linkedin.pinot.common.data.DateTimeFormatSpec;
import com.linkedin.pinot.common.data.DateTimeGranularitySpec;


public class DateTimeConverterFactory {

  /**
   * Method to get dateTimeConverter depending on the input and output format
   * @param inputFormat
   * @param outputFormat
   * @return
   */
  public static DateTimeConverter getDateTimeConverterFromFormats(String inputFormat, String outputFormat,
      String outputGranularity) {
    DateTimeFormatSpec inputDateTimeFormatSpec = new DateTimeFormatSpec(inputFormat);
    DateTimeFormatSpec outputDateTimeFormatSpec = new DateTimeFormatSpec(outputFormat);
    DateTimeGranularitySpec outputDateTimeGranularitySpec = new DateTimeGranularitySpec(outputGranularity);

    DateTimeConverter dateTimeConverter;
    TimeFormat inputTimeFormat = inputDateTimeFormatSpec.getTimeFormat();
    TimeFormat outputTimeFormat = outputDateTimeFormatSpec.getTimeFormat();
    if (inputTimeFormat.equals(TimeFormat.EPOCH)) {
      if (outputTimeFormat.equals(TimeFormat.EPOCH)) {
        dateTimeConverter =
            new EpochToEpochConverter(inputDateTimeFormatSpec, outputDateTimeFormatSpec, outputDateTimeGranularitySpec);
      } else {
        dateTimeConverter =
            new EpochToSDFConverter(inputDateTimeFormatSpec, outputDateTimeFormatSpec, outputDateTimeGranularitySpec);
      }
    } else {
      if (outputTimeFormat.equals(TimeFormat.EPOCH)) {
        dateTimeConverter =
            new SDFToEpochConverter(inputDateTimeFormatSpec, outputDateTimeFormatSpec, outputDateTimeGranularitySpec);
      } else {
        dateTimeConverter =
            new SDFToSDFConverter(inputDateTimeFormatSpec, outputDateTimeFormatSpec, outputDateTimeGranularitySpec);
      }
    }
    return dateTimeConverter;
  }
}
