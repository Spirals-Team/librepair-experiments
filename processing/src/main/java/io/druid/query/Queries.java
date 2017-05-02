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

package io.druid.query;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import io.druid.query.aggregation.AggregatorFactory;
import io.druid.query.aggregation.PostAggregator;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 */
public class Queries
{
  public static List<PostAggregator> decoratePostAggregators(List<PostAggregator> postAggs,
                                                             Map<String, AggregatorFactory> aggFactories)
  {
    List<PostAggregator> decorated = Lists.newArrayListWithExpectedSize(postAggs.size());
    for (PostAggregator aggregator : postAggs) {
      decorated.add(aggregator.decorate(aggFactories));
    }
    return decorated;
  }

  public static List<PostAggregator> prepareAggregations(
      List<AggregatorFactory> aggFactories,
      List<PostAggregator> postAggs
  )
  {
    Preconditions.checkNotNull(aggFactories, "aggregations cannot be null");

    final Map<String, AggregatorFactory> aggsFactoryMap = Maps.newHashMap();
    for (AggregatorFactory aggFactory : aggFactories) {
      Preconditions.checkArgument(!aggsFactoryMap.containsKey(aggFactory.getName()),
                                  "[%s] already defined", aggFactory.getName());
      aggsFactoryMap.put(aggFactory.getName(), aggFactory);
    }

    if (postAggs != null && !postAggs.isEmpty()) {
      final Set<String> combinedAggNames = Sets.newHashSet(aggsFactoryMap.keySet());

      List<PostAggregator> decorated = Lists.newArrayListWithExpectedSize(postAggs.size());
      for (final PostAggregator postAgg : postAggs) {
        final Set<String> dependencies = postAgg.getDependentFields();
        final Set<String> missing = Sets.difference(dependencies, combinedAggNames);

        Preconditions.checkArgument(
            missing.isEmpty(),
            "Missing fields [%s] for postAggregator [%s]", missing, postAgg.getName()
        );
        Preconditions.checkArgument(combinedAggNames.add(postAgg.getName()),
                                    "[%s] already defined", postAgg.getName());

        decorated.add(postAgg.decorate(aggsFactoryMap));
      }
      return decorated;
    }

    return postAggs;
  }
}
