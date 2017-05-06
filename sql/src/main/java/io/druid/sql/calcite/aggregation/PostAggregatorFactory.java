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

package io.druid.sql.calcite.aggregation;

import io.druid.query.aggregation.PostAggregator;

/**
 * Can create PostAggregators with specific output names.
 */
public abstract class PostAggregatorFactory
{
  public abstract PostAggregator factorize(final String outputName);

  @Override
  public boolean equals(Object o)
  {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    PostAggregatorFactory that = (PostAggregatorFactory) o;

    return factorize(null).equals(that.factorize(null));
  }

  @Override
  public int hashCode()
  {
    return factorize(null).hashCode();
  }

  @Override
  public String toString()
  {
    return factorize(null).toString();
  }
}
