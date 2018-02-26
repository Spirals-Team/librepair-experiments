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
package com.linkedin.pinot.common.metrics;

import com.linkedin.pinot.common.Utils;


/**
* Enumeration containing all the query phases executed by the Pinot broker.
*
*/
public enum BrokerQueryPhase implements AbstractMetrics.QueryPhase {
  REQUEST_COMPILATION,
  QUERY_EXECUTION,
  QUERY_ROUTING,
  SCATTER_GATHER,
  DESERIALIZATION,
  REDUCE,
  REQUEST_CONNECTION_WAIT,
  AUTHORIZATION;

  private final String queryPhaseName;

  BrokerQueryPhase() {
    queryPhaseName = Utils.toCamelCase(name().toLowerCase());
  }

  @Override
  public String getQueryPhaseName() {
    return queryPhaseName;
  }
}
