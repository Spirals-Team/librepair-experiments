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
package com.linkedin.pinot.core.query.pruner;

import java.util.HashMap;
import java.util.Map;
import org.apache.commons.configuration.Configuration;


/**
 * A static SegmentPrunerProvider will give SegmentPruner instance based on prunerClassName and configuration.
 *
 *
 */
public class SegmentPrunerProvider {
  private SegmentPrunerProvider() {
  }

  private static final Map<String, Class<? extends SegmentPruner>> PRUNER_MAP = new HashMap<>();

  static {
    PRUNER_MAP.put("columnvaluesegmentpruner", ColumnValueSegmentPruner.class);
    PRUNER_MAP.put("dataschemasegmentpruner", DataSchemaSegmentPruner.class);
    PRUNER_MAP.put("validsegmentpruner", ValidSegmentPruner.class);
    PRUNER_MAP.put("partitionsegmentpruner", PartitionSegmentPruner.class);
  }

  public static SegmentPruner getSegmentPruner(String prunerClassName, Configuration segmentPrunerConfig) {
    try {
      Class<? extends SegmentPruner> cls = PRUNER_MAP.get(prunerClassName.toLowerCase());
      if (cls != null) {
        SegmentPruner segmentPruner = cls.newInstance();
        segmentPruner.init(segmentPrunerConfig);
        return segmentPruner;
      }
    } catch (Exception ex) {
      throw new RuntimeException("Not support SegmentPruner type with - " + prunerClassName, ex);
    }
    throw new UnsupportedOperationException("No SegmentPruner type with - " + prunerClassName);
  }
}
