/**
 * Copyright (C) 2016-2018 Expedia Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.hotels.housekeeping.conf;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import org.joda.time.Duration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Configuration
@ConfigurationProperties(prefix = "housekeeping")
public class Housekeeping {

  @NotNull(message = "housekeeping.expiredPathDuration must not be null")
  private Duration expiredPathDuration = Duration.standardDays(3);

  @NotNull(message = "housekeeping.dataSource must not be null")
  @Valid
  private DataSource dataSource = new DataSource();

  public Duration getExpiredPathDuration() {
    return expiredPathDuration;
  }

  public void setExpiredPathDuration(Duration expiredPathDuration) {
    this.expiredPathDuration = expiredPathDuration;
  }

  public DataSource getDataSource() {
    return dataSource;
  }

  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
  }

}
