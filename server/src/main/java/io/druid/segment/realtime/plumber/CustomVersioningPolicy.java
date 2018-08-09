/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

package io.druid.segment.realtime.plumber;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.druid.java.util.common.DateTimes;
import org.joda.time.Interval;

/**
 */
public class CustomVersioningPolicy implements VersioningPolicy
{
  private final String version;

  @JsonCreator
  public CustomVersioningPolicy(
      @JsonProperty("version") String version
  )
  {
    this.version = version == null ? DateTimes.nowUtc().toString() : version;
  }

  @Override
  public String getVersion(Interval interval)
  {
    return version;
  }

  @JsonProperty("version")
  public String getVersion()
  {
    return version;
  }
}
