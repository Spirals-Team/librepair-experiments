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

package io.druid.query.search;

import com.google.common.annotations.VisibleForTesting;
import com.google.inject.Inject;
import io.druid.query.DefaultGenericQueryMetricsFactory;
import io.druid.query.GenericQueryMetricsFactory;

public class DefaultSearchQueryMetricsFactory implements SearchQueryMetricsFactory
{
  private static final SearchQueryMetricsFactory INSTANCE =
      new DefaultSearchQueryMetricsFactory(DefaultGenericQueryMetricsFactory.instance());
  private final GenericQueryMetricsFactory genericQueryMetricsFactory;

  @Inject
  public DefaultSearchQueryMetricsFactory(GenericQueryMetricsFactory genericQueryMetricsFactory)
  {
    this.genericQueryMetricsFactory = genericQueryMetricsFactory;
  }

  @VisibleForTesting
  public static SearchQueryMetricsFactory instance()
  {
    return INSTANCE;
  }

  @Override
  public SearchQueryMetrics makeMetrics(SearchQuery query)
  {
    return new DefaultSearchQueryMetrics(genericQueryMetricsFactory.makeMetrics(query));
  }
}
